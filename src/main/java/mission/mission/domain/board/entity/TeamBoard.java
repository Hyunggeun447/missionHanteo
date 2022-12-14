package mission.mission.domain.board.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mission.mission.domain.team.entity.Team;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "team_board")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE team_board SET is_deleted = true WHERE id = ?")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamBoard implements Comparable<TeamBoard> {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  private int seq;

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  public TeamBoard(Team team, Board board) {
    addTeam(team);
    addBoard(board);
    this.seq = board.getSeq();
  }

  public void addTeam(Team team) {
    if (Objects.nonNull(this.team)) {
      this.team.getTeamBoardList().remove(this);
    }
    team.addTeamBoard(this);
    this.team = team;
  }

  public void addBoard(Board board) {
    if (Objects.nonNull(this.board)) {
      this.board.getTeamBoardList().remove(this);
    }
    board.addTeamBoard(this);
    this.board = board;
  }

  @Override
  public int compareTo(TeamBoard o) {
    if (this.getSeq() > o.getSeq()) {
      return 1;
    } else if (this.getSeq() < o.getSeq()) {
      return -1;
    } else if (this.getId() > o.getId()) {
      return 1;
    } else if (this.getId() < o.getId()) {
      return -1;
    }
    return 0;
  }
}
