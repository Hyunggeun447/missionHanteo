package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.SequenceConstant.NOTICE_BOARD_SEQ;

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

  private int seq = NOTICE_BOARD_SEQ;

  public NoticeBoard(String name, Team team) {
    super(name);
    addTeam(team);
  }

  @Override
  public void addTeam(Team team) {
    if (Objects.nonNull(this.getTeam())) {
      team.getBoardList().remove(this);
    }
    team.addBoard(this);
    setTeam(team);
  }
}
