package com.asholokh.utglivedata.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asholokh.utglivedata.GasDto;
import com.asholokh.utglivedata.reader.ResourceReader;
import com.asholokh.utglivedata.service.GasDataService;
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
  @Autowired
  private GasDataService dataService;

  /**
   * Gets all data.
   * @return {@link List} of {@link GasDto}s.
   * @throws IOException
   * @throws RarException
   */
  @RequestMapping("/allData")
  public List<GasDto> readAllData() throws IOException, RarException {
    return dataService.getAllData();
  }

  /**
   * Gets data for current year.
   * @return {@link List} of {@link GasDto}s for corresponding period.
   * @throws IOException
   * @throws RarException
   */
  @RequestMapping("/yearData")
  public List<GasDto> readYearData() throws IOException, RarException {
    return dataService.getYearData();
  }

  /**
   * Gets data for current month.
   * @return {@link List} of {@link GasDto}s for corresponding period.
   * @throws IOException
   * @throws RarException
   */
  @RequestMapping("/monthData")
  public List<GasDto> readMonthData() throws IOException, RarException {
    return dataService.getMonthData();
  }
}
