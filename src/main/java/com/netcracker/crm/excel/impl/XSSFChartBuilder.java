package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.Coordinates;
import com.netcracker.crm.excel.interfaces.ChartBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * Created by AN on 17.04.2017.
 */
public class XSSFChartBuilder implements ChartBuilder {
    private Workbook workbook;
    private Sheet sheet;
    private Chart lineChart;
    private LineChartData data;
    private Coordinates coordinates_X;
    private List<Coordinates> coordinates_Y;
    private ChartAxis bottomAxis;
    private ValueAxis leftAxis;

    public XSSFChartBuilder(Workbook workbook, Coordinates coordinates_X, List<Coordinates> coordinates_Y) {
        this.workbook = workbook;
        this.coordinates_X = coordinates_X;
        this.coordinates_Y = coordinates_Y;
    }

    public void buildChart(){
        createChart();
        addData();
        plotChart();
    }

    private void createChart(){
        /* Create a drawing canvas on the worksheet */
        sheet = workbook.getSheetAt(0);
        Drawing drawing = sheet.createDrawingPatriarch();
        Coordinates coordinates = calculatePlotCoordinates();
        int leftTopCell = coordinates.getStartColumn();
        int leftTopRow = coordinates.getStartRow();
        int rightBottomCell = coordinates.getEndColumn();
        int rightBottomRow = coordinates.getEndRow();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, leftTopCell, leftTopRow, rightBottomCell, rightBottomRow);
        lineChart = drawing.createChart(anchor);
        ChartLegend legend = lineChart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        /* Create data for the chart */
        data = lineChart.getChartDataFactory().createLineChartData();
        /* Define chart AXIS */
        bottomAxis = lineChart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        leftAxis = lineChart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
    }

    private LineChartData addData(){
        int startCol = coordinates_X.getStartColumn();
        int endCol = coordinates_X.getEndColumn();
        int startRow = coordinates_X.getStartRow();
        int endRow = coordinates_X.getEndRow();
        String title;
        ChartDataSource<?> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(startRow, endRow, startCol, endCol));
        ChartDataSource<Number> ys;
        for(int i = 0; i < coordinates_Y.size(); i++){
            startCol = coordinates_Y.get(i).getStartColumn();
            endCol = coordinates_Y.get(i).getEndColumn();
            startRow = coordinates_Y.get(i).getStartRow();
            endRow = coordinates_Y.get(i).getEndRow();
            ys = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(startRow, endRow, startCol, endCol));
            /* Add chart data sources as data to the chart */
            title = sheet.getRow(startRow-1).getCell(startCol).toString();
            data.addSeries(xs, ys).setTitle(title);
        }
        return data;
    }

    private void plotChart(){
        lineChart.plot(data, bottomAxis, leftAxis);
    }

    private Coordinates calculatePlotCoordinates(){
        int modifier = 2 + coordinates_X.getEndRow() - coordinates_X.getStartRow();
        int startCol = 0;
        int startRow = coordinates_X.getEndRow() + 2;
        int endCol = modifier;
        int endRow = startRow + 15;
        return new Coordinates(startCol,startRow,endCol,endRow);
    }
}
