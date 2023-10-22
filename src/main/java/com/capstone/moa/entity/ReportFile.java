package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportFile extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    private String title;
    private String fileName;
    private String contentType;

    @Lob
    @Column(length = 100000000)
    private byte[] fileData;

    @Builder
    public ReportFile(Group group, String title, String fileName, String contentType, byte[] fileData) {
        this.group = group;
        this.title = title;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileData = fileData;
    }
}
