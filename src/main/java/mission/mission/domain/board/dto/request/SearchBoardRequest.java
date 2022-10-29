package mission.mission.domain.board.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mission.mission.domain.team.value.Gender;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchBoardRequest {

  private Gender gender;
  private String teamName;
  private String boardName;
  private Long boardId;

}
