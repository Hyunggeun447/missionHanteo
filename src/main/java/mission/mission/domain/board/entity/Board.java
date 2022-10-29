package mission.mission.domain.board.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Table(name = "board")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Board {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  private String name;

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private List<TeamBoard> teamBoardList = new ArrayList<>();

  private int seq;

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  public Board(String name) {
    this.name = name;
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void addTeamBoard(TeamBoard teamBoard) {
    this.teamBoardList.add(teamBoard);
  }

}
