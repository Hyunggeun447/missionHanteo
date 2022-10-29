package mission.mission.domain.team.service;

import static mission.mission.domain.board.value.BoardType.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import mission.mission.domain.board.dto.request.CreateBoardRequest;
import mission.mission.domain.board.dto.request.SearchRequest;
import mission.mission.domain.board.dto.response.Response;
import mission.mission.domain.board.dto.response.TeamResponse;
import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.repository.BoardRepository;
import mission.mission.domain.board.service.BoardService;
import mission.mission.domain.board.value.BoardType;
import mission.mission.domain.team.dto.request.CreateTeamRequest;
import mission.mission.domain.team.dto.request.UpdateTeamRequest;
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
class TeamServiceTest {

  @Autowired
  TeamService teamService;

  @Autowired
  TeamRepository teamRepository;

  @Autowired
  BoardRepository boardRepository;

  @Autowired
  BoardService boardService;

  @AfterEach
  void clean() {
    teamRepository.deleteAll();
    boardRepository.deleteAll();
  }

  @Nested
  @DisplayName("save")
  class save {

    String teamName = "엑소";
    Gender gender = Gender.MALE;
    CreateTeamRequest request = CreateTeamRequest.builder()
        .name(teamName)
        .gender(gender)
        .build();

    @Test
    @DisplayName("성공")
    public void s() throws Exception {

      //given
      Long id = teamService.save(request);

      //when
      Team team = teamRepository.findById(id).orElseThrow(RuntimeException::new);

      //then
      assertThat(request)
          .usingRecursiveComparison()
          .isEqualTo(team);
    }

  }

  @Nested
  @DisplayName("update")
  class update {

    Long teamId;

    @BeforeEach
    void setup() {
      String teamName = "엑소";
      Gender gender = Gender.MALE;
      CreateTeamRequest request = CreateTeamRequest.builder()
          .name(teamName)
          .gender(gender)
          .build();

      teamId = teamService.save(request);
    }

    @Test
    @DisplayName("성공")
    public void s() throws Exception {

      //given
      UpdateTeamRequest request = UpdateTeamRequest.builder()
          .id(teamId)
          .name("newTeamName")
          .gender(Gender.FEMALE)
          .build();

      //when
      teamService.update(request);

      //then
      Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
      assertThat(request)
          .usingRecursiveComparison()
          .isEqualTo(team);
    }

  }

  @Nested
  @DisplayName("delete")
  class delete {

    Long teamId;

    Board board1;
    Board board2;
    Team team;

    @BeforeEach
    void setup() {
      String teamName = "엑소";
      Gender gender = Gender.MALE;
      CreateTeamRequest request = CreateTeamRequest.builder()
          .name(teamName)
          .gender(gender)
          .build();

      teamId = teamService.save(request);

      team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
      board1 = boardRepository.save(MEMBER.createBoard("name", team));
      board2 = boardRepository.save(MEMBER.createBoard("name", team));
    }

    @Test
    @DisplayName("성공")
    public void s() throws Exception {

      //when
      teamService.delete(teamId);

      //then
      assertThrows(RuntimeException.class,
          () -> teamRepository.findById(teamId).orElseThrow(RuntimeException::new));
      assertThat(board1.getTeamBoardList()).isEmpty();
      assertThat(board2.getTeamBoardList()).isEmpty();
    }

  }

  @Nested
  @DisplayName("addAnonymousBoard")
  class addAnonymousBoard {

    Long teamId;

    @BeforeEach
    void setup() {
      String teamName = "엑소";
      Gender gender = Gender.MALE;
      CreateTeamRequest request = CreateTeamRequest.builder()
          .name(teamName)
          .gender(gender)
          .build();

      teamId = teamService.save(request);
    }

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      teamService.addAnonymousBoard(teamId);

      //then
      Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
      assertThat(team.getTeamBoardList().size()).isEqualTo(1);
      assertThat(team.getExistAnonymous()).isTrue();

    }

    @Test
    @DisplayName("실패")
    public void f() throws Exception {

      //when
      teamService.addAnonymousBoard(teamId);

      //then
      assertThrows(RuntimeException.class, () -> teamService.addAnonymousBoard(teamId));
    }

  }

  @Test
  @DisplayName("싱글톤 유지 확인 테스트")
  public void anonymousBoardIsSingleTon() throws Exception {

    //given
    String teamName1 = "엑소";
    Gender gender1 = Gender.MALE;
    CreateTeamRequest request1 = CreateTeamRequest.builder()
        .name(teamName1)
        .gender(gender1)
        .build();

    Long teamId1 = teamService.save(request1);
    teamService.addAnonymousBoard(teamId1);

    //when
    String teamName2 = "블랙핑크";
    Gender gender2 = Gender.FEMALE;
    CreateTeamRequest request2 = CreateTeamRequest.builder()
        .name(teamName2)
        .gender(gender2)
        .build();

    Long teamId2 = teamService.save(request2);
    teamService.addAnonymousBoard(teamId2);

    //then
    Team team1 = teamRepository.findById(teamId1).orElseThrow(RuntimeException::new);
    Team team2 = teamRepository.findById(teamId2).orElseThrow(RuntimeException::new);

    Board board1 = team1.getTeamBoardList().get(0).getBoard();
    Board board2 = team2.getTeamBoardList().get(0).getBoard();

    assertThat(board1.getId()).isNotNull();
    assertThat(board1).isEqualTo(board2);

  }

  @Nested
  @DisplayName("searchResult")
  class searchResult {

    Long teamId;
    Long teamId2;

    @BeforeEach
    void setup() {
      String teamName = "엑소";
      Gender gender = Gender.MALE;
      CreateTeamRequest request = CreateTeamRequest.builder()
          .name(teamName)
          .gender(gender)
          .build();

      teamId = teamService.save(request);

      String teamName2 = "블랙핑크";
      Gender gender2 = Gender.FEMALE;
      CreateTeamRequest request2 = CreateTeamRequest.builder()
          .name(teamName2)
          .gender(gender2)
          .build();

      teamId2 = teamService.save(request2);

      boardService.save(CreateBoardRequest.builder()
          .name("백현")
          .teamId(teamId)
          .boardType(MEMBER)
          .build());

      boardService.save(CreateBoardRequest.builder()
          .name("시우민")
          .teamId(teamId)
          .boardType(MEMBER)
          .build());

      boardService.save(CreateBoardRequest.builder()
          .name("로제")
          .teamId(teamId2)
          .boardType(MEMBER)
          .build());

      boardService.save(CreateBoardRequest.builder()
          .name("제니")
          .teamId(teamId2)
          .boardType(MEMBER)
          .build());

    }

    @Test
    @DisplayName("성공: ")
    public void s() throws Exception {

      //given
      SearchRequest request = SearchRequest.builder()
          .boardId(null)
          .boardName(null)
          .gender(null)
          .teamName(null)
          .build();

      //when
      Response response = teamService.searchResult(request);

      //then

      assertThat(response.getResult().containsKey(Gender.MALE)).isTrue();
      assertThat(response.getResult().containsKey(Gender.FEMALE)).isTrue();

      List<TeamResponse> teamResponses = response.getResult().get(Gender.MALE);
      assertThat(teamResponses.get(0).getTeamName()).isEqualTo("엑소");

      assertThat(teamResponses.get(0).getBoard().get(0).getBoardName()).isEqualTo("백현");

    }

    @Test
    @DisplayName("성공: 성별 카테고리")
    public void s_gender() throws Exception {

      //given
      SearchRequest request = SearchRequest.builder()
          .boardId(null)
          .boardName(null)
          .gender(Gender.MALE)
          .teamName(null)
          .build();

      //when
      Response response = teamService.searchResult(request);

      //then
      assertThat(response.getResult().containsKey(Gender.MALE)).isTrue();
      assertThat(response.getResult().containsKey(Gender.FEMALE)).isFalse();
    }


    @Test
    @DisplayName("성공: teamName")
    public void s_teamName() throws Exception {

      //given
      SearchRequest request = SearchRequest.builder()
          .boardId(null)
          .boardName(null)
          .gender(null)
          .teamName("블랙핑크")
          .build();

      //when
      Response response = teamService.searchResult(request);

      //then

      assertThat(response.getResult().containsKey(Gender.MALE)).isFalse();
      assertThat(response.getResult().containsKey(Gender.FEMALE)).isTrue();

      List<TeamResponse> teamResponses = response.getResult().get(Gender.FEMALE);
      assertThat(teamResponses.get(0).getTeamName()).isEqualTo("블랙핑크");

    }

    @Test
    @DisplayName("성공: boardName")
    public void s_boardName() throws Exception {

      //given
      SearchRequest request = SearchRequest.builder()
          .boardId(null)
          .boardName("제니")
          .gender(null)
          .teamName(null)
          .build();

      //when
      Response response = teamService.searchResult(request);

      //then

      assertThat(response.getResult().containsKey(Gender.MALE)).isFalse();
      assertThat(response.getResult().containsKey(Gender.FEMALE)).isTrue();

      List<TeamResponse> teamResponses = response.getResult().get(Gender.FEMALE);
      assertThat(teamResponses.get(0).getTeamName()).isEqualTo("블랙핑크");

      assertThat(teamResponses.get(0).getBoard().get(1).getBoardName()).isEqualTo("제니");

    }
  }

}