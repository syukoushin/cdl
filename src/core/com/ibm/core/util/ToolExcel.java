package com.ibm.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 导出execl的基本类
 * @author hezheng
 *@version 1.0
 */
public class ToolExcel
{
  /**
   * 直接从前台页面导出表头，然后与记录拼装到一起 最终
   * @param str 表头字符串
   * @param outputStream 输出流
   * @param list 记录集合
   * @author baolong
   */
  public static void exportExcel(String str, OutputStream outputStream, List<List<String>> list)
  {
    List<ExcelCellContent> cellList = ToolExcel.parseFromStrToList(str);
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet();
    HSSFRow row = null;
    // 设置标题样式
    HSSFCellStyle style = workbook.createCellStyle();
    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    style.setLocked(true);
    HSSFFont font = workbook.createFont();
    font.setFontHeightInPoints((short) 18);
    font.setFontName("宋体");
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    style.setFont(font);
    // 设置其他字体
    HSSFFont font1 = workbook.createFont();
    font1.setFontName("宋体");
    font1.setFontHeightInPoints((short) 10);

    // 设置靠左字体样式
    HSSFCellStyle style1 = workbook.createCellStyle();
    style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
    style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
    style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    style1.setLocked(true);
    style1.setFillForegroundColor(HSSFColor.BLACK.index);
    style1.setFont(font);

    // 设置其他样式
    HSSFCellStyle style2 = workbook.createCellStyle();
    style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
    style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    style2.setLocked(true);
    style2.setFillForegroundColor(HSSFColor.BLACK.index);
    style2.setFont(font1);

    for (ExcelCellContent ec : cellList)
    {
      row = sheet.getRow(ec.getRow());
      if (row == null)
      {
        row = sheet.createRow(ec.getRow());
      }
      HSSFCell cell = row.createCell(ec.getCol());
      CellRangeAddress cellRangeAddress = new CellRangeAddress(ec.getRow(),
          ec.getRow() + ec.getRowSpan() - 1, ec.getCol(), ec.getCol() + ec.getColSpan() - 1);
      sheet.addMergedRegion(cellRangeAddress);
      setRegionStyle(sheet, cellRangeAddress, style1);
      cell.setCellValue(new HSSFRichTextString(ec.getText()));
      if (ec.getAlignment() != null && ec.getAlignment().equals("left"))
      { // 如果页面设置的是靠左
        cell.setCellStyle(style1);
      } else
      {
        cell.setCellStyle(style);
      }
      if (ec.getColWidth() != 0)
      {
        sheet.setColumnWidth(ec.getCol(), ec.getColWidth() * 35);
      }
    }
    sheet.getRow(0).setHeightInPoints((float) 30);
    // 处理其他数据
    int startRow = sheet.getLastRowNum() + 1;
    for (int i = startRow; i < list.size() + startRow; i++)
    {
      HSSFRow row2 = sheet.createRow(i);
      for (int j = 0; j < list.get(0).size(); j++)
      {
        HSSFCell cell2 = row2.createCell(j);
        cell2.setCellValue(list.get(i - startRow).get(j));
        cell2.setCellStyle(style2);
      }
    }
    try
    {
      workbook.write(outputStream);
    } catch (IOException e)
    {
      e.printStackTrace();
    } finally
    {
      try
      {
        outputStream.flush();
        outputStream.close();
      } catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args) throws FileNotFoundException {
	  OutputStream out = new FileOutputStream("D:/test.xls");
}
  

  /**
   * 设置合并单元格的样式（普通的方法设置，合并的单元格只有第一个是有边框的）
   * @param sheet 工作空间
   * @param cellRangeAddress 合并单元格对象
   * @param style 合并单元格的样式
   * @author baolong
   */
  private static void setRegionStyle(HSSFSheet sheet, CellRangeAddress cellRangeAddress, HSSFCellStyle style)
  {
    int rowFrom = cellRangeAddress.getFirstRow();
    int rowTo = cellRangeAddress.getLastRow();
    int colFrom = cellRangeAddress.getFirstColumn();
    int colTo = cellRangeAddress.getLastColumn();
    for (int i = rowFrom; i <= rowTo; i++)
    {
      HSSFRow row = HSSFCellUtil.getRow(i, sheet);
      for (int j = colFrom; j <= colTo; j++)
      {
        HSSFCell cell = HSSFCellUtil.getCell(row, (short) j);
        cell.setCellStyle(style);
      }
    }
  }

  
  /**
   * 把字符串转换成cell对象的list 保留
   * @param str 表头组成的字符串
   * @return cell对象
   * @author baolong
   */
  private static List<ExcelCellContent> parseFromStrToList(String str)
  {
    List<ExcelCellContent> cellList = new ArrayList<ExcelCellContent>();
    ExcelCellContent excelCellContent = null;
    String[] cells = str.split("&");
    for (String cellStr : cells)
    {
      String[] cell = cellStr.split(",");
      excelCellContent = new ExcelCellContent();
      if (StringUtils.isNotBlank(cell[0]))
      {
        excelCellContent.setRow(Integer.parseInt(cell[0]));
      }
      if (StringUtils.isNotBlank(cell[1]))
      {
        excelCellContent.setCol(Integer.parseInt(cell[1]));
      }
      if (StringUtils.isNotBlank(cell[2]))
      {
        excelCellContent.setRowSpan(Integer.parseInt(cell[2]));
      }
      if (StringUtils.isNotBlank(cell[3]))
      {
        excelCellContent.setColSpan(Integer.parseInt(cell[3]));
      }
      if (cell.length > 4)
      {
        if (StringUtils.isNotBlank(cell[4]))
        {
        	excelCellContent.setColWidth(Integer.parseInt(cell[4]));
        }
      }
      if (cell.length > 5)
      { // 有时单元格中没有内容，会导致数组长度只有4，不加判断会索引溢出
        if (StringUtils.isNotBlank(cell[5]))
        {
          excelCellContent.setText(cell[5]);
        }
      }
      if (cell.length > 6)
      { // 有时单元格中没有内容，会导致数组长度只有4，不加判断会索引溢出
        if (StringUtils.isNotBlank(cell[6]))
        {
          excelCellContent.setAlignment(cell[6]);
        }
      }
      cellList.add(excelCellContent);
    }
    return cellList;
  }
  
  /**
   * 读取单sheet
   * @param fileName
   * @return
   */
  public static List<Map<Integer,Object>> readXml(String fileName){  
  	List<Map<Integer,Object>> resultList = new ArrayList<Map<Integer,Object>>();
      boolean isE2007 = false;    //判断是否是excel2007格式  
      InputStream input = null;
      Workbook wb  = null;  
      if(fileName.endsWith("xlsx"))  
          isE2007 = true;  
      try {  
          input = new FileInputStream(fileName);  //建立输入流  
          //根据文件格式(2003或者2007)来初始化  
          if(isE2007)  
              wb = new XSSFWorkbook(input);  
          else  
              wb = new HSSFWorkbook(input);  
          int length = wb.getNumberOfSheets();
          for(int i = 0 ; i < length ; i++){
          	Sheet sheet = wb.getSheetAt(i);     //获得第一个表单  
              Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
              while (rows.hasNext()) {  
              	Map<Integer,Object> tempMap = new HashMap<Integer,Object>();
                  Row row = rows.next();  //获得行数据  
                  Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                  while (cells.hasNext()) {  
                      Cell cell = cells.next();  
                      int columnIndex = cell.getColumnIndex();
                      switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                      case HSSFCell.CELL_TYPE_NUMERIC:  
                          tempMap.put(columnIndex, cell.getNumericCellValue());
                          break;  
                      case HSSFCell.CELL_TYPE_STRING:  
                          tempMap.put(columnIndex, cell.getStringCellValue());
                          break;  
                      case HSSFCell.CELL_TYPE_BOOLEAN:  
                          tempMap.put(columnIndex, cell.getBooleanCellValue());
                          break;  
                      case HSSFCell.CELL_TYPE_FORMULA:  
                          tempMap.put(columnIndex, cell.getCellFormula());
                          break;
                      case HSSFCell.CELL_TYPE_BLANK:
                          tempMap.put(columnIndex, "");
                      default:  
                      break;  
                      }  
                  }  
                  resultList.add(tempMap);
              }  
          }
      } catch (IOException ex) {  
          ex.printStackTrace();  
      } finally{
      	try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
      }
      return resultList;
  }

  public static Map<String,Object> readXmlMultiSheet(String fileName){
  	List<List<Map<Integer,Object>>> resultList = new ArrayList<List<Map<Integer,Object>>>();
      List<String> sheetInfoList = new ArrayList<String>();
      Map<String, Object> resultMap = new HashMap<String,Object>();
      boolean isE2007 = false;    //判断是否是excel2007格式  
      InputStream input = null;
      Workbook wb  = null;
      if(fileName.endsWith("xlsx"))
          isE2007 = true;
      try {
          input = new FileInputStream(fileName);  //建立输入流  
          //根据文件格式(2003或者2007)来初始化  
          if(isE2007)
              wb = new XSSFWorkbook(input);
          else
              wb = new HSSFWorkbook(input);
          int length = wb.getNumberOfSheets();
          for(int i = 0 ; i < length ; i++){
          	List<Map<Integer,Object>> tempList = new ArrayList<Map<Integer,Object>>();
          	Sheet sheet = wb.getSheetAt(i);     //获得第一个表单
              String sheetName = sheet.getSheetName();
              Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
              while (rows.hasNext()) {
              	Map<Integer,Object> tempMap = new HashMap<Integer,Object>();
                  Row row = rows.next();  //获得行数据  
                  Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                  while (cells.hasNext()) {
                      Cell cell = cells.next();
                      int columnIndex = cell.getColumnIndex();
                      switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                      case HSSFCell.CELL_TYPE_NUMERIC:
                          tempMap.put(columnIndex, cell.getNumericCellValue());
                          break;
                      case HSSFCell.CELL_TYPE_STRING:
                          tempMap.put(columnIndex, cell.getStringCellValue());
                          break;
                      case HSSFCell.CELL_TYPE_BOOLEAN:
                          tempMap.put(columnIndex, cell.getBooleanCellValue());
                          break;
                      case HSSFCell.CELL_TYPE_FORMULA:
                          tempMap.put(columnIndex, cell.getCellFormula());
                          break;
                      default:
                      break;
                      }
                  }
                  tempList.add(tempMap);
              }
              resultList.add(tempList);
              sheetInfoList.add(sheetName);
              resultMap.put("sheetInfo", sheetInfoList);
              resultMap.put("sheetData", resultList);
          }
      } catch (IOException ex) {
          ex.printStackTrace();
      } finally{
      	try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
      }
      return resultMap;
  }  
}
