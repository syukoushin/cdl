package com.ibm.cdl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ImportExcelUtil {
    public static void main(String[] args) {  
        readXml("D:/MyEclipseWork/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/bjdlexam/temp/管理制度题库（自动化专业）.xls");  
        System.out.println("-------------");  
        //readXml("d:/test2.xls");  
    }  
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return resultMap;
    }  
}
