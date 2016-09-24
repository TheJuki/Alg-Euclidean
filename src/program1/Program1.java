package program1;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Program1 {

    private static final int NUM_INTEGERS = 100;

    private static class GCDTableRow
    {
        private int number1;
        private int number2;
        private int gcd;
        private double timeSpent;

        public GCDTableRow() {}

        GCDTableRow(int number1, int number2)
        {
            this.number1 = number1;
            this.number2 = number2;
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

    public static void main(String[] args) {

        List<GCDTableRow> gcdTable = new ArrayList<>();

        long startTime = 0;
        long endTime = 0;

        for(int i = 0; i < NUM_INTEGERS; i++)
        {
            gcdTable.add(new GCDTableRow((int)(Math.random()*1001), (int)(Math.random()*1001)));
            //System.out.print("\nRow: " + (i+1) + " - GCD (" + gcdTable.get(i).getNumber1() + ", " + gcdTable.get(i).getNumber2() + ")");
        }

        System.out.print("\nEuclid Algorithm 1");

        for(int i = 0; i < NUM_INTEGERS; i++) {
            startTime = System.nanoTime();
            gcdTable.get(i).setGcd(EuclidAlgorithm1(gcdTable.get(i).getNumber1(), gcdTable.get(i).getNumber2()));
            endTime = System.nanoTime();
            gcdTable.get(i).setTimeSpent((endTime - startTime) / 1000000.0);
            System.out.print("\nRow: " + (i + 1) +
                    " - GCD (" + gcdTable.get(i).getNumber1() + ", " +
                    gcdTable.get(i).getNumber2() + ") = " +
                    gcdTable.get(i).getGcd() +
                    " - Time spent: " + gcdTable.get(i).getTimeSpent() + " ms");
        }

        System.out.print("\nEuclid Algorithm 2");

        for(int i = 0; i < NUM_INTEGERS; i++)
        {
            startTime = System.nanoTime();
            gcdTable.get(i).setGcd(EuclidAlgorithm2(gcdTable.get(i).getNumber1(), gcdTable.get(i).getNumber2()));
            endTime = System.nanoTime();
            gcdTable.get(i).setTimeSpent((endTime - startTime) / 1000000.0);
            System.out.print("\nRow: " + (i+1) +
                    " - GCD (" + gcdTable.get(i).getNumber1() + ", " +
                    gcdTable.get(i).getNumber2() + ") = " +
                    gcdTable.get(i).getGcd() +
                    " - Time spent: " + gcdTable.get(i).getTimeSpent() + " ms");
        }

        writeExcelFile();
    }

    private static void writeExcelFile()
    {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        Map<String, Object[]> data = new HashMap<String, Object[]>();
        data.put("1", new Object[] {"Emp No.", "Name", "Salary"});
        data.put("2", new Object[] {1d, "John", 1500000d});
        data.put("3", new Object[] {2d, "Sam", 800000d});
        data.put("4", new Object[] {3d, "Dean", 700000d});

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof Date)
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }

        try {
            FileOutputStream out =
                    new FileOutputStream(new File("src/new.xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        return a;
    }

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

        return a;
    }
}
