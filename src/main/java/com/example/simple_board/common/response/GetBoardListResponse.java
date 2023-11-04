package com.example.simple_board.common.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetBoardListResponse(
        @Schema(description = "요청한 페이지 index", example = "1")
        long page,
        @Schema(description = "페이지 크기", example = "10")
        int size,
        @Schema(description = "총 게시글 수", example = "1000")
        long totalElement,
        @Schema(description = "총 게시글 페이지 수", example = "100")
        long totalPages,
        @Schema(description = "조회된 게시글 id 정보 목록")
        List<Long> boardIdList
) {
}