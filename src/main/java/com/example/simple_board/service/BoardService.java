package com.example.simple_board.service;

import com.example.simple_board.common.exception.DoesNotHaveAuthException;
import com.example.simple_board.common.exception.NotFoundBoardException;
import com.example.simple_board.common.request.CreateBoardRequest;
import com.example.simple_board.common.request.UpdateBoardRequest;
import com.example.simple_board.common.response.BoardInfoResponse;
import com.example.simple_board.common.response.GetBoardListResponse;
import com.example.simple_board.domain.Board;
import com.example.simple_board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardInfoResponse createBoard(CreateBoardRequest request) {

        Board board = boardRepository.save(Board.builder()
                .title(request.title())
                .author(request.author())
                .content(request.content())
                .password(request.password())
                .build());

        return BoardInfoResponse.of(board);
    }

    public BoardInfoResponse getBoardInfo(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(NotFoundBoardException::new);

        return BoardInfoResponse.of(board);
    }

    public GetBoardListResponse getBoardList(Pageable pageable) {
        Page<Long> pages = boardRepository.findList(pageable);

        if (pages.isEmpty()) throw new NotFoundBoardException();

        return new GetBoardListResponse(
                pageable.getPageNumber(),
                pages.getSize(),
                pages.getTotalElements(),
                pages.getTotalPages(),
                pages.getContent());
    }

    public BoardInfoResponse update(UpdateBoardRequest request) {
        Board board = boardRepository.findById(request.id())
                .orElseThrow(NotFoundBoardException::new);

        checkAuth(board.getPassword(), request.password());

        board.update(request.title(), request.author(), request.content());
        return BoardInfoResponse.of(board);
    }

    private void checkAuth(String boardPassword, String password) {
        if (!boardPassword.equals(password)) throw new DoesNotHaveAuthException();
    }

    public void delete(Long id, String password) {
        String boardPassword = boardRepository.findPasswordById(id)
                .orElseThrow(NotFoundBoardException::new);

        checkAuth(boardPassword, password);
        boardRepository.deleteById(id);
    }
}