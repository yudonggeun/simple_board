package com.example.simple_board.repository;

import com.example.simple_board.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @DisplayName("R2dbc를 이용한 entity 조회하기")
    @Test
    void findById() throws InterruptedException {
        // given
        boardRepository.save(Board.builder()
                        .author("유동근")
                        .title("책으로 배우는 코딩")
                        .password("1234")
                        .content("좋아요")
                        .build())
                .map(Board::getId)
        // when
                .flatMap(id -> boardRepository.findById(id))
        // then
                .as(StepVerifier::create)
                .assertNext(board -> {
                    log.info("result : {}", board);
                    assertThat(board.getAuthor()).isEqualTo("유동근");
                    assertThat(board.getPassword()).isEqualTo("1234");
                    assertThat(board.getContent()).isEqualTo("좋아요");
                    assertThat(board.getTitle()).isEqualTo("책으로 배우는 코딩");
                })
                .verifyComplete();
    }
}