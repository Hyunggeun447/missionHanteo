package mission.mission.domain.board.service;

import static mission.mission.domain.board.constant.BoardConstant.MEMBER_BOARD_SEQ;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_NAME;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_SEQ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import mission.mission.domain.board.dto.request.CreateBoardRequest;
import mission.mission.domain.board.dto.request.UpdateBoardRequest;
import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.entity.MemberBoard;
import mission.mission.domain.board.entity.NoticeBoard;
import mission.mission.domain.board.repository.BoardRepository;
import mission.mission.domain.board.value.BoardType;
import mission.mission.domain.team.entity.Team;
import mission.mission.domain.team.repository.TeamRepository;
import mission.mission.domain.team.value.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {

  @Autowired
  BoardService boardService;

  @Autowired
  BoardRepository boardRepository;

  @Autowired
  TeamRepository teamRepository;

  @AfterEach
  void clean() {
    teamRepository.deleteAll();
    boardRepository.deleteAll();
  }

  @Nested
  @DisplayName("saveBoard")
  class saveBoard {

    Team team;
    CreateBoardRequest createMemberRequest;
    CreateBoardRequest createNoticeRequest;

    @BeforeEach
    void setup() {
      team = teamRepository.save(new Team("엑소", Gender.MALE));

      createMemberRequest = CreateBoardRequest.builder()
          .name("백현")
          .teamId(team.getId())
          .boardType(BoardType.MEMBER)
          .build();

      createNoticeRequest = CreateBoardRequest.builder()
          .teamId(team.getId())
          .boardType(BoardType.NOTICE)
          .build();
    }

    @Test
    @DisplayName("성공: memberBoard")
    public void s_memberBoard() throws Exception {

      //when
      Long id = boardService.save(createMemberRequest);

      //then
      MemberBoard board = (MemberBoard) boardRepository.findById(id)
          .orElseThrow(RuntimeException::new);
      assertThat(createMemberRequest)
          .usingRecursiveComparison()
          .ignoringFields("teamId", "boardType")
          .isEqualTo(board);

      assertThat(board.getTeamBoardList().get(0).getTeam()).isEqualTo(team);
      assertThat(board.getSeq()).isEqualTo(MEMBER_BOARD_SEQ);
    }

    @Test
    @DisplayName("성공: noticeBoard")
    public void s_noticeBoard() throws Exception {

      //when
      Long id = boardService.save(createNoticeRequest);

      //then
      NoticeBoard board = (NoticeBoard) boardRepository.findById(id)
          .orElseThrow(RuntimeException::new);

      assertThat(board.getTeamBoardList().get(0).getTeam()).isEqualTo(team);
      assertThat(board.getName()).isEqualTo(NOTICE_BOARD_NAME);
      assertThat(board.getSeq()).isEqualTo(NOTICE_BOARD_SEQ);
    }

  }

  @Nested
  @DisplayName("update")
  class update {

    Team team1;
    Team team2;

    Long memberId;
    Long noticeId;

    @BeforeEach
    void setup() {
      team1 = teamRepository.save(new Team("엑소", Gender.MALE));
      team2 = teamRepository.save(new Team("블랙핑크", Gender.FEMALE));

      memberId = boardService.save(
          CreateBoardRequest.builder()
              .name("백현")
              .teamId(team1.getId())
              .boardType(BoardType.MEMBER)
              .build());

      noticeId = boardService.save(
          CreateBoardRequest.builder()
              .teamId(team1.getId())
              .boardType(BoardType.NOTICE)
              .build());
    }

    @Test
    @DisplayName("성공: memberBoard 변경 -> 이름 변경 가능, team 변경 가능")
    public void s_memberBoard() throws Exception {

      //given
      String newBoardName = "백현 -> 현백";
      Long newTeamId = team2.getId();

      UpdateBoardRequest request = UpdateBoardRequest.builder()
          .id(memberId)
          .name(newBoardName)
          .build();

      //when
      boardService.update(request);

      //then
      Board memberBoard = boardRepository.findById(memberId).orElseThrow(RuntimeException::new);
      assertThat(memberBoard.getName()).isEqualTo(newBoardName);
    }

    @Test
    @DisplayName("성공: noticeBoard 변경 -> 이름 변경 불가능, team 변경 가능")
    public void s_noticeBoard() throws Exception {

      //given
      String newBoardName = "백현 -> 현백";
      Long newTeamId = team2.getId();

      UpdateBoardRequest request = UpdateBoardRequest.builder()
          .id(noticeId)
          .name(newBoardName)
          .build();

      //when

      //then
      assertThrows(RuntimeException.class, () -> boardService.update(request));
    }

  }

  @Nested
  @DisplayName("delete")
  class delete {

    Team team1;

    Long memberId;
    Long noticeId;

    @BeforeEach
    void setup() {
      team1 = teamRepository.save(new Team("엑소", Gender.MALE));

      memberId = boardService.save(
          CreateBoardRequest.builder()
              .name("백현")
              .teamId(team1.getId())
              .boardType(BoardType.MEMBER)
              .build());

      noticeId = boardService.save(
          CreateBoardRequest.builder()
              .teamId(team1.getId())
              .boardType(BoardType.NOTICE)
              .build());
    }

    @Test
    @DisplayName("성공: 삭제")
    public void s() throws Exception {

      //given
      Board board = boardRepository.findById(memberId).orElseThrow(RuntimeException::new);

      //when
      boardService.delete(memberId);

      //then
      assertThrows(RuntimeException.class,
          () -> boardRepository.findById(memberId).orElseThrow(RuntimeException::new));
    }

  }

}