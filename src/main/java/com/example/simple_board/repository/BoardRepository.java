package com.example.simple_board.repository;

import com.example.simple_board.domain.Board;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface BoardRepository extends R2dbcRepository<Board, Long> {
}
