# be-chess

소프티어 부트캠프 2기 체스 프로젝트

## step 1 폰 생성

> JUnit을 활용한 테스트 코드 작성

- Pawn 테스트 클래스
    - Pawn 생성 테스트 메소드 추가
- Pawn 클래스
    - 생성자 및 getter 구현
- Pawn 생성 테스트 메소드 리팩토링

## step 2 체스판 생성

> JCF를 이용한 객체 관리

- Pawn 클래스 변경
    - 생성자 매개변수에 기본값 설정
    - 상수 추가
- Board 테스트 클래스
    - Board에 Pawn을 추가하는 테스트
    - Board에 Pawn 이외의 객체가 추가되지 않도록 테스트
- 리팩토링
    - 패키지 리팩토링
    - 중복 제거 ➡️ `@Before` 활용

## step 3 체스판 초기화

> JCF를 이용한 객체 관리

- 체스판 초기화
    - 색상별 Pawn 출력 문자 부여 & Pawn 열 조회 테스트 추가
    - 8*8 체스판 초기화(Pawn 기물 배치) 및 출력 기능 추가
- 게임 시작 및 종료 기능 추가
    - 체스 게임을 시작하는 Chess 클래스 추가
    - Scanner를 이용해 사용자에게 게임 시작/종료 명령어 입력 받기

## step 4 기물 배치

> Value Object의 활용
> <br>팩토리 메소드의 활용

**체스판의 모든 기물 초기화하기**

- `StringUtils` 클래스 추가
    - `\n` 문자 제거하기 ➡ `System.getProperty("line.separator")`
    - 유틸리티 메소드 구현 (`appendNewLine()`)
- Pawn ➡ Piece 변경 (일반화)
    - 색상과 이름으로 기물을 구분
    - 색상 구분 메소드 추가
    - value object로 구현 ➡ 팩토리 메소드를 통해서만 생성 가능 & 불변 객체
- 전체 체스판 출력 기능 추가 및 테스트