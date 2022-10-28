package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.BoardConstant.ANONYMOUS_BOARD_NAME;
import static mission.mission.domain.board.constant.BoardConstant.ANONYMOUS_BOARD_SEQ;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mission.mission.domain.team.entity.Team;

@Entity
@DiscriminatorValue("Anonymous")
@Getter
@NoArgsConstructor
public class AnonymousBoard extends Board {

  private String name = ANONYMOUS_BOARD_NAME;
  private int seq = ANONYMOUS_BOARD_SEQ;

  @Override
  public void addTeam(Team team) {
    return;
  }

  @Override
  public void changeName(String name) {
    return;
  }
}
