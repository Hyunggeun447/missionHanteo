package mission.mission.domain.board.entity;

import static mission.mission.domain.board.constant.BoardConstant.ANONYMOUS_BOARD_SEQ;
import static mission.mission.domain.board.constant.BoardConstant.MEMBER_BOARD_SEQ;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_NAME;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_SEQ;
import static org.assertj.core.api.Assertions.*;

import mission.mission.domain.team.entity.Team;
import mission.mission.domain.team.value.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardTest {

  Team team;

  @Autowired
  AnonymousBoard anonymousBoard;

  @BeforeEach
  void setup() {
    //given
    String teamName = "엑소";
    Gender gender = Gender.MALE;

    //when
    team = new Team(teamName, gender);
  }

  @AfterEach
  void delete() {
    team = null;
  }

  @Test
  @DisplayName("성공: memberBoard")
  public void s_memberBoard() throws Exception {

    //given
    String name = "첸";

    //when
    MemberBoard memberBoard = new MemberBoard(name);
    TeamBoard teamBoard = new TeamBoard(team, memberBoard);

    //then
    assertThat(team.getTeamBoardList().size()).isEqualTo(1);
    assertThat(memberBoard.getName()).isEqualTo(name);
    assertThat(memberBoard.getTeamBoardList()).contains(teamBoard);
    assertThat(teamBoard.getTeam()).isEqualTo(team);
    assertThat(memberBoard.getSeq()).isEqualTo(MEMBER_BOARD_SEQ);
    assertThat(memberBoard.getIsDeleted()).isFalse();
  }

  @Test
  @DisplayName("성공: noticeBoard")
  public void s_noticeBoard() throws Exception {

    //given

    //when
    NoticeBoard noticeBoard = new NoticeBoard(team);
    TeamBoard teamBoard = new TeamBoard(team, noticeBoard);


    //then
    assertThat(team.getTeamBoardList().size()).isEqualTo(1);
    assertThat(noticeBoard.getName()).isEqualTo(NOTICE_BOARD_NAME);
    assertThat(noticeBoard.getTeamBoardList()).contains(teamBoard);
    assertThat(teamBoard.getTeam()).isEqualTo(team);
    assertThat(noticeBoard.getSeq()).isEqualTo(NOTICE_BOARD_SEQ);
    assertThat(noticeBoard.getIsDeleted()).isFalse();
  }

  @Test
  @DisplayName("성공: anonymousBoard")
  public void s_anonymousBoard() throws Exception {

    //given
    TeamBoard teamBoard = new TeamBoard(team, anonymousBoard);

    //then
    assertThat(team.getTeamBoardList().size()).isEqualTo(1);
    assertThat(anonymousBoard.getName()).isEqualTo("익명게시판");
    assertThat(anonymousBoard.getTeamBoardList()).contains(teamBoard);
    assertThat(team.getTeamBoardList()).contains(teamBoard);
    assertThat(anonymousBoard.getSeq()).isEqualTo(ANONYMOUS_BOARD_SEQ);
    assertThat(anonymousBoard.getIsDeleted()).isFalse();
  }

}