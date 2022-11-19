package com.asholokh.utglivedata.reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.asholokh.utglivedata.GasDto;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

/**
 * This class is responsible for downloading, reading and transforming to apropriate format the data from Ukrtransgaz.
 *
 * @autor a.sholokh
 */
@Component
public class ResourceReader {
  @Autowired
  private XlsWorkBookParser xlsWorkBootParser;
  @Value("${gaz.production.data.url}")
  private String url;

  /**
   * Reads all data from resource defined via <code>url</code>.
   * @return {@link List} of {@link GasDto}.
   * @throws IOException
   * @throws RarException
   */
  public List<GasDto> readAllData() throws IOException, RarException {
    List<GasDto> result = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();
    ClientHttpRequest request = restTemplate.getRequestFactory().createRequest(URI.create(url), HttpMethod.GET);
    String html = StreamUtils.copyToString(request.execute().getBody(), Charset.defaultCharset());

    Document document = Jsoup.parse(html);
    Elements links = document.getElementsByTag("a");
    for (Element link : links) {
      String linkHref = link.attr("href");
      if (linkHref.endsWith(".xlsx")) {
        ClientHttpRequest req = restTemplate.getRequestFactory().createRequest(URI.create(url+linkHref), HttpMethod.GET);
        ClientHttpResponse res = req.execute();
        GasDto dto = readWorkBookData(linkHref, res.getBody());
        result.add(dto);
      } else if (linkHref.endsWith(".rar")) {
        ClientHttpRequest req = restTemplate.getRequestFactory().createRequest(URI.create(url+linkHref), HttpMethod.GET);
        ClientHttpResponse res = req.execute();
        File file = Files.createTempFile("prefix", "sufix").toFile();
        OutputStream outputStream = new FileOutputStream(file);
        IOUtils.copy(res.getBody(), outputStream);
        outputStream.close();

        try (Archive archive = new Archive(file)) {
          for (FileHeader fileHeader : archive.getFileHeaders()) {
            String fileNameFromArchive = fileHeader.getFileNameString();
            GasDto dto = readWorkBookData(fileNameFromArchive, archive.getInputStream(fileHeader));
            result.add(dto);
          }
        }
      }
    }
    return result;
  }

  private GasDto readWorkBookData(String fileName, InputStream res) throws IOException {
    try (XSSFWorkbook workBook = new XSSFWorkbook(res)) {
      GasDto dto = xlsWorkBootParser.parse(workBook);

      String day = fileName.split("_")[2].split("\\.")[0];
      String month = fileName.split("_")[2].split("\\.")[1];
      String year = fileName.split("_")[2].split("\\.")[2];
      Calendar date = Calendar.getInstance();
      date.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));

      dto.setDate(date);
      return dto;
    }
  }
}
