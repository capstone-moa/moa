package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 500, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(Member member, String title, String content, String interest) {
        this(member, title, content, Interest.find(interest));
    }

    private Post(Member member, String title, String content, Interest interest) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.interest = interest;
    }

    public boolean isSameWriter(String email) {
        return Objects.equals(this.member.getEmail(), email);
    }

    public void modify(String title, String content, String interest) {
        this.title = title;
        this.content = content;
        this.interest = Interest.find(interest);
    }
}
