package com.company;

public class Main {

    public static void main(String[] args) {
        String inputFileName = "dongcode.txt";
        String outputFileName = "parse_dongcode.csv";

        DongCodeParser dongCodeParser = new DongCodeParser();
        int rowCount = dongCodeParser.parse(inputFileName, outputFileName);
        System.out.println("### dongCode count: " + rowCount);
    }
}
