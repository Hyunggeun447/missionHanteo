package mission.mission.domain.config;

import mission.mission.domain.board.entity.AnonymousBoard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnonymousConfig {

  @Bean
  public AnonymousBoard anonymous() {
    return new AnonymousBoard();
  }

}