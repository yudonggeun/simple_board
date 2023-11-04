package com.example.simple_board.controller;

import com.example.simple_board.common.exception.DoesNotHaveAuthException;
import com.example.simple_board.common.exception.NotFoundBoardException;
import com.example.simple_board.common.response.FailResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundBoardException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Mono<FailResponse> notFoundBoard() {
        return Mono.just(new FailResponse("게시글이 존재하지 않습니다."));
    }

    @ExceptionHandler(DoesNotHaveAuthException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Mono<FailResponse> doesNotHaveAuth() {
        return Mono.just(new FailResponse("권한이 없습니다."));
    }

    @ExceptionHandler({WebExchangeBindException.class, IllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Mono<FailResponse> badRequest() {
        return Mono.just(new FailResponse("옳바르지 않은 요청입니다."));
    }
}