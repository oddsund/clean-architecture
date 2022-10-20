package no.bekk.power.integrations

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

class HttpClient {

    val client = WebClient.builder()
        .baseUrl("https://clean-architecture-services.fly.dev")
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer clean-architecture-workshop")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchangeStrategies(
            ExchangeStrategies.builder()
            .codecs { codecs: ClientCodecConfigurer ->
                codecs.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)
            }
            .build())
        .build()

    inline fun <reified T> meterValues(): T? {
        return client.get()
            .uri("/metervalues")
            .retrieve()
            .bodyToMono(T::class.java)
            .block()
    }

}
