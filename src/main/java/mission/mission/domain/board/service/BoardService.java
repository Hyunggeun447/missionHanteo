package mission.mission.domain.board.service;

import lombok.RequiredArgsConstructor;
import mission.mission.domain.board.dto.request.CreateBoardRequest;
import mission.mission.domain.board.dto.request.UpdateBoardRequest;
import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.entity.TeamBoard;
import mission.mission.domain.board.repository.BoardRepository;
import mission.mission.domain.common.exception.NotFoundException;
import mission.mission.domain.team.entity.Team;
import mission.mission.domain.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final TeamRepository teamRepository;

  public Long save(CreateBoardRequest request) {
    Team team = teamRepository.findById(request.getTeamId())
        .orElseThrow(() -> new NotFoundException("팀 카테고리를 찾을 수 없습니다."));
    Board board = request.getBoardType().createBoard(request.getName(), team);

    TeamBoard teamBoard = new TeamBoard(team, board);

    return boardRepository.save(board).getId();
  }

  public void update(UpdateBoardRequest request) {
    Board board = boardRepository.findById(request.getId())
        .orElseThrow(() -> new NotFoundException("게시판을 찾을 수 없습니다."));
    board.changeName(request.getName());
  }

  public void delete(Long boardId) {
    boardRepository.deleteById(boardId);
  }
}
