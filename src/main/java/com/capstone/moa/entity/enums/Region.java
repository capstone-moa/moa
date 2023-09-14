package com.capstone.moa.entity.enums;

public enum Region {
    NONE("지역 선택"),
    REGION1("서울"),
    REGION2("경기/인천"),
    REGION3("대전/충청/강원"),
    REGION4("부산/대구/울산/경상"),
    REGION5("광주/전라/제주"),
    ONLINE("온라인");

    private String krName;

    Region(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }

    public static Region fromKrName(String input) {
        for (Region region : Region.values()) {
            if (region.getKrName().equals(input)) {
                return region;
            }
        }
        return null;
    }
}
