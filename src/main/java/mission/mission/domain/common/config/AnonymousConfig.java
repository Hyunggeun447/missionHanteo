package mission.mission.domain.common.config;

import mission.mission.domain.board.entity.AnonymousBoard;
import mission.mission.domain.board.repository.BoardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnonymousConfig {

  @Bean
  public AnonymousBoard anonymous(BoardRepository boardRepository) {
    return boardRepository.save(new AnonymousBoard());
  }

}
