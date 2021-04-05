# Java_Web_Development_Study

#### 자바를 이용한 웹 개발 공부
- 서블릿클래스 작성 및 배포방법
- 컨텍스트 초기화 매개변수, 서블릿 초기화 매개변수
- Servlet3.0의 Part API를 이용한 파일 업로드 및 다운로드
- JSP 스크립트 요소 (선언문, 스크립틀릿, 식)
- JSP 내장객체
- Session 객체 이용방법 
- Session Listener 를 이용한 세션 및 세션속성 변경 감지
- EL식 사용방법
- JSTL 을 이용한 JSP내의 자바코드 제거방법
- 커스텀 JSP 태그 이용방법
- 서블릿 필터 (압축필터, 로깅필터, 인증필터)
- Web Socket을 이용해 Tic-Tac-Toe 게임 구현하기

<hr>
 
#### 공부한 내용을 적용하여 고객관리서비스 애플리케이션 제작하기
- [Customer Support Project v1] 서블릿을 이용한 파일 업로드 및 다운로드 기능 추가
- [Customer Support Project v2] 서블릿에는 비즈니스 로직만을, JSP에는 프레젠테이션 코드를 작성하여 분리
- [Customer Support Project v3] Session을 이용해 로그인/로그아웃 기능 구현 + Session 활동 로그기록 확인가능
- [Customer Support Project v4] EL을 이용해 JSP에서 식을 사용한 코드 대체
- [Customer Support Project v5] JSTL을 이용해 JSP파일 내에서 자바코드를 완전히 제거
- [Customer Support Project v6] 커스텀 태그를 이용해 JSP내의 자바코드 완전제거 + css 를 이용한 웹페이지 UI 개선
- [Customer Support Project v7] 인증필터를 활용하여 보안이 필요한 페이지의 보안 확보
- [Customer Support Project v8] WebSocket을 이용한 채팅시스템 구현

<hr>

#### CRUD기능 연습을 위한 게시판 웹사이트 제작하기

<hr>

#### 서블릿을 이용한 HTTP이용 웹 서버 구축에 대한 공부 + 리팩토링 + Unit Test 연습
- 요구사항1 : index.html 응답하기
- 요구사항2 : GET 방식으로 회원가입하기
- 요구사항3 : POST 방식으로 회원가입하기
- 요구사항4 : 302 status code 적용하여 회원가입 후 리디렉션하기
- 요구사항5 : 로그인하기
- 요구사항6 : 로그인한 사용자 목록 출력하기
- 요구사항7 : CSS 지원하기
- HTTP 웹 서버 리팩토링 1단계 : 요청 데이터를 처리하는 로직 분리하기(HttpRequest) 및 Test
- HTTP 웹 서버 리팩토링 2단계 : 응답 데이터를 처리하는 로직 분리하기(HttpResponse) 및 Test
- HTTP 웹 서버 리팩토링 3단계 : Controller 인터페이스를 이용한 분기처리 제거
