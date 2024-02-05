package com.rvs.springboot.thymeleaf.pojo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PaySlip_ExcelGenerator {

	private List <String> payList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    

    public PaySlip_ExcelGenerator(List <String> payList) {
        this.payList = payList;
        workbook = new XSSFWorkbook();
    }
    private void writeHeader() {
        sheet = workbook.createSheet("String");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "S.NO", style);
        createCell(row, 1, "NAME", style);
        createCell(row, 2, "CTC", style);
        createCell(row, 3, "TOTAL", style);
        createCell(row, 4, "ABSENT", style);
        createCell(row, 5, "WORKING DAYS", style);
        createCell(row, 6, "EXTRA WORKING DAYS", style);
        createCell(row, 7, "BASIC SALARY", style);
        createCell(row, 8, "DA", style);
        createCell(row, 9, "HRA", style);
        createCell(row, 10, "TOTAL GROSS", style);
        createCell(row, 11, "ESI", style);
        createCell(row, 12, "EPF", style);
        createCell(row, 13, "ADVANCE BAL", style);
        createCell(row, 14, "TOTAL DEDUCTION", style);
        createCell(row, 15, "MONTHLY INCENTIVES", style);
        createCell(row, 16, "NET SALARY", style);
        createCell(row, 17, "ADV Deduction", style); 
        createCell(row, 18, "BRANCH", style); 
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        
        int i=1;
        for (String record: payList) {
            Row row = sheet.createRow(rowCount++);
            
            int columnCount = 0;
            
            
            
            for(String str : record.split("-"))
            {
            	
            	  createCell(row, columnCount++, str, style);
            	
            }
            createCell(row, 0, i++, style);
            
        }
    }
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
