package mission.mission.domain.board.repository;

import mission.mission.domain.board.entity.TeamBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamBoardRepository extends JpaRepository<TeamBoard, Long> {

}
