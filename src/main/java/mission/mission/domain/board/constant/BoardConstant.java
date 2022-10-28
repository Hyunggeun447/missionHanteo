package mission.mission.domain.board.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardConstant {

  public static final int NOTICE_BOARD_SEQ = 0;
  public static final int ANONYMOUS_BOARD_SEQ = 1;
  public static final int MEMBER_BOARD_SEQ = 2;

  public static final String ANONYMOUS_BOARD_NAME = "익명게시판";
  public static final String NOTICE_BOARD_NAME = "공지사항";

}
