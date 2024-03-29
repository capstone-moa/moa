package com.capstone.moa.entity;

import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.PostType;
import com.capstone.moa.entity.enums.Region;
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
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private Region region;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(Member member, String title, String content, String interest, String postType, String region) {
        this(member, title, content, Interest.find(interest), PostType.find(postType), Region.fromKrName(region));
    }

    private Post(Member member, String title, String content, Interest interest, PostType postType, Region region) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.interest = interest;
        this.postType = postType;
        this.region = region;
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
