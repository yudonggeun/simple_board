package com.example.simple_board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
