# damda-gateway-server
> MSA 기반의 사이드 프로젝트입니다.
> Gateway Server 입니다.

- 프로젝트 기간 : 2023.02.01 - continue

# 프로젝트 기록
- 23/02/01
    1. Gateway Server 구축 - 알게된 점 : Spring Cloud API Gateway는 비동기 방식으로 서비스가 실행된다.
    2. Eureka Server에 등록
    3. Gateway Custom Pre, Post Filter 구축 - 알게된 점 - Mono란 Webflux로 비동기 방식의 서버에서 단일값을 전달할 때 사용한다.
       (By 토비): 토비님은 WebFlux를 아래와 같은 용도로 사용하는 것을 추천했다.
         -비동기 : Non-Blocking Reactive 개발에 사용.
         -효율적으로 동작하는 고성능 Web Application 개발.
         -Service 간 호출이 많은 MSA에 적합.4. Gateway Global Filter 구축
       (의문점): Flux도 0~1개의 데이터 전달이 가능한데, Mono라는 타입이 필요할까?
         -데이터 설계 시, 결과가 없거나 하나의 결과값만 받는 것이 명백한 경우,  List를 사용하지 않는 것처럼, 불필요하게 Flux를 사용하지 않고 Mono를 사용하는 것.
    4. Gateway Global Filter 구축
    5. Gateway Logging Filter 구축
       flow : Gateway Client -> Gateway Handler -> Global Filter -> Custom Filter -> Logging Filter -> Proxied Service 

