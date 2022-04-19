# WirebarelyCodingTest

## 출처

* [테스트 출처](https://github.com/wirebarley/apply/blob/master/coding_test.md)

### 기능

---

* 외부 API 호출로 환율 데이터를 불러온다.
    * https://currencylayer.com 에 회원가입을 하면 무료로 사용 할수 있다.
    * 단, 무료 계정은 한달에 250번만 가능하니 참고해야 한다.
    * `2분마다 이벤트 스케줄러 호출`로 최신 정보를 업데이트 한다.
* 전달 받은 값을 converting 하여 Server를 호출한 Client 쪽에 전달한다.
    * [Front-End 소스코드](https://github.com/goodgood619/wirebarley_coding_test_front_web)
* `Error Handling 및 Log 기능` 추가
* 동일한 Origin으로 통신하기 위해 `CORS 설정`

<br/><br/>

### 설정

---

* Server의 기본 동작 port는 28080이다.
    * 변경을 원한다면, src/main/resources에서 application.yml 에서 변경 가능하다.
* 환율 회원가입을 하게 되면, secretKey가 발급되는데, application.yml에서 또한 설정 가능하다.