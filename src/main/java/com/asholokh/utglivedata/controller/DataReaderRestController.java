package com.asholokh.utglivedata.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asholokh.utglivedata.GasDto;
import com.asholokh.utglivedata.reader.ResourceReader;
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

  @RequestMapping("/allData")
  public List<GasDto> readData() throws IOException, RarException {
    return resourceReader.readData();
  }

  @RequestMapping("data/from{dateFrom}/to{dateTo}")
  public List<GasDto> readDataForPeriod(@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws IOException, RarException {
    Calendar dateFromCalendar = parseDateStr(dateFrom);
    Calendar dateToCalendar = parseDateStr(dateTo);

    List<GasDto> allData = resourceReader.readData();

    List<GasDto> result = new ArrayList<>();
    for (GasDto gasDto : allData) {
      if (gasDto.getDate().compareTo(dateFromCalendar) >= 0 && gasDto.getDate().compareTo(dateToCalendar) <= 0) {
        result.add(gasDto);
      }
    }

    return result;
  }

  private Calendar parseDateStr(String dateStr) {
    String[] splittedDateString = dateStr.split("-");
    if (splittedDateString.length != 3) {
      return null;
    }
    String day = splittedDateString[0];
    String month = splittedDateString[1];
    String year = splittedDateString[2];
    Calendar date = Calendar.getInstance();
    try {
      date.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    } catch (NumberFormatException e) {
      return null;
    }
    return date;
  }
}
