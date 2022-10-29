package mission.mission.domain.team.repository;

import static mission.mission.domain.board.entity.QBoard.board;
import static mission.mission.domain.team.entity.QTeam.team;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import mission.mission.domain.board.dto.request.SearchRequest;
import mission.mission.domain.team.entity.Team;
import mission.mission.domain.team.value.Gender;
import org.springframework.util.ObjectUtils;

public class TeamSearchRepositoryImpl implements TeamSearchRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public TeamSearchRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Team> search(SearchRequest request) {

    return jpaQueryFactory.selectDistinct(team)
        .from(team)
        .leftJoin(board)
        .on(team.teamBoardList.any().board.eq(board))
        .where(
            boardIdEq(request.getBoardId()),
            boardNameEq(request.getBoardName()),
            teamNameEq(request.getTeamName()),
            genderEq(request.getGender())
        )
        .orderBy(
            team.gender.desc(),
            team.name.asc()
        )
        .fetch();
  }

  private BooleanExpression boardNameEq(String boardName) {
    return ObjectUtils.isEmpty(boardName) ? null : board.name.eq(boardName);
  }

  private BooleanExpression teamNameEq(String teamName) {
    return ObjectUtils.isEmpty(teamName) ? null : team.name.eq(teamName);
  }

  private BooleanExpression genderEq(Gender gender) {
    return ObjectUtils.isEmpty(gender) ? null : team.gender.eq(gender);
  }

  private BooleanExpression boardIdEq(Long boardId) {
    return ObjectUtils.isEmpty(boardId) ? null : board.id.eq(boardId);
  }
}
