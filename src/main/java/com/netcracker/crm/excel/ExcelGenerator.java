package com.netcracker.crm.excel.interfaces;

import com.netcracker.crm.excel.additional.ExcelFormat;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 25.04.2017.
 */
public interface ExcelGenerator {
    public String generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName);

    public String generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName, String xAxisColumn, List<String> yAxisColumn);
}
