package mission.mission.domain.board.value;

import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.entity.MemberBoard;
import mission.mission.domain.board.entity.NoticeBoard;
import mission.mission.domain.team.entity.Team;

public enum BoardType {
  MEMBER("멤버이름 게시판"){
    @Override
    public Board createBoard(String name, Team team) {
      return new MemberBoard(name);
    }
  },
  NOTICE("공지사항"){
    @Override
    public Board createBoard(String name, Team team) {
      team.validateExistNoticeBoard();
      team.changeExistNoticeBoard(true);
      return new NoticeBoard(team);
    }
  }
  ;

  private String description;

  BoardType(String description) {
    this.description = description;
  }

  public abstract Board createBoard(String name, Team team);

}
