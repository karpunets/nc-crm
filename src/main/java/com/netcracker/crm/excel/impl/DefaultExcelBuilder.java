package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.Coordinates;
import com.netcracker.crm.excel.additional.ExcelFormat;
import com.netcracker.crm.excel.interfaces.ChartBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 24.04.2017.
 */
public class ExcelBuilder {

    public ExcelBuilder(){};

    private ExcelFiller getExcelFiller(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName) {
        int tableRowStart = 1;
        int tableCellStart = 0;
        switch (fileFormat) {
            case XLS:
                return new ExcelFiller(new HSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart);
            case XLSX:
                return new ExcelFiller(new XSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart);
            default:
                return new ExcelFiller(new XSSFWorkbook(), table, sheetName, tableRowStart, tableCellStart);
        }
    }

    private ChartBuilder getChartBuilder(ExcelFormat fileFormat, Workbook workbook, Coordinates coordinates_X, List<Coordinates> coordinates_Y){
        switch (fileFormat) {
            case XLS:
                return new HSSFChartBuilder(workbook, coordinates_X,coordinates_Y);
            case XLSX:
                return new XSSFChartBuilder(workbook, coordinates_X,coordinates_Y);
            default:
                return new XSSFChartBuilder(workbook, coordinates_X,coordinates_Y);
        }
    }

    public Workbook getWorkbook(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName){
        return getExcelFiller(fileFormat, table, sheetName).fillExcel();
    }

    public Workbook getWorkbookChart(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> table, String sheetName, String xColumnName, List<String> yColumnName){
        ExcelFiller excelFiller = getExcelFiller(fileFormat, table, sheetName);
        Workbook workbook = excelFiller.fillExcel();
        Coordinates coordinates_X = excelFiller.getCoordinatesOfColumns().get(xColumnName);
        ArrayList<Coordinates> coordinates_Y = new ArrayList<Coordinates>();
        for (String string : yColumnName){
            coordinates_Y.add(excelFiller.getCoordinatesOfColumns().get(string));
        }
        ChartBuilder chartBuilder = getChartBuilder(fileFormat, workbook, coordinates_X, coordinates_Y);
        chartBuilder.buildChart();
        return workbook;
    }

}
