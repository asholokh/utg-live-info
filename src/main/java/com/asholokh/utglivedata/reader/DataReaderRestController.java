package com.asholokh.utglivedata.reader;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asholokh.utglivedata.GasDto;
import com.github.junrar.exception.RarException;

/**
 * Controller which handles data retrieval requests.
 *
 * @autor a.sholokh
 */
@RestController
public class DataReaderRestController {
  @Autowired
  private ResourceReader resourceReader;

  @RequestMapping("/data")
  public List<GasDto> readData() throws IOException, RarException {
    return resourceReader.readData();
  }
}