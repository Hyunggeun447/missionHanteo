package mission.mission.domain.board.service;

import lombok.RequiredArgsConstructor;
import mission.mission.domain.board.dto.request.CreateBoardRequest;
import mission.mission.domain.board.dto.request.UpdateBoardRequest;
import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.entity.MemberBoard;
import mission.mission.domain.board.entity.NoticeBoard;
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

  public Long saveMemberBoard(CreateBoardRequest request) {
    Team team = teamRepository.findById(request.getTeamId())
        .orElseThrow(RuntimeException::new);
    MemberBoard memberBoard = new MemberBoard(request.getName(), team);
    return boardRepository.save(memberBoard).getId();
  }

  public Long saveNoticeBoard(CreateBoardRequest request) {
    Team team = teamRepository.findById(request.getTeamId())
        .orElseThrow(RuntimeException::new);

    team.validateExistNoticeBoard();

    NoticeBoard noticeBoard = new NoticeBoard(request.getName(), team);
    team.changeExistNoticeBoard(true);
    return boardRepository.save(noticeBoard).getId();
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
    boardRepository.deleteById(id);
  }
}
