/*
 * Copyright (c) 2017 by Eyefreight BV (www.eyefreight.com). All rights reserved.
 *
 * This software is provided by the copyright holder and contributors "as is" and any express or implied warranties, including, but
 * not limited to, the implied warranties of merchantability and fitness for a particular purpose are disclaimed. In no event shall
 * Eyefreight BV or contributors be liable for any direct, indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services; * loss of use, data, or profits; or business
 * interruption) however caused and on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of the possibility of such damage.
 */
package com.asholokh.utglivedata.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asholokh.utglivedata.DataHolder;
import com.asholokh.utglivedata.GasDto;
import com.asholokh.utglivedata.reader.ResourceReader;
import com.github.junrar.exception.RarException;

/**
 * Gas data retrieval service.
 *
 * @autor a.sholokh
 */
@Component
public class GasDataService {
  @Autowired
  private DataHolder dataHolder;
  @Autowired
  private ResourceReader resourceReader;

  public List<GasDto> getAllData() {
    updateDataIfNecessary();
    return dataHolder.getData();
  }

  public List<GasDto> getYearData() {
    updateDataIfNecessary();
    return filterByCurrentCalendarField(dataHolder.getData(), Calendar.YEAR);
  }

  public List<GasDto> getMonthData() {
    updateDataIfNecessary();
    return filterByCurrentCalendarField(dataHolder.getData(), Calendar.MONTH);
  }

  private List<GasDto> filterByCurrentCalendarField(List<GasDto> initialData, int calendarField) {
    List<GasDto> result = new ArrayList<>();
    for (GasDto gasDto: initialData) {
      int currentCalendarField = Calendar.getInstance().get(calendarField);
      if (gasDto.getDate().get(calendarField) == currentCalendarField &&
        (currentCalendarField != Calendar.MONTH || gasDto.getDate().get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))) {
        result.add(gasDto);
      }
    }

    return result;
  }

  private void updateDataIfNecessary() {
    if (!dataHolder.isUpToDate()) {
      try {
        dataHolder.setData(resourceReader.readAllData());
      } catch (IOException | RarException e) {
        throw new ServiceException(e);
      }
    }
  }
}
