package com.github.nicklaus4.http.reactive;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.collections4.MapUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.base.Suppliers;

import reactor.core.publisher.Mono;

/**
 * @author weishibai
 * @date 2019/05/13 14:21
 */
public class ReactiveHttpClient {

    private Supplier<WebClient> clientSupplier = Suppliers.memoize(WebClient::create);

    public <T> Mono<T> asyncGet(String uri, Class<T> respClass) {
        final WebClient webClient = clientSupplier.get();
        return webClient.method(HttpMethod.GET)
                .uri(uri)
                .retrieve()
                .bodyToMono(respClass);
    }

    public <T> Mono<T> asyncGet(String host, String path, Map<String, String> params, Class<T> respClass) {
        final WebClient webClient = clientSupplier.get();
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.scheme("http")
                            .host(host)
                            .path(path);

                    if (MapUtils.isNotEmpty(params)) {
                        params.forEach(uriBuilder::queryParam);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(respClass);
    }

    public <T> Mono<T> asyncGet(String uri, Map<String, String> headers, Class<T> respClass) {
        return asyncGet(uri, headers, null, respClass);
    }

    public <T> Mono<T> asyncGet(String uri, Map<String, String> headers, Map<String, String> cookies, Class<T> respClass) {
        final WebClient webClient = clientSupplier.get();
        final WebClient.RequestHeadersUriSpec<?> spec = webClient.get();

        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(spec::header);
        }

        if (MapUtils.isNotEmpty(cookies)) {
            cookies.forEach(spec::cookie);
        }

        return spec.uri(uri)
                .retrieve()
                .bodyToMono(respClass);
    }

    public <T> Mono<T> asyncFormPost(String uri, MultiValueMap<String, String> formData, Class<T> respClass) {
        final WebClient webClient = clientSupplier.get();
        return webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(respClass);
    }

    public <T, R> Mono<R> asyncJsonPost(String uri, T req, Class<T> reqClz, Class<R> respClz) {
        final WebClient webClient = clientSupplier.get();
        return webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(req), reqClz)
                .retrieve()
                .bodyToMono(respClz);
    }

    public <T> Mono<T> uploadFile(String uri, String fileClasspathName, MediaType mediaType, Class<T> respClz) {
        final WebClient webClient = clientSupplier.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", new HttpEntity<>(new ClassPathResource(fileClasspathName), headers));
        return webClient.post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .bodyToMono(respClz);
    }

    public void downloadFile(String uri, String targetFullPathFileName) {
        final WebClient webClient = clientSupplier.get();
        Mono<ClientResponse> resp = webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .exchange();

        Resource resource = resp.block()
                .bodyToMono(Resource.class)
                .block();

        if (resource == null) {
            throw new RuntimeException("no response");
        }

        try(OutputStream stream = new FileOutputStream(targetFullPathFileName)) {
            FileCopyUtils.copy(resource.getInputStream(), stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
