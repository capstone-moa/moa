package com.capstone.moa;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Enumerated(EnumType.STRING)
    private Job job;
}
