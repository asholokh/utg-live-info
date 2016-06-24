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

import java.time.LocalDate;

/**
 * TODO javadoc
 *
 * @autor a.sholokh
 */
public class GasDto {
  private LocalDate date;
  private int ukrgazvydobuvannya;
  private int ukrnafta;
  private int others;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public int getUkrgazvydobuvannya() {
    return ukrgazvydobuvannya;
  }

  public void setUkrgazvydobuvannya(int ukrgazvydobuvannya) {
    this.ukrgazvydobuvannya = ukrgazvydobuvannya;
  }

  public int getUkrnafta() {
    return ukrnafta;
  }

  public void setUkrnafta(int ukrnafta) {
    this.ukrnafta = ukrnafta;
  }

  public int getOthers() {
    return others;
  }

  public void setOthers(int others) {
    this.others = others;
  }
}
