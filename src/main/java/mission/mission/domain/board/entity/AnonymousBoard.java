package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.BoardConstant.ANONYMOUS_BOARD_NAME;
import static mission.mission.domain.board.constant.BoardConstant.ANONYMOUS_BOARD_SEQ;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@DiscriminatorValue("Anonymous")
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
public class AnonymousBoard extends Board {

  private String name = ANONYMOUS_BOARD_NAME;
  private int seq = ANONYMOUS_BOARD_SEQ;

  public AnonymousBoard() {
    super();
  }

  @Override
  public void changeName(String name) {
    return;
  }
}
