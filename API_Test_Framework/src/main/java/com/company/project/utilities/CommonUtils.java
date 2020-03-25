package com.company.project.utilities;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//**********************************************************************************************************
//Description: CommonUtils class can be used to mange the generic methods for the framework
//**********************************************************************************************************
public class CommonUtils {
    static Logger log = Logger.getLogger(CommonUtils.class.getName());

    private CommonUtils() {

    }

    public static String getEndpoint(String param) {
        String endpoinrUrl = "";
        switch (param.toLowerCase()) {
            case "manufacturer":
                endpoinrUrl = Constants.MANUFACTURERENDPOINT;
                break;
            case "main-types":
                endpoinrUrl = Constants.MAINTYPEENDPOINT;
                break;
            case "built-dates":
                endpoinrUrl = Constants.BUILTDATESENDPOINT;
                break;
        }
        return endpoinrUrl;
    }

    public static Integer getRandomNumber(Integer min, Integer max) {
        if (min > max) {
            throw new IllegalArgumentException("Maximum number must be greater than minimum number. Input file is empty");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static List<Map<String, String>> getInputFromExcel() throws IOException {

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testdata" + File.separator + "InputSheet.xlsx";

        List<Map<String, String>> getSheetData = new ArrayList<>();
        try {

            FileInputStream file = new FileInputStream(new File(filePath));

            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(file);

            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheet("Data");
            // Get iterator to all the rows in current sheet

            mySheet.forEach(row -> {
                HashMap<String, String> rowData = new LinkedHashMap<>();
                row.forEach(cell -> {

                    String cellValue = null;
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            cellValue = String.valueOf(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                            break;
                    }
                    rowData.put(cellValue.trim(), cellValue.trim());
                });
                getSheetData.add(rowData);
            });

            file.close();
        } catch (FileNotFoundException e) {
            log.error("Input file not found. " + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return getSheetData;
    }

    public static boolean isUniqueValues(ArrayList<Object> fieldValues) {
        boolean flag = false;
        List<Object> distinctElements = fieldValues.stream().distinct().collect(Collectors.toList());
        if (distinctElements.equals(fieldValues)) {
            flag = true;
        }
        return flag;
    }
}
