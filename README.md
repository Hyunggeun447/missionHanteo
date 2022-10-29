# missionHanteo

| 기능                    | HTTP verbs | Path                   | 요청값                                                                         |
|-----------------------|------------|------------------------|----------------------------------------------------------------------------|
| 팀 생성                  | POST       | /api/v1/team           | {"name": string, "gender": string(MALE, FEMALE)}                           |
| 팀 수정                  | PUT        | /api/v1/team           | {"id": int, "name": string, "gender": string(MALE, FEMALE)}                |
| 팀 삭제                  | DELETE     | /api/v1/team           | ?teamId=int                                                                |
| 게시판 생성  <br/>(멤버, 공지) | POST       | /api/v1/board          | {"name": string, "teamId":int, "boardType":string(MEMBER, NOTICE)}         |
| 게시판 생성  <br/>(익명)     | PUT        | /api/v1/team/anonymous | ?teamId=int                                                                |
| 게시판 변경                | PUT        | /api/v1/board          | {"id":int, "name": string}                                                 |
| 게시판 삭제                | DELETE     | /api/v1/board          | ?boardId=5                                                                 |
| 조회                    | GET        | /api/v1/team/search    | {"gender": string, "teamName":string, "boardName": string, "boardId": int} |
