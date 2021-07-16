package com.company;

import com.company.dto.DongCodeDto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

public class DongCodeParser {

    private final String columnNames = "dong_code,sido_name,sigungu_name,eupmyeondong_name,ri_name";

    public Integer parse(String inputFileName, String outputFileName) {
        try {
            List<String> dongCodeLines = readLines(inputFileName);
            dongCodeLines.remove(0);

            return writeLines(outputFileName, dongCodeLines);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private List<String> readLines(String inputFileName) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(inputFileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "EUC-KR");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        try (fileInputStream; inputStreamReader; bufferedReader) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Integer writeLines(String outputFileName, List<String> dongCodeLines) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFileName, false);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        List<DongCodeDto> dtos = dongCodeLines.stream()
            .map(DongCodeDto::new)
            .filter(DongCodeDto::isLive)
            .collect(Collectors.toList());

        try (fileOutputStream; outputStreamWriter; bufferedWriter) {
            bufferedWriter.write(columnNames + "\n");

            for (DongCodeDto dto : dtos) {
                bufferedWriter.write(dto.toCsv() + "\n");
            }

            return dtos.size();
        } catch (Exception e) {
            e.getStackTrace();
            throw new RuntimeException();
        }
    }
}
