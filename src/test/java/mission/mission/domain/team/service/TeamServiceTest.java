package mission.mission.domain.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import mission.mission.domain.team.dto.request.CreateTeamRequest;
import mission.mission.domain.team.dto.request.UpdateTeamRequest;
import mission.mission.domain.team.entity.Team;
import mission.mission.domain.team.repository.TeamRepository;
import mission.mission.domain.team.value.Gender;
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

}