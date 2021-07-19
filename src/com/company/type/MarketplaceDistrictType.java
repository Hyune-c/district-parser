package com.company.type;

import java.util.Arrays;
import java.util.List;

public enum MarketplaceDistrictType {
    SIDO(1),
    SIGUNGU(2),
    EUPMYEONDONG(3),
    RI(4);

    private final Integer legnth;

    MarketplaceDistrictType(Integer legnth) {
        this.legnth = legnth;
    }

    public static MarketplaceDistrictType findByDistrictNames(List<String> districtNames) {
        long notBlankSize = districtNames.stream()
            .filter(districtName -> !districtName.isBlank())
            .count();
        return Arrays.stream(MarketplaceDistrictType.values())
            .filter(type -> type.legnth == notBlankSize)
            .findFirst()
            .get();
    }
}
