package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zipcode;
    private String streetAdr;
    private String detailAdr;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", unique = true)
    private Group group;

    @Builder
    public Address(String zipcode, String streetAdr, String detailAdr, Group group) {
        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
        this.group = group;
    }
}
