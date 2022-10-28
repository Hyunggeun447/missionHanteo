package mission.mission.domain.board.service;

import static mission.mission.domain.board.constant.BoardConstant.MEMBER_BOARD_SEQ;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_NAME;
import static mission.mission.domain.board.constant.BoardConstant.NOTICE_BOARD_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

import mission.mission.domain.board.dto.request.CreateBoardRequest;
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

  @Nested
  @DisplayName("saveMemberBoard")
  class saveMemberBoard {

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

    @AfterEach
    void clean() {
      teamRepository.deleteAll();
      boardRepository.deleteAll();
    }

    @Test
    @DisplayName("성공: memberBoard")
    public void s_memberBoard() throws Exception {

      //when
      Long id = boardService.saveBoard(createMemberRequest);

      //then
      MemberBoard board = (MemberBoard) boardRepository.findById(id).orElseThrow(RuntimeException::new);
      assertThat(createMemberRequest)
          .usingRecursiveComparison()
          .ignoringFields("teamId", "boardType")
          .isEqualTo(board);

      assertThat(board.getTeam()).isEqualTo(team);
      assertThat(board.getSeq()).isEqualTo(MEMBER_BOARD_SEQ);
    }

    @Test
    @DisplayName("성공: noticeBoard")
    public void s_noticeBoard() throws Exception {

      //when
      Long id = boardService.saveBoard(createNoticeRequest);

      //then
      NoticeBoard board = (NoticeBoard) boardRepository.findById(id).orElseThrow(RuntimeException::new);

      assertThat(board.getTeam()).isEqualTo(team);
      assertThat(board.getName()).isEqualTo(NOTICE_BOARD_NAME);
      assertThat(board.getSeq()).isEqualTo(NOTICE_BOARD_SEQ);
    }

  }


}