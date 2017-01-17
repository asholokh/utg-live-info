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
package com.asholokh.utglivedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * This class supposed to be a singleton object to hold (cache) UTG data. So it will on need to be loaded for each and
 * every HTTP request.
 *
 * @autor a.sholokh
 */
@Component
public class DataHolder {
  private List<GasDto> data = new ArrayList<>();

  private Calendar dateUpdated;

  public boolean isUpToDate() {
    return isSameDay(Calendar.getInstance(), dateUpdated);
  }

  public List<GasDto> getData() {
    return data;
  }

  public void setData(List<GasDto> data) {
    this.data = data;
    dateUpdated = Calendar.getInstance();
  }

  private boolean isSameDay(Calendar cal1, Calendar cal2) {
    if (cal1 == null || cal2 == null) {
      return false;
    }
    return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
      cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
      cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
  }
}
