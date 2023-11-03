package com.example.simple_board.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record CreateBoardRequest(
        @Schema(description = "게시글 제목", example = "즐거운 스프링")
        @NotEmpty
        String title,
        @Schema(description = "작성자", example = "현실일상")
        @NotEmpty
        String author,
        @Schema(description = "비밀번호", example = "1234")
        @NotEmpty
        String password,
        @Schema(description = "게시글 내용", example = "오늘은 공부하기 싫다.")
        @NotEmpty
        String content
) { }
