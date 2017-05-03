package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.ExcelFormat;
import com.netcracker.crm.excel.impl.DefaultExcelGenerator;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by AN on 16.04.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        LinkedHashMap<String,List<?>> table = new LinkedHashMap<String, List<?>>();
        List<Integer> order_id = new  ArrayList<Integer>();
        for (int i = 0; i < 10; i++){
            order_id.add(i);
        }
        table.put("Order_name", order_id);
        List<String> account_id = new  ArrayList<String>();
        account_id.add("prod1");
        account_id.add("prod2");
        account_id.add("prod3");
        account_id.add("prod4");
        account_id.add("prod5");
        account_id.add("prod6");
        account_id.add("prod7");
        account_id.add("pro");
        account_id.add("prod9");
        account_id.add("prod10");
        table.put("Account_id", account_id);
        List<Integer> cost = new  ArrayList<Integer>();
        for (int i = 0; i < 10; i++){
            cost.add((i+1)*10);
        }
        table.put("cost", cost);

        for (String s: table.keySet()) {
            System.out.println(s);
        }

        String reportName = "This report";
        String xColumnName = "Account_id";
        List<String> yColumnName = new ArrayList<String>();
        yColumnName.add("Order_name");
        yColumnName.add("cost");


        ExcelGenerator excelGenerator = new DefaultExcelGenerator();
        Workbook workbook = excelGenerator.generateExcel(ExcelFormat.XLSX,table,reportName,xColumnName,yColumnName);
        FileOutputStream fileOutputStream = new FileOutputStream("src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\"+reportName+"."+ExcelFormat.XLSX.toString());
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
