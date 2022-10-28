package mission.mission.domain.board.service;

import lombok.RequiredArgsConstructor;
import mission.mission.domain.board.dto.request.CreateBoardRequest;
import mission.mission.domain.board.dto.request.UpdateBoardRequest;
import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.repository.BoardRepository;
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

  public Long saveBoard(CreateBoardRequest request) {
    Team team = teamRepository.findById(request.getTeamId())
        .orElseThrow(RuntimeException::new);
    Board board = request.getBoardType().createBoard(request.getName(), team);
    return boardRepository.save(board).getId();
  }

  public void update(UpdateBoardRequest request) {
    Board board = boardRepository.findById(request.getId())
        .orElseThrow(RuntimeException::new);
    board.changeName(request.getName());

    Team team = teamRepository.findById(request.getTeamId())
        .orElseThrow(RuntimeException::new);
    board.addTeam(team);
  }

  public void delete(Long id) {
    Board board = boardRepository.findById(id)
        .orElseThrow(RuntimeException::new);

    board.removeTeam();
    boardRepository.deleteById(id);
  }
}
