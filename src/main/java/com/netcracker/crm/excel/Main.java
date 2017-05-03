package com.netcracker.crm.excelService;

import java.util.*;

/**
 * Created by AN on 16.04.2017.
 */
public class Main {
    public static void main(String[] args) {
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
        yColumnName.add("Order_id");
        yColumnName.add("cost");


        ExcelGenerator excelGenerator = new ExcelGenerator.DefaultExcelGenerator();
       // String fullFileName = excelGenerator.generateExcel(ExcelFormat.XLSX,table,reportName,xColumnName,yColumnName);
        String fullFileName = excelGenerator.generateExcel(ExcelFormat.XLS,table,reportName);
    }
}
