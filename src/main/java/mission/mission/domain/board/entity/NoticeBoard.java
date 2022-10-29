package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_NAME;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_SEQ;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mission.mission.domain.common.exception.BadRequestException;
import mission.mission.domain.team.entity.Team;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@DiscriminatorValue("Notice")
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBoard extends Board{

  private String name = NOTICE_BOARD_NAME;
  private int seq = NOTICE_BOARD_SEQ;

  public NoticeBoard(Team team) {
    super();
  }

  @Override
  public void changeName(String name) {
    throw new BadRequestException("게시판명을 변경할 수 없습니다.");
  }

}
