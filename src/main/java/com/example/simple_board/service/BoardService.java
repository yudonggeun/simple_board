package com.example.simple_board.service;

import com.example.simple_board.common.exception.DoesNotHaveAuthException;
import com.example.simple_board.common.exception.NotFoundBoardException;
import com.example.simple_board.common.request.CreateBoardRequest;
import com.example.simple_board.common.request.UpdateBoardRequest;
import com.example.simple_board.common.response.BoardInfoResponse;
import com.example.simple_board.common.response.GetBoardListResponse;
import com.example.simple_board.domain.Board;
import com.example.simple_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final R2dbcEntityTemplate template;

    public Mono<BoardInfoResponse> getBoardInfo(Long id) {
        return boardRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundBoardException()))
                .map(BoardInfoResponse::of);
    }

    public Mono<GetBoardListResponse> getBoardList(Pageable pageable) {
        Mono<Long> count = boardRepository.count();

        return boardRepository.findAll(pageable.getOffset(), pageable.getPageSize())
                .collectList()
                .zipWith(count)
                .flatMap(tuple -> {
                            var contents = tuple.getT1();
                            long totalElements = tuple.getT2();
                            long totalPages = totalElements / pageable.getPageSize();

                            if (contents.isEmpty()) return Mono.error(new NotFoundBoardException());
                            return Mono.just(new GetBoardListResponse(
                                    pageable.getPageNumber(),
                                    contents.size(),
                                    totalElements,
                                    totalPages,
                                    contents
                            ));
                        }
                );
    }

    public Mono<BoardInfoResponse> createBoard(CreateBoardRequest request) {
        return boardRepository.save(
                        Board.builder()
                                .title(request.title())
                                .author(request.author())
                                .content(request.content())
                                .password(request.password())
                                .build()
                )
                .map(BoardInfoResponse::of);
    }

    public Mono<BoardInfoResponse> update(UpdateBoardRequest req) {
        return boardRepository
                .findById(req.id())
                .switchIfEmpty(Mono.error(new NotFoundBoardException()))
                .flatMap(board -> {
                            if (!board.getPassword().equals(req.password()))
                                return Mono.error(new DoesNotHaveAuthException());

                            board.update(req.title(), req.author(), req.content());
                            return template.update(board);
                        }
                )
                .map(BoardInfoResponse::of);
    }


    public Mono<Void> delete(Long id, String password) {
        return boardRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new NotFoundBoardException()))
                .flatMap(board -> {
                    if (!board.getPassword().equals(password)) return Mono.error(new DoesNotHaveAuthException());
                    return boardRepository.deleteById(id);
                });
    }
}
