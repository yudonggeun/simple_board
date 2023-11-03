package com.example.simple_board.common.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record BoardInfoResponse(
        @Schema(description = "게시글 id", example = "1")
        Long id,
        @Schema(description = "게시글 제목", example = "즐거운 스프링")
        String title,
        @Schema(description = "작성자", example = "현실일상")
        String author,
        @Schema(description = "비밀번호", example = "1234")
        String password,
        @Schema(description = "게시글 내용", example = "오늘은 공부하기 싫다.")
        String content,
        @Schema(description = "생성일")
        LocalDateTime createdAt
) {
}
