## GOAL
* "스파르타 익명 게시판 서버 만들기"
* Swagger UI를 이용한 API 자동화
* 비동기 서버 만들어보기

## REQUIRE
- 게시글 작성 기능
    - `제목`, `작성자명`, `비밀번호`, `작성 내용`, `작성일`을 저장할 수 있습니다.
    - 저장된 게시글의 정보를 반환 받아 확인할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
- 선택한 게시글 조회 기능
    - 선택한 게시글의 정보를 조회할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
- 게시글 목록 조회 기능
    - 등록된 게시글 전체를 조회할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
    - 조회된 게시글 목록은 작성일 기준 내림차순으로 정렬 되어있습니다.
- 선택한 게시글 수정 기능
    - 선택한 게시글의 `제목`, `작성자명`, `작성 내용`을 수정할 수 있습니다.
        - 서버에 게시글 수정을 요청할 때 `비밀번호`를 함께 전달합니다.
        - 선택한 게시글의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 수정이 가능합니다.
    - 수정된 게시글의 정보를 반환 받아 확인할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
- 선택한 게시글 삭제 기능
    - 선택한 게시글을 삭제할 수 있습니다.
        - 서버에 게시글 삭제를 요청할 때 `비밀번호`를 함께 전달합니다.
        - 선택한 게시글의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 삭제가 가능합니다.

## USECASE
![test](/dosc/usecase.svg)

## ERD
![erd](/dosc/spring.png)

## API
![api](/dosc/api.png)
[스프링부트를 가동하고 클릭하면 swagger를 통해서 API를 사용할 수 있습니다.](http://localhost:8080/swagger-ui/index.html)

## ISSUE
1. [Docker compose 버전 문제](https://github.com/spring-projects/spring-boot/issues/37982)
 
    2.23.0 버전에서 docker compose support에서 실행이 안되는 오류가 발생함.
    spring team에서 3.1.6 버전에서 이슈를 해결할 것으로 보임.

2. Pageable 파라미터 매핑 실패 오류

    원인 : WebFlux에서는 Pageble 인터페이스를 지원하지 않음.

    해결법
    1. Pageable 인터페이스 대신 커스텀 Dto 혹은 primitive type 사용하기
    2. Pageable 생성 커스텀 리졸버 만들기

3. R2DBC Pageable 지원하지 않음

    원인 : R2DBC에서도 역시 Pageable 지원하지 않는다. 오직 Sort만 지원하고 있다.
    
    해결 : Pageable을 그대로 사용하지 않고 offset과 size를 추출해서 사용함.

4. Swagger RestControllerAdvice exception handle 로직이 전체 API 문서에 각각 반영되는 현상
    
    해결 : @Hidden, @Operation(hidden = true)를 사용하여 의도하지 않은 문서화 제거

5. R2dbc 설정 오류

    원인 : R2dbc 숙련도 부족
    
    해결 : Github R2dbc 사용 코드 탐색
