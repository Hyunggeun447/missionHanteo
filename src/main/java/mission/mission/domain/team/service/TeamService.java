package mission.mission.domain.team.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mission.mission.domain.board.dto.request.SearchBoardRequest;
import mission.mission.domain.board.dto.response.BoardResponse;
import mission.mission.domain.board.dto.response.Response;
import mission.mission.domain.board.dto.response.TeamResponse;
import mission.mission.domain.board.entity.AnonymousBoard;
import mission.mission.domain.board.entity.TeamBoard;
import mission.mission.domain.team.dto.request.CreateTeamRequest;
import mission.mission.domain.team.dto.request.UpdateTeamRequest;
import mission.mission.domain.team.entity.Team;
import mission.mission.domain.team.repository.TeamRepository;
import mission.mission.domain.team.value.Gender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class TeamService {

  private final TeamRepository teamRepository;
  private final AnonymousBoard anonymousBoard;

  public Long save(CreateTeamRequest request) {
    Team team = new Team(request.getName(), request.getGender());
    return teamRepository.save(team).getId();
  }

  public void update(UpdateTeamRequest request) {
    Team team = teamRepository.findById(request.getId()).orElseThrow(RuntimeException::new);
    team.changeName(request.getName());
    team.changeGender(request.getGender());
  }

  public void delete(Long teamId) {
    teamRepository.deleteById(teamId);
  }

  public void addAnonymousBoard(Long teamId) {
    Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
    team.validateExistAnonymousBoard();
    TeamBoard teamBoard = new TeamBoard(team, anonymousBoard);
    team.changeExistAnonymousBoard(true);
  }

  @Transactional(readOnly = true)
  public Response searchResult(SearchBoardRequest request) {
    Map<Gender, List<TeamResponse>> teamResponseMap = new HashMap<>();

    List<Team> teamList = teamRepository.search(request);

    teamList.forEach(
        team -> {
          TeamResponse teamResponse = TeamResponse.builder()
              .teamName(team.getName())
              .board(team.getTeamBoardList()
                  .stream().sorted(TeamBoard::compareTo)
                  .map(
                      tb ->
                          BoardResponse.builder()
                              .boardId(tb.getBoard().getId())
                              .boardName(tb.getBoard().getName())
                              .build()
                  ).collect(Collectors.toList())
              ).build();

          if (!teamResponseMap.containsKey(team.getGender())) {
            teamResponseMap.put(team.getGender(), new ArrayList<>());
          }
          List<TeamResponse> teamResponses = teamResponseMap.get(team.getGender());
          teamResponses.add(teamResponse);
        }
    );
    return Response.builder()
        .result(teamResponseMap)
        .build();
  }

}
