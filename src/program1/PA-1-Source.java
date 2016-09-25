package program1;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

// PA-1-Group-Huang-Kirk-Parten

class Program1 {

    // Number of integer pairs to generate
    private static final int NUM_INTEGERS = 100;

    // The GCD Table Row Class
    private static class GCDTableRow
    {
        // Number 1
        private int number1;

        // Number 2
        private int number2;

        // GCD
        private int gcd;

        // Time spent
        private double timeSpent;

        public GCDTableRow() {}

        GCDTableRow(int number1, int number2)
        {
            this.number1 = number1;
            this.number2 = number2;
        }

        Object[] getRowAsArray()
        {
            return new Object[] {number1, number2, gcd, timeSpent};
        }

        public int getNumber1() {
            return number1;
        }

        public void setNumber1(int number1) {
            this.number1 = number1;
        }

        public int getNumber2() {
            return number2;
        }

        public void setNumber2(int number2) {
            this.number2 = number2;
        }

        public int getGcd() {
            return gcd;
        }

        public void setGcd(int gcd) {
            this.gcd = gcd;
        }

        public double getTimeSpent() {
            return timeSpent;
        }

        public void setTimeSpent(double timeSpent) {
            this.timeSpent = timeSpent;
        }
    }

    // Main
    public static void main(String[] args) {

        // The list of the Euclid Algorithm 1 results
        List<GCDTableRow> gcdTableAlg1 = new ArrayList<>();
        // The list of the Euclid Algorithm 2 results
        List<GCDTableRow> gcdTableAlg2 = new ArrayList<>();

        long startTime = 0;
        long endTime = 0;

        int num1 = 0;
        int num2 = 0;

        // Generate the random integer pairs for each list
        for(int i = 0; i < NUM_INTEGERS; i++)
        {
            num1 = (int)(Math.random()*1000 + 1);
            num2 = (int)(Math.random()*1000 + 1);
            gcdTableAlg1.add(new GCDTableRow(num1, num2));
            gcdTableAlg2.add(new GCDTableRow(num1, num2));
        }

        System.out.print("\nEuclid Algorithm 1\n");
        System.out.print("------------------------------");

        // Perform the Euclid Algorithm 1 on all pairs
        for(int i = 0; i < NUM_INTEGERS; i++) {
            startTime = System.nanoTime();
            gcdTableAlg1.get(i).setGcd(EuclidAlgorithm1(gcdTableAlg1.get(i).getNumber1(), gcdTableAlg1.get(i).getNumber2()));
            endTime = System.nanoTime();
            gcdTableAlg1.get(i).setTimeSpent((endTime - startTime) / 1000000.0);
            System.out.print("\nRow: " + (i + 1) +
                    " - GCD (" + gcdTableAlg1.get(i).getNumber1() + ", " +
                    gcdTableAlg1.get(i).getNumber2() + ") = " +
                    gcdTableAlg1.get(i).getGcd() +
                    " - Time spent: " + gcdTableAlg1.get(i).getTimeSpent() + " ms");
        }

        System.out.print("\n\n\nEuclid Algorithm 2\n");
        System.out.print("------------------------------");

        // Perform the Euclid Algorithm 2 on all pairs
        for(int i = 0; i < NUM_INTEGERS; i++)
        {
            startTime = System.nanoTime();
            gcdTableAlg2.get(i).setGcd(EuclidAlgorithm2(gcdTableAlg2.get(i).getNumber1(), gcdTableAlg2.get(i).getNumber2()));
            endTime = System.nanoTime();
            gcdTableAlg2.get(i).setTimeSpent((endTime - startTime) / 1000000.0);
            System.out.print("\nRow: " + (i+1) +
                    " - GCD (" + gcdTableAlg2.get(i).getNumber1() + ", " +
                    gcdTableAlg2.get(i).getNumber2() + ") = " +
                    gcdTableAlg2.get(i).getGcd() +
                    " - Time spent: " + gcdTableAlg2.get(i).getTimeSpent() + " ms");
        }

        // Write the excel file - this also generates the statistics
        writeExcelFile(gcdTableAlg1, gcdTableAlg2);
    }

    // Writes the Excel file
    private static void writeExcelFile(List<GCDTableRow> gcdTableAlg1,  List<GCDTableRow> gcdTableAlg2)
    {
        // Create new Excel workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // Create sheets
        HSSFSheet originalEuclidResultsSheet = workbook.createSheet("Original_Euclid_Results");
        HSSFSheet improvedEuclidResultsSheet = workbook.createSheet("Improved_Euclid_Results");
        HSSFSheet originalEuclidStatisticsSheet = workbook.createSheet("Original_Euclid_Statistics");
        HSSFSheet improvedEuclidStatisticsSheet = workbook.createSheet("Improved_Euclid_Statistics");

        // Cell style - Adds a thin border around each cell
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        Map<String, Object[]> data = new HashMap<String, Object[]>();

        // Set header rows for results sheets
        addResultsHeaderRow(style, originalEuclidResultsSheet);
        addResultsHeaderRow(style, improvedEuclidResultsSheet);

        // Set header rows and create cells for statistics sheets
        addStatisticsHeaderRowAndData(gcdTableAlg1.stream().map(GCDTableRow::getTimeSpent).collect(Collectors.toList()), style, originalEuclidStatisticsSheet);
        addStatisticsHeaderRowAndData(gcdTableAlg2.stream().map(GCDTableRow::getTimeSpent).collect(Collectors.toList()), style, improvedEuclidStatisticsSheet);

        // Gather rows for Original_Euclid_Results
        for(int i = 0; i < gcdTableAlg1.size(); i++) {
            data.put(String.valueOf(i + 1), gcdTableAlg1.get(i).getRowAsArray());
        }

        // Create cells for Original_Euclid_Results
        createCells(data, style, originalEuclidResultsSheet);

        data = new HashMap<String, Object[]>();

        // Gather rows for Improved_Euclid_Results
        for(int i = 0; i < gcdTableAlg2.size(); i++) {
            data.put(String.valueOf(i + 1), gcdTableAlg2.get(i).getRowAsArray());
        }

        // Create cells for Improved_Euclid_Results
        createCells(data, style, improvedEuclidResultsSheet);


        try {
            // Write Excel file
            FileOutputStream out =
                    new FileOutputStream(new File("Euclid_Results_And_Statistics.xls"));
            workbook.write(out);
            out.close();
            System.out.println("\n\nEuclid_Results_And_Statistics.xls document created successfully");

            JOptionPane.showMessageDialog(null, "Euclid Algorithms finished and Euclid_Results_And_Statistics.xls document created successfully",
                    "PA-1",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Euclid Algorithms finished; however, Euclid_Results_And_Statistics.xls creation failed: \n" + e.getMessage(),
                    "PA-1",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Create each row and cell of a data map
    private static void createCells(Map<String, Object[]> data, HSSFCellStyle style, HSSFSheet sheet)
    {
        Set<String> keyset = data.keySet();
        int rownum = 1;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                cell.setCellStyle(style);
                if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }
    }

    // Create the header row for a Results sheet
    private static void addResultsHeaderRow(HSSFCellStyle style, HSSFSheet sheet)
    {
        //Set Column Widths
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);

        // Create header row
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Number One");
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Number Two");
        headerCell = headerRow.createCell(2);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Their GCD");
        headerCell = headerRow.createCell(3);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Time Spent (Milliseconds)");
    }

    // Create the header row(s) and data cells for a Statistics sheet
    // Performs the statistics
    private static void addStatisticsHeaderRowAndData(List<Double> data, HSSFCellStyle style, HSSFSheet sheet)
    {
        //Set Column Widths
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);

        // Find median
        Collections.sort(data);
        double median;
        int middle = data.size() / 2;

        if (data.size() % 2 == 1) {
            median = data.get(middle);
        } else {
            median = (data.get(middle - 1) + data.get(middle)) / 2.0;
        }

        // Create header row
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Statistics");
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Milliseconds");

        // Create first column "Headers"
        headerRow = sheet.createRow(1);
        headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Maximum Time");
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue(Collections.max(data)); // Get Maximum
        headerRow = sheet.createRow(2);
        headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Minimum  Time");
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue(Collections.min(data)); // Get Minimum
        headerRow = sheet.createRow(3);
        headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Average Time");
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue(data.stream().mapToDouble(a -> a).average().orElse(0)); // Get Average
        headerRow = sheet.createRow(4);
        headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Median Time");
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue(median); // Set Medium
    }

    // The Euclid Algorithm 1 method
    private static int EuclidAlgorithm1(int a, int b)
    {
        int quotient = 0;
        int remainder = -1;

        while (0 != remainder)
        {
            quotient = a / b;
            remainder = a - quotient * b;
            a = b;
            b = remainder;
        }

        // The GCD is now a
        return a;
    }

    // The Euclid Algorithm 2 method
    private static int EuclidAlgorithm2(int a, int b)
    {
        // Guarantee a >= b > 0
        if(a > 0 && b > 0)
        {
            if(a <= b)
            {
                // Swap a and b
                int temp = a;
                a = b;
                b = temp;
            }
        }
        else
        {
            return 0;
        }

        int remainder = -1;

        while (0 != remainder)
        {
           remainder = a - b;
            if(remainder >= b) {
                remainder = remainder - b;
                if (remainder >= b) {
                    remainder = remainder - b;
                    if (remainder >= b) {
                        remainder = a - (b * (a / b));
                    }
                }
            }
            a = b;
            b = remainder;
        }

        // The GCD is now a
        return a;
    }
}
