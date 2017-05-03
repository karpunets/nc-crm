package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.ExcelFormat;
import com.netcracker.crm.excel.interfaces.ExcelGenerator;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 25.04.2017.
 */
public class DefaultExcelGenerator implements ExcelGenerator {
    private String defaultRootPath = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\";
    public String generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName){
        Workbook workbook = new ExcelBuilder().getWorkbook(fileFormat,data,reportName);
        FileOutputStream os;
        String fullFileName = defaultRootPath+reportName+"."+fileFormat.toString();
        try {
            os = new FileOutputStream(fullFileName);
            workbook.write(os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullFileName;
    }

    public String generateExcel(ExcelFormat fileFormat, LinkedHashMap<String, List<?>> data, String reportName, String xAxisColumn, List<String> yAxisColumn){
        Workbook workbook = new ExcelBuilder().getWorkbookChart(fileFormat,data,reportName,xAxisColumn,yAxisColumn);
        FileOutputStream os;
        try {
            os = new FileOutputStream(defaultRootPath+reportName+fileFormat.toString());
            workbook.write(os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultRootPath+reportName+fileFormat.toString();
    }
}
