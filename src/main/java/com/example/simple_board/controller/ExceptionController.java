package com.example.simple_board.controller;

import com.example.simple_board.common.exception.DoesNotHaveAuthException;
import com.example.simple_board.common.exception.NotFoundBoardException;
import com.example.simple_board.common.response.FailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundBoardException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public FailResponse notFoundBoard(){
        return new FailResponse("게시글이 존재하지 않습니다.");
    }

    @ExceptionHandler(DoesNotHaveAuthException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public FailResponse doesNotHaveAuth(){
        return new FailResponse("권한이 없습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public FailResponse badRequest(){
        return new FailResponse("옳바르지 않은 요청입니다.");
    }
}
