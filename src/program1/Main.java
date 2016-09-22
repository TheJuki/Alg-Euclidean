package program1;

import java.util.ArrayList;
import java.util.List;

public class Main {

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
