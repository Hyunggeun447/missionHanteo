package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_NAME;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_SEQ;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mission.mission.domain.team.entity.Team;

@Entity
@DiscriminatorValue("Notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBoard extends Board{

  private String name = NOTICE_BOARD_NAME;
  private int seq = NOTICE_BOARD_SEQ;

  public NoticeBoard(Team team) {
    super();
    addTeam(team);
  }

  @Override
  public void changeName(String name) {
    return;
  }

  @Override
  public void addTeam(Team team) {
    if (Objects.nonNull(this.getTeam())) {
      this.getTeam().getBoardList().remove(this);
    }
    team.addBoard(this);
    setTeam(team);
  }
}
