package com.company.dto;

import com.company.type.MarketplaceDistrictType;
import java.util.ArrayList;
import java.util.List;

public class DongCodeDto {

    private final String dongCode;
    private final MarketplaceDistrictType marketplaceDistrictType;
    private final List<String> districtNames = new ArrayList<>();
    private final String live;

    public DongCodeDto(String dongCodeLine) {
        String[] splitTokens = dongCodeLine.split("\t");
        this.dongCode = splitTokens[0];
        updateDistrictNames(splitTokens[1]);
        this.marketplaceDistrictType = MarketplaceDistrictType.findByDistrictNames(districtNames);
        this.live = splitTokens[2];
    }

    public void updateDistrictNames(String districtName) {
        String restDistrictName = districtName;

        List<List<String>> patterns = List.of(
            List.of("시 ", "도 "),
            List.of("시 ", "군 ", "구 "),
            List.of("읍 ", "면 ", "동 "),
            List.of("리 "));

        for (List<String> pattern : patterns) {
            restDistrictName = addDistrictNamesAndReturnRestDistrictName(restDistrictName, pattern);
        }
    }

    private String addDistrictNamesAndReturnRestDistrictName(String restDistrictName, List<String> patterns) {
        int index = -1;
        for (String pattern : patterns) {
            index = (restDistrictName.contains(pattern)) ? restDistrictName.indexOf(pattern) : index;
        }

        if (index == -1) {
            districtNames.add(restDistrictName);
            return "";
        }

        districtNames.add(restDistrictName.substring(0, index + 1));
        return restDistrictName.substring(index + 2);
    }

    public boolean isLive() {
        return live.equals("존재");
    }

    public String toCsv() {
        while (districtNames.size() < 4) {
            districtNames.add("");
        }
        return String.format("%s,%s,%s", dongCode, marketplaceDistrictType.name(), String.join(",", districtNames));
    }
}
