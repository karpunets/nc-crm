package com.netcracker.crm.excel.impl;

import com.netcracker.crm.excel.ExcelGenerator;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 25.04.2017.
 */
public class DefaultExcelGenerator implements ExcelGenerator {
    private String defaultRootPath = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\";
    public Workbook generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName) throws IOException {
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName);
    }

    public Workbook generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName, String xAxisColumn, List<String> yAxisColumn) throws IOException {
        return new DefaultExcelBuilder().getWorkbookChart(fileFormat,data,reportName,xAxisColumn,yAxisColumn);
    }
}
