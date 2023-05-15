package idv.laborLab.apiGateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("Path of request: {}", exchange.getRequest().getURI());
        return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        log.info("Last Post Global Filter, path of request: {}", exchange.getRequest().getURI());
                    }));
    }
}
