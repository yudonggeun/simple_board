package com.example.simple_board.repository;

import com.example.simple_board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b.id from Board b")
    Page<Long> findList(Pageable pageable);

    @Query("select b.password from Board b where b.id =:id")
    Optional<String> findPasswordById(@Param("id") Long id);
}
