/*
 * Copyright (c) 2016 by Eyefreight BV (www.eyefreight.com). All rights reserved.
 *
 * This software is provided by the copyright holder and contributors "as is" and any express or implied warranties, including, but
 * not limited to, the implied warranties of merchantability and fitness for a particular purpose are disclaimed. In no event shall
 * Eyefreight BV or contributors be liable for any direct, indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services; * loss of use, data, or profits; or business
 * interruption) however caused and on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of the possibility of such damage.
 */
package com.asholokh.hello;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

/**
 * This class is responible for downloading, reading and transforming to apropriate format the data from Ukrtransgaz.
 *
 * @autor a.sholokh
 */
@Component
public class ResourceReader {
  public List<GasDto> readData() throws IOException {
    List<GasDto> result = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();
    ClientHttpRequest request = restTemplate.getRequestFactory().createRequest(URI.create("http://utg.ua/wp-content/uploads/cdd/ARCHIVE/PROD/UA/"), HttpMethod.GET);
    String html = StreamUtils.copyToString(request.execute().getBody(), Charset.defaultCharset());

    Document document = Jsoup.parse(html);
    Elements links = document.getElementsByTag("a");
    for (Element link : links) {
      String linkHref = link.attr("href");
      if (linkHref.endsWith(".xlsx")) {
        ClientHttpRequest req = restTemplate.getRequestFactory().createRequest(URI.create("http://utg.ua/wp-content/uploads/cdd/ARCHIVE/PROD/UA/"+linkHref), HttpMethod.GET);
        ClientHttpResponse res = req.execute();

        XSSFWorkbook workBook = new XSSFWorkbook(res.getBody());
        GasDto dto = new GasDto();
        XSSFCell ukrGazVydobuvannyaCell = workBook.getSheetAt(0).getRow(7).getCell(2);
        XSSFCell ukrNaftaCell = workBook.getSheetAt(0).getRow(8).getCell(2);
        XSSFCell othersCell = workBook.getSheetAt(0).getRow(9).getCell(2);
        dto.setUkrgazvydobuvannya(Double.valueOf(ukrGazVydobuvannyaCell.getNumericCellValue()).intValue());
        dto.setUkrnafta(Double.valueOf(ukrNaftaCell.getNumericCellValue()).intValue());
        dto.setOthers(Double.valueOf(othersCell.getNumericCellValue()).intValue());

        String day = linkHref.split("_")[2].split("\\.")[0];
        String month = linkHref.split("_")[2].split("\\.")[1];
        String year = linkHref.split("_")[2].split("\\.")[2];
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        dto.setDate(date);

        result.add(dto);
      }
    }
    return result;
  }

}
