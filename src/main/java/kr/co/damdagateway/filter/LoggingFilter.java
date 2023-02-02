package kr.co.damdagateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(LoggingFilter.Config.class);
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(LoggingFilter.Config config) {

        GatewayFilter gatewayFilter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage: {}", config.getBaseMessage());
            getRequestId(config, request);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                getResponseStatusCode(config, response);
            }));
        }, Ordered.HIGHEST_PRECEDENCE);

        return gatewayFilter;
    }

      private void getRequestId(LoggingFilter.Config config, ServerHttpRequest request){
        if(config.isPostLogger()){
            log.info("Logging Pre Filter: Request ID -> {}", request.getId());
        }
    }

    private void getResponseStatusCode(LoggingFilter.Config config, ServerHttpResponse response){
        if(config.isPostLogger()){
            log.info("Logging Post Filter: Response Code -> {}", response.getStatusCode());
        }
    }
}
