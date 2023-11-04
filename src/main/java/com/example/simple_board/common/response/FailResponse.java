package com.example.simple_board.common.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FailResponse(
        @Schema(description = "에러 원인", example = "오늘은 일 안해요!!")
        String message
) {
}