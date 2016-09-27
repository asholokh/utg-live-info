package com.asholokh.utglivedata.reader;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.asholokh.utglivedata.GasDto;

/**
 * TODO javadoc
 *
 * @autor a.sholokh
 */
@Component
public class XlsWorkBootParser {
  public GasDto parse(XSSFWorkbook workBook) {
    XSSFCell ukrGazVydobuvannyaCell = workBook.getSheetAt(0).getRow(7).getCell(2);
    XSSFCell ukrNaftaCell = workBook.getSheetAt(0).getRow(8).getCell(2);
    XSSFCell othersCell = workBook.getSheetAt(0).getRow(9).getCell(2);

    GasDto dto = new GasDto();
    dto.setUkrgazvydobuvannya(Double.valueOf(ukrGazVydobuvannyaCell.getNumericCellValue()).intValue());
    dto.setUkrnafta(Double.valueOf(ukrNaftaCell.getNumericCellValue()).intValue());
    dto.setOthers(Double.valueOf(othersCell.getNumericCellValue()).intValue());

    return dto;
  }
}
