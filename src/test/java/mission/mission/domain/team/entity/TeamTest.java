package mission.mission.domain.team.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import mission.mission.domain.team.value.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamTest {

  @Nested
  @DisplayName("생성")
  class create {

    String teamName = "엑소";
    Gender gender = Gender.MALE;

    @Test
    @DisplayName("성공")
    public void s() throws Exception {

      //when
      Team team = new Team(teamName, gender);

      //then
      assertThat(team.getGender()).isEqualTo(gender);
      assertThat(team.getName()).isEqualTo(teamName);
      assertThat(team.getExistNotice()).isFalse();
      assertThat(team.getIsDeleted()).isFalse();
    }

    @Test
    @DisplayName("실패: teamName = null")
    public void teamNameIsNull() throws Exception {

      //when
      teamName = null;

      //then
      assertThrows(RuntimeException.class, () -> new Team(teamName, gender));
    }

    @Test
    @DisplayName("실패: gender = null")
    public void genderIsNull() throws Exception {

      //when
      gender = null;

      //then
      assertThrows(RuntimeException.class, () -> new Team(teamName, gender));
    }

  }

  String teamName = "엑소";
  Gender gender = Gender.MALE;
  Team team;

  @Nested
  @DisplayName("수정")
  class update {

    @BeforeEach
    void setup() {
      team = new Team(teamName, gender);
    }

    @AfterEach
    void delete() {
      team = null;
    }

    @Nested
    @DisplayName("changeName")
    class changeName {

      @Test
      @DisplayName("성공")
      public void s() throws Exception {

        //given
        String newName = "newName";

        //when
        team.changeName(newName);

        //then
        assertThat(team.getName()).isEqualTo(newName);
      }

      @Test
      @DisplayName("실패: teamName = null")
      public void newNameIsNull() throws Exception {

        //when
        String newName = null;

        //then
        assertThrows(RuntimeException.class, () -> team.changeName(newName));
      }

      @Test
      @DisplayName("실패: teamName = empty")
      public void newNameIsEmpty() throws Exception {

        //when
        String newName = "";

        //then
        assertThrows(RuntimeException.class, () -> team.changeName(newName));
      }

    }

    @Nested
    @DisplayName("changeGender")
    class changeGender {

      @Test
      @DisplayName("성공")
      public void s() throws Exception {

        //given
        Gender newGender = Gender.FEMALE;

        //when
        team.changeGender(newGender);

        //then
        assertThat(team.getGender()).isEqualTo(newGender);
      }

      @Test
      @DisplayName("실패: newGender = null")
      public void newGenderIsNull() throws Exception {

        //when
        Gender newGender = null;

        //then
        assertThrows(RuntimeException.class, () -> team.changeGender(newGender));
      }

    }

    @Nested
    @DisplayName("changeExistNoticeBoard")
    class changeExistNoticeBoard {

      @Test
      @DisplayName("성공")
      public void s() throws Exception {

        //given
        Boolean aBoolean = Boolean.TRUE;

        //when
        team.changeExistNoticeBoard(aBoolean);

        //then
        assertThat(team.getExistNotice()).isEqualTo(aBoolean);
      }

      @Test
      @DisplayName("실패: aBoolean = null")
      public void aBooleanIsNull() throws Exception {

        //when
        Boolean aBoolean = null;

        //then
        assertThrows(RuntimeException.class, () -> team.changeExistNoticeBoard(aBoolean));
      }
    }

    @Nested
    @DisplayName("validateExistNoticeBoard")
    class validateExistNoticeBoard {

      @Test
      @DisplayName("성공: existNotice = false")
      public void s() throws Exception {

        //then
        team.validateExistNoticeBoard();
      }

      @Test
      @DisplayName("실패: existNotice = true")
      public void existNoticeIsTrue() throws Exception {

        //when
        Boolean aBoolean = Boolean.TRUE;
        team.changeExistNoticeBoard(aBoolean);

        //then
        assertThrows(RuntimeException.class, () -> team.validateExistNoticeBoard());
      }
    }

  }




}