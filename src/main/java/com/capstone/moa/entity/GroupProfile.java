package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origImgName;
    private String type;
    private String imgPath;
    private Long imgSize;
    @Builder
    public GroupProfile(String origImgName, String type, String imgPath, Long imgSize) {
        this.origImgName = origImgName;
        this.type = type;
        this.imgPath = imgPath;
        this.imgSize = imgSize;
    }
}
