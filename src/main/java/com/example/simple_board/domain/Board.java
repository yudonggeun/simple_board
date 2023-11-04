package com.example.simple_board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Table(name = "BOARD")
public class Board {

    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String password;
    @Column
    private String content;
    @Column
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