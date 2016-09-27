package com.asholokh.utglivedata.reader;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.asholokh.utglivedata.GasDto;

import static org.junit.Assert.assertEquals;

/**
 * TODO javadoc
 *
 * @autor a.sholokh
 */
public class XlsWorkBootParserTest {
  @Test
  public void testParse() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    workbook.createSheet("sheet1");
    workbook.createSheet("sheet2");
    workbook.getSheetAt(0).createRow(0);
    workbook.getSheetAt(0).createRow(1);
    workbook.getSheetAt(0).createRow(2);
    workbook.getSheetAt(0).createRow(3);
    workbook.getSheetAt(0).createRow(4);
    workbook.getSheetAt(0).createRow(5);
    workbook.getSheetAt(0).createRow(6);
    XSSFRow row7 = workbook.getSheetAt(0).createRow(7);
    XSSFRow row8 = workbook.getSheetAt(0).createRow(8);
    XSSFRow row9 = workbook.getSheetAt(0).createRow(9);

    createCell(row7, 0, 11);
    createCell(row7, 1, 12);
    createCell(row7, 2, 13);
    createCell(row8, 0, 21);
    createCell(row8, 1, 22);
    createCell(row8, 2, 23);
    createCell(row9, 0, 31);
    createCell(row9, 1, 32);
    createCell(row9, 2, 33);

    GasDto resultDto = new XlsWorkBootParser().parse(workbook);

    assertEquals(13, resultDto.getUkrgazvydobuvannya());
    assertEquals(23, resultDto.getUkrnafta());
    assertEquals(33, resultDto.getOthers());

  }

  private XSSFCell createCell(XSSFRow row, int index, Integer value) {
    XSSFCell cell = row.createCell(index, XSSFCell.CELL_TYPE_NUMERIC);
    cell.setCellValue(value);

    return cell;
  }
}
