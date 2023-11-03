package com.example.simple_board.controller;

import com.example.simple_board.common.request.CreateBoardRequest;
import com.example.simple_board.common.request.UpdateBoardRequest;
import com.example.simple_board.common.response.BoardInfoResponse;
import com.example.simple_board.common.response.ErrorResponse;
import com.example.simple_board.common.response.GetBoardListResponse;
import com.example.simple_board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author ydong
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Board", description = "간단 게시글 API 만들기")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "get board", description = "게시글 하나를 상세히 조회하기")
    @Parameter(name = "id", description = "게시글 id", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "없는 게시글 조회", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/board/{id}")
    public ResponseEntity<BoardInfoResponse> getBoardInfo(@PathVariable Long id) {
        return null;
    }

    @Operation(summary = "get board list", description = "게시글 목록을 조회한다. 게시글 목록에는 게시글의 id가 담겨있다.")
    @Parameters({
            @Parameter(name = "size", description = "페이지 사이즈 default : 10", example = "10"),
            @Parameter(name = "page", description = "페이지 인덱스 default : 0", example = "0"),
            @Parameter(name = "sort", description = "정렬 기준으로 기본 설정은 게시물 생성일이다. default : createdAt", example = "createdAt"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "범위를 초과한 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/board")
    public ResponseEntity<GetBoardListResponse> getBoardList(@Schema(hidden = true)
                                                             @PageableDefault(sort = {"createdAt"})
                                                             Pageable pageable) {
        return null;
    }

    @Operation(summary = "create board", description = "게시글 생성하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "403", description = "비밀번호가 잘못됨", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "필수 정보 누락", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/board")
    public ResponseEntity<BoardInfoResponse> createBoard(@RequestBody CreateBoardRequest request) {
        return null;
    }

    @Operation(summary = "update board", description = "게시글의 내용을 일부 혹은 전체 수정하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "403", description = "비밀번호가 잘못됨", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PatchMapping("/board")
    public ResponseEntity<BoardInfoResponse> updateBoard(@RequestBody UpdateBoardRequest request) {
        return null;
    }

    @Operation(summary = "delete board", description = "게시글 내용 삭제하기")
    @Parameter(name = "id", description = "게시글 id", required = true)
    @Parameter(name = "password", description = "비밀번호", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "비밀번호가 잘못됨", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/board/{id}")
    public HttpEntity<?> deleteBoard(@PathVariable Long id,
                                     @RequestParam String password) {
        return ResponseEntity.EMPTY;
    }
}
