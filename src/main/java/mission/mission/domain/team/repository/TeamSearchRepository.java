package mission.mission.domain.team.repository;

import java.util.List;
import mission.mission.domain.board.dto.request.SearchRequest;
import mission.mission.domain.team.entity.Team;

public interface TeamSearchRepository {

  List<Team> search(SearchRequest request);

}
