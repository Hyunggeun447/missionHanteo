package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.BoardConstant.MEMBER_BOARD_SEQ;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@DiscriminatorValue("Member")
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBoard extends Board {

  private int seq = MEMBER_BOARD_SEQ;

  public MemberBoard(String name) {
    super(name);
  }
}
