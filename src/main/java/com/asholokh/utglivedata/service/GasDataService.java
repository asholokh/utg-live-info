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
    List<GasDto> allData = dataHolder.getData();
    return chooseDataForCurrentYear(allData);
  }

  public List<GasDto> getMonthData() {
    updateDataIfNecessary();
    List<GasDto> allData = dataHolder.getData();
    return chooseDataForCurrentMonth(allData);
  }

  private List<GasDto> chooseDataForCurrentYear(List<GasDto> allData) {
    return null;
  }

  private List<GasDto> chooseDataForCurrentMonth(List<GasDto> allData) {
    return null;
  }

  private void updateDataIfNecessary() {
    if (!dataHolder.isUpToDate()) {
      try {
        dataHolder.setData(resourceReader.readAllData());
      } catch (IOException e) {
        throw new ServiceException(e);
      } catch (RarException e) {
        throw new ServiceException(e);
      }
    }
  }
}
