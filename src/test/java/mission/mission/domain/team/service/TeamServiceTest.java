package mission.mission.domain.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import mission.mission.domain.board.entity.Board;
import mission.mission.domain.board.repository.BoardRepository;
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

  @AfterEach
  void clean() {
    teamRepository.deleteAll();
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

      //when
      teamService.delete(teamId);

      //then
      assertThrows(RuntimeException.class,
          () -> teamRepository.findById(teamId).orElseThrow(RuntimeException::new));

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
      assertThat(team.getBoardList().size()).isEqualTo(1);
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

  @Autowired
  BoardRepository boardRepository;

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

    Board board1 = team1.getBoardList().get(0);
    Board board2 = team2.getBoardList().get(0);

    assertThat(board1.getId()).isNotNull();
    assertThat(board1).isEqualTo(board2);

  }

}