package kr.co.damdagateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage: {}", config.getBaseMessage());
            getRequestId(config, request);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                getResponseStatusCode(config, response);
            }));
        };
    }

    private void getRequestId(Config config, ServerHttpRequest request){
        if(config.isPostLogger()){
            log.info("Global Filter Start: Request ID -> {}", request.getId());
        }
    }

    private void getResponseStatusCode(Config config, ServerHttpResponse response){
        if(config.isPostLogger()){
            log.info("Global Filter End: Response Code -> {}", response.getStatusCode());
        }
    }
}
