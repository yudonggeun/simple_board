package com.example.simple_board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board {

    private Long id;
    private String title;
    private String author;
    private String password;
    private String content;
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    private Board(String title, String author, String password, String content) {
        this.title = title;
        this.author = author;
        this.password = password;
        this.content = content;
    }

    public void update(String title, String author, String content) {
        if(title != null) this.title = title;
        if(author != null) this.author = author;
        if(content != null) this.content = content;
    }
}