package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.Coordinates;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

/**
 * Created by AN on 26.04.2017.
 */
public interface ExcelFiller {
    public Workbook fillExcel();

    public Map<String, Coordinates> getCoordinatesOfColumns();

}
