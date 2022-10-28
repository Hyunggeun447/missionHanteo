package mission.mission.domain.board.controller;

import lombok.RequiredArgsConstructor;
import mission.mission.domain.board.dto.request.CreateBoardRequest;
import mission.mission.domain.board.dto.request.UpdateBoardRequest;
import mission.mission.domain.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {

  private final BoardService boardService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Long save(@RequestBody CreateBoardRequest request) {
    return boardService.save(request);
  }

  @PutMapping()
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody UpdateBoardRequest request) {
    boardService.update(request);
  }

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@RequestParam Long boardId) {
    boardService.delete(boardId);
  }

}
