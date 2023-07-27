package com.capstone.moa.entity.enums;

public enum Interest {
    WEB,
    APP,
    IOT,
    AI,
    GAME,
    QNA,
    ETC,
    ;

    public static Interest find(String interest) {
        return Interest.valueOf(interest.toUpperCase());
    }
}
