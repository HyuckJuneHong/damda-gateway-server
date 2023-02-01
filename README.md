# damda-gateway-server
> MSA 기반의 사이드 프로젝트입니다.
> Gateway Server 입니다.

- 프로젝트 기간 : 2023.02.01 - continue

# 프로젝트 기록
- 23/02/01
    1. Gateway Server - 알게된 점 : Spring Cloud API Gateway는 비동기 방식으로 서비스가 실행된다.
    2. Eureka Server에 등록
    3. Gateway Custom Pre Filter 구축 -> logging
      ```
        알게된 점: Mono
        * Mono : Webflux로 비동기 방식의 서버에서 단일값을 전달할 때 사용한다.
      ```

