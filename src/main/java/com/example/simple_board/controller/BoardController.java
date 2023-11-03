package com.example.simple_board.controller;

import com.example.simple_board.common.request.CreateBoardRequest;
import com.example.simple_board.common.request.UpdateBoardRequest;
import com.example.simple_board.common.response.BoardInfoResponse;
import com.example.simple_board.common.response.FailResponse;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
            @ApiResponse(responseCode = "404", description = "없는 게시글 조회", content = @Content(schema = @Schema(implementation = FailResponse.class)))
    })
    @GetMapping(value = "/board/{id}")
    public BoardInfoResponse getBoardInfo(@PathVariable Long id) {
        return boardService.getBoardInfo(id);
    }

    @Operation(summary = "get board list", description = "게시글 목록을 조회한다. 게시글 목록에는 게시글의 id가 담겨있다.")
    @Parameters({
            @Parameter(name = "size", description = "페이지 사이즈 default : 10", example = "10"),
            @Parameter(name = "page", description = "페이지 인덱스 default : 0", example = "0"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "범위를 초과한 요청", content = @Content(schema = @Schema(implementation = FailResponse.class)))
    })
    @GetMapping("/board")
    public GetBoardListResponse getBoardList(@Schema(hidden = true)
                                                             @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
                                                             Pageable pageable) {
        return boardService.getBoardList(pageable);
    }

    @Operation(summary = "create board", description = "게시글 생성하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "필수 정보 누락", content = @Content(schema = @Schema(implementation = FailResponse.class))),
            @ApiResponse(responseCode = "403", description = "비밀번호가 잘못됨", content = @Content(schema = @Schema(implementation = FailResponse.class))),
    })
    @PostMapping("/board")
    public BoardInfoResponse createBoard(@Validated @RequestBody CreateBoardRequest request) {
        return boardService.createBoard(request);
    }

    @Operation(summary = "update board", description = "게시글의 내용을 일부 혹은 전체 수정하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "403", description = "비밀번호가 잘못됨", content = @Content(schema = @Schema(implementation = FailResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시글 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = FailResponse.class))),
    })
    @PatchMapping("/board")
    public BoardInfoResponse updateBoard(@RequestBody UpdateBoardRequest request) {
        return boardService.update(request);
    }

    @Operation(summary = "delete board", description = "게시글 내용 삭제하기")
    @Parameter(name = "id", description = "게시글 id", required = true, example = "1")
    @Parameter(name = "password", description = "비밀번호", required = true, example = "1234")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "비밀번호가 잘못됨", content = @Content(schema = @Schema(implementation = FailResponse.class))),
            @ApiResponse(responseCode = "404", description = "이미 삭제된 게시글", content = @Content(schema = @Schema(implementation = FailResponse.class))),
    })
    @DeleteMapping("/board/{id}")
    public HttpEntity<?> deleteBoard(@PathVariable Long id,
                                     @RequestParam String password) {
        boardService.delete(id, password);
        return ResponseEntity.EMPTY;
    }
}
