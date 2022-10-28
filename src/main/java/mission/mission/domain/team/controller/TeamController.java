package mission.mission.domain.team.controller;

import lombok.RequiredArgsConstructor;
import mission.mission.domain.team.dto.request.CreateTeamRequest;
import mission.mission.domain.team.dto.request.UpdateTeamRequest;
import mission.mission.domain.team.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {

  private final TeamService teamService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Long save(@RequestBody CreateTeamRequest request) {
    return teamService.save(request);
  }

  @PutMapping()
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody UpdateTeamRequest request) {
    teamService.update(request);
  }

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@RequestParam Long teamId) {
    teamService.delete(teamId);
  }

  @PutMapping("/anonymous")
  @ResponseStatus(HttpStatus.OK)
  public void getAnonymousBoard(@RequestParam Long teamId) {
    teamService.addAnonymousBoard(teamId);
  }

}
