package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 25.04.2017.
 */
public interface ExcelGenerator {
    public Workbook generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName) throws IOException;

    public Workbook generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName, String xAxisColumn, List<String> yAxisColumn) throws IOException;
}
