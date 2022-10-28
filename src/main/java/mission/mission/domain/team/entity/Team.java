package mission.mission.domain.team.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mission.mission.domain.board.entity.AnonymousBoard;
import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.entity.NoticeBoard;
import mission.mission.domain.team.value.Gender;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

@Entity
@Table(name = "team")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE team SET is_deleted = true WHERE id = ?")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "Gender")
  private Gender gender;

  @Column(name = "exist_notice")
  private Boolean existNotice = Boolean.FALSE;

  @Column(name = "exist_anonymous")
  private Boolean existAnonymous = Boolean.FALSE;

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "team")
  private List<Board> boardList = new ArrayList<>();

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  public Team(String name, Gender gender) {
    Assert.hasText(name, "need name");
    Assert.notNull(gender, "need gender");

    this.name = name;
    this.gender = gender;
  }

  public void changeName(String name) {
    Assert.hasText(name, "need name");

    this.name = name;
  }

  public void changeGender(Gender gender) {
    Assert.notNull(gender, "need gender");

    this.gender = gender;
  }

  public void changeExistNoticeBoard(Boolean aBoolean) {
    Assert.notNull(aBoolean, "need aBoolean");

    this.existNotice = aBoolean;
  }
  public void changeExistAnonymousBoard(Boolean aBoolean) {
    Assert.notNull(aBoolean, "need aBoolean");

    this.existAnonymous = aBoolean;
  }

  public void validateExistNoticeBoard() {
    if (this.existNotice) {
      throw new RuntimeException();
    }
  }

  public void validateExistAnonymousBoard() {
    if (this.existAnonymous) {
      throw new RuntimeException();
    }
  }

  public void addBoard(Board board) {
    this.boardList.add(board);
  }

  public void addAnonymousBoard(AnonymousBoard anonymousBoard) {
    this.boardList.add(anonymousBoard);
  }

}
