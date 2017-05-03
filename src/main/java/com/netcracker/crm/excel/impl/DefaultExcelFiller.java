package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.Coordinates;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

/**
 * Created by AN on 16.04.2017.
 */
public class ExcelFiller {
    private Workbook workbook;
    private Map<String, List<?>> table;
    private Map<String, Coordinates> coordinatesOfColumns;
    private List<String> titles;
    private String sheetName;
    private int rowStart;
    private int cellStart;


    public ExcelFiller(Workbook workbook, Map<String, List<?>> table, String sheetName, int rowStart, int cellStart) {
        this.workbook = workbook;
        this.table = table;
        this.sheetName = sheetName;
        this.rowStart = rowStart;
        this.cellStart = cellStart;
        titles = new ArrayList<String>(table.keySet());

    }

     public Workbook fillExcel(){
        createSheet(sheetName);
        Sheet sheet = workbook.getSheet(sheetName);
        setTitles(sheet);
        fillData(sheet);
        return workbook;
    }

    private void createSheet(String sheetName){
        workbook.createSheet(sheetName);
    }

    private void setTitles(Sheet sheet){
        Row row = sheet.createRow(rowStart);
        row.createCell(cellStart).setCellValue("№");
        String title;
        for(int i = 0; i < titles.size(); i++){
            title = titles.get(i);
            row.createCell(i+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(i+1+cellStart).setCellValue(title);
        }
    }

    private void fillData(Sheet sheet){
        int numOfColumns = table.size();
        int numOfRows = table.get(titles.get(0)).size();
        Row row;
        String currentTitle;
        Object currentValue;
        for(int i = 0; i < numOfRows; i++){
            row = sheet.createRow(i+1+rowStart);
            row.createCell(cellStart).setCellValue(i+1);
            for (int j = 0; j < numOfColumns; j++){
                currentTitle = titles.get(j);
                currentValue = table.get(currentTitle).get(i);
                setValueFromTable(currentTitle, currentValue, j, i, row);
            }

        }
        calculateCoordinates();
    }

    private void setValueFromTable(String currentTitle, Object currentValue, int titleIndex, int valueIndex, Row row){
        currentTitle = titles.get(titleIndex);
        currentValue = table.get(currentTitle).get(valueIndex);
        if(currentValue instanceof String){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue((String) currentValue);
        }
        else if(currentValue instanceof Integer){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(titleIndex+1+cellStart).setCellValue((Integer) currentValue);
        }
        else if(currentValue instanceof Double){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(titleIndex+1+cellStart).setCellValue((Double) currentValue);
        }
        else if(currentValue instanceof Calendar){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue((Calendar) currentValue);
        }
        else if(currentValue instanceof Date){
            row.createCell(titleIndex+1+cellStart).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(titleIndex+1+cellStart).setCellValue((Date) currentValue);
        }
    }

    private void calculateCoordinates(){
        int startColumn;
        int startRow;
        int endColumn;
        int endRow;
        coordinatesOfColumns = new HashMap<String, Coordinates>();
        String currentTitle;
        for (int i = 0; i < titles.size(); i++){
            currentTitle = titles.get(i);
            startColumn = i+ cellStart + 1;
            startRow = rowStart + 1;
            endColumn = startColumn;
            endRow = rowStart + 1 +  table.get(currentTitle).size();
            coordinatesOfColumns.put(currentTitle, new Coordinates(startColumn,startRow,endColumn,endRow));
        }
    }

    public Map<String, Coordinates> getCoordinatesOfColumns() {
        return coordinatesOfColumns;
    }

}
