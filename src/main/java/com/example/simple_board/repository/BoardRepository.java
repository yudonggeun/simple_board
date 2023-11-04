package com.example.simple_board.repository;

import com.example.simple_board.domain.Board;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BoardRepository extends R2dbcRepository<Board, Long> {

    @Query("SELECT ID FROM BOARD ORDER BY CREATED_AT DESC LIMIT :offset, :size")
    Flux<Long> findAll(long offset, int size);

    Mono<Void> deleteByIdAndPassword(Long id, String password);
}
