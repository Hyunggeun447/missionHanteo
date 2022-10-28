package mission.mission.domain.team.service;

import lombok.RequiredArgsConstructor;
import mission.mission.domain.board.entity.AnonymousBoard;
import mission.mission.domain.team.dto.request.CreateTeamRequest;
import mission.mission.domain.team.dto.request.UpdateTeamRequest;
import mission.mission.domain.team.entity.Team;
import mission.mission.domain.team.repository.TeamRepository;
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
    Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
    team.delete();
    teamRepository.deleteById(teamId);
  }

  public void addAnonymousBoard(Long teamId) {
    Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
    team.validateExistAnonymousBoard();
    team.addAnonymousBoard(anonymousBoard);
    team.changeExistAnonymousBoard(true);
  }

}
