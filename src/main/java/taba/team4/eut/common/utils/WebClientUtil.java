package taba.team4.eut.common.utils;

import taba.team4.eut.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientUtil {

    public static <T> Mono<T> get(WebClient webClient, String uri, Class<T> tClass) {
        Mono<T> response = webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus (
                    httpStatus -> httpStatus != HttpStatus.OK,
                    clientResponse -> {
                        ClientResponse.Headers headers = clientResponse.headers();

                        HttpStatusCode httpStatusCode = clientResponse.statusCode();
                        log.error("uri : {}", uri);
                        log.error("contentType : {}", headers.contentType());
                        log.error("httpStatusCode : {}", httpStatusCode.value());

                        return clientResponse.createException()
                                .flatMap(it -> Mono.error(new RuntimeException(clientResponse.statusCode().toString())));
                    })
                .bodyToMono(tClass)
                .onErrorResume(throwable -> {
                    log.debug("# onErrorResume : {}", throwable.getMessage());
                    return Mono.error(new BizException(throwable.getMessage()));
                });

        return response;
    }
}
