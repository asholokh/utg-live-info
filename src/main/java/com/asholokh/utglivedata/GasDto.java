package com.asholokh.utglivedata;

import java.util.Calendar;

/**
 * DTO to handle Gaz extraction information for date specified in <code>date</code> attribute. This information consists of Gaz extractions volumes for the
 * following companies:
 * <ul>
 * <li>Ukrgazvydobuvannya</li>
 * <li>Ukrnafta</li>
 * <li>Other companies</li>
 * </ul>
 *
 * @autor a.sholokh
 */
public class GasDto {
  private Calendar date;
  private Calendar dateFrom;
  private Calendar dateTo;
  private int ukrgazvydobuvannya;
  private int ukrnafta;
  private int others;

  public Calendar getDate() {
    return date;
  }

  public void setDate(Calendar date) {
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
  public Calendar getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(Calendar dateFrom) {
    this.dateFrom = dateFrom;
  }

  public Calendar getDateTo() {
    return dateTo;
  }

  public void setDateTo(Calendar dateTo) {
    this.dateTo = dateTo;
  }
}
