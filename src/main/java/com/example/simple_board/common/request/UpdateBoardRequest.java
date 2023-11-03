package com.example.simple_board.common.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateBoardRequest(

        @Schema(description = "게시글 제목", example = "고양이 좋아")
        String title,
        @Schema(description = "작성자", example = "현실일상님")
        String author,
        @Schema(description = "비밀번호", example = "비밀번호")
        String password,
        @Schema(description = "게시물 내용", example = "근데 댕댕이가 더 좋아")
        String content
) {
}
