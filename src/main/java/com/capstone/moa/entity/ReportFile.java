package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long groupId;
    private String title;
    private String fileName;
    private String contentType;

    @Lob
    @Column(length = 100000000)
    private byte[] fileData;

    @Builder
    public ReportFile(Long groupId, String title, String fileName, String contentType, byte[] fileData) {
        this.groupId = groupId;
        this.title = title;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileData = fileData;
    }
}
