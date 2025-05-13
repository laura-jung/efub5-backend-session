package com.practice.blog.comment.domain;

import com.practice.blog.account.entity.Account;
import com.practice.blog.global.domain.BaseEntity;
import com.practice.blog.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=1000)
    private String content;

    //Comment-Account 매핑
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "account_id", updatable = false)
    private Account writer;

    //Comment-Post 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @Builder
    public Comment(String content, Account writer, Post post) {
        this.content = content;
        this.writer = writer;
        this.post = post;
    }
}
