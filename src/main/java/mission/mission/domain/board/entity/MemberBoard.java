package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.BoardConstant.MEMBER_BOARD_SEQ;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mission.mission.domain.team.entity.Team;

@Entity
@DiscriminatorValue("Member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBoard extends Board {

  private int seq = MEMBER_BOARD_SEQ;

  public MemberBoard(String name) {
    super(name);
  }
}
