package com.green.enums;

public enum BannerType {
    EVENT("EVENT"), //행사안내
    RECOMMEND("RECOMMEND"), //사서추천
    INFO("INFO"), //이용안내
    CULTURE("CULTURE"); //문화프로그램

    private final String type;
    BannerType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
