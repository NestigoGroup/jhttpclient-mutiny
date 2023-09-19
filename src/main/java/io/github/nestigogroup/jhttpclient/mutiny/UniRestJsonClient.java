package io.github.nestigogroup.jhttpclient.mutiny;

import io.github.nestigogroup.jhttpclient.AsyncRestJsonClient;
import io.github.nestigogroup.jhttpclient.exceptions.ObjectMappingException;
import io.github.nestigogroup.jhttpclient.exceptions.RuntimeObjectMappingException;
import io.github.nestigogroup.jhttpclient.interfaces.IObjectMapper;
import io.github.nestigogroup.jhttpclient.responses.FileResponse;
import io.github.nestigogroup.jhttpclient.responses.MappedResponse;
import io.github.nestigogroup.jhttpclient.responses.NoBodyResponse;
import io.github.nestigogroup.jhttpclient.responses.StringResponse;
import io.smallrye.mutiny.Uni;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Simplified Async Rest Http Client with Mutiny bindings for working with Rest services that handles serialization/deserialization of request/responses
 * <b>Content-Type</b> is set to <i>application/json</i> by default (but can be overridden)
 */
public class UniRestJsonClient {

    private AsyncRestJsonClient asyncRestClient;

    /**
     * The default constructor is made private as the Client doesn't work without provided {@link IObjectMapper} implementation
     */
    private UniRestJsonClient() {
    }

    /**
     * Creates an instance of the {@link UniRestJsonClient} with HTTP version 1.1, preventing redirects from <i>Https</i> to <i>Http</i>, 30 seconds timeout and UTF-8 as Charset and <b>Content-Type</b> as <i>application/json</i>
     * @param objectMapper {@link IObjectMapper} implementation
     */
    public UniRestJsonClient(IObjectMapper objectMapper) {
        asyncRestClient = new AsyncRestJsonClient(objectMapper);
    }

    /**
     * Creates an instance of the {@link UniRestJsonClient} with the specified parameters and <b>Content-Type</b> as <i>application/json</i>
     * @param version the HTTP version (refer: {@link java.net.http.HttpClient.Version})
     * @param redirectPolicy the redirect policy (refer: {@link java.net.http.HttpClient.Redirect})
     * @param timeout the timeout as {@link Duration}
     * @param sslContext the {@link SSLContext}
     * @param headers {@link Map} of header key/value pairs to be included in all requests
     * @param charset The specified {@link Charset}
     * @param objectMapper {@link IObjectMapper} implementation
     */
    public UniRestJsonClient(HttpClient.Version version, HttpClient.Redirect redirectPolicy, Duration timeout, SSLContext sslContext, Map<String, String> headers, Charset charset, IObjectMapper objectMapper) {
        asyncRestClient = new AsyncRestJsonClient(version, redirectPolicy, timeout, sslContext, headers, charset, objectMapper);
    }

    /**
     * Creates an instance of the {@link UniRestJsonClient} with the specified parameters and <b>Content-Type</b> as <i>application/json</i>
     * @param version the HTTP version (refer: {@link java.net.http.HttpClient.Version})
     * @param executor the underlining executor to use
     * @param redirectPolicy the redirect policy (refer: {@link java.net.http.HttpClient.Redirect})
     * @param timeout the timeout as {@link Duration}
     * @param sslContext the {@link SSLContext}
     * @param headers {@link Map} of header key/value pairs to be included in all requests
     * @param charset The specified {@link Charset}
     * @param objectMapper {@link IObjectMapper} implementation
     */
    public UniRestJsonClient(HttpClient.Version version, Executor executor, HttpClient.Redirect redirectPolicy, Duration timeout, SSLContext sslContext, Map<String, String> headers, Charset charset, IObjectMapper objectMapper) {
        asyncRestClient = new AsyncRestJsonClient(version, executor, redirectPolicy, timeout, sslContext, headers, charset, objectMapper);
    }

    public void addHeader(String name, String value) {
        asyncRestClient.addHeader(name, value);
    }

    public void removeHeader(String name) {
        asyncRestClient.removeHeader(name);
    }

    /**
     * Performs an asynchronous HEAD request
     * @param url The Request URL
     * @return Uni resolving to {@link NoBodyResponse object} containing the response code and the response headers
     */
    public Uni<NoBodyResponse> head(String url) {
        return Uni.createFrom().completionStage(asyncRestClient.head(url));
    }

    /**
     * Performs an asynchronous GET request
     * @param url The Request URL
     * @return Uni resolving to {@link MappedResponse object} containing the response code, response headers and the response body as deserialized POJO/Record
     * @throws RuntimeObjectMappingException if the deserialization fails
     */
    public Uni<MappedResponse> get(String url, Class<?> outClass) {
        return Uni.createFrom().completionStage(asyncRestClient.get(url, outClass));
    }

    /**
     * Performs an asynchronous POST request
     * @param url The Request URL
     * @param body The request POJO/Record body
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as deserialized POJO/Record
     * @throws RuntimeObjectMappingException if the serialization/deserialization fails
     */
    public Uni<MappedResponse> post(String url, Class<?> outClass, String body) throws ObjectMappingException {
        return Uni.createFrom().completionStage(asyncRestClient.post(url, outClass, body));
    }

    /**
     * Performs an asynchronous PUT request
     * @param url The Request URL
     * @param body The request POJO/Record body
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as deserialized POJO/Record
     * @throws RuntimeObjectMappingException if the serialization/deserialization fails
     */
    public Uni<MappedResponse> put(String url, Class<?> outClass, String body) throws ObjectMappingException {
        return Uni.createFrom().completionStage(asyncRestClient.put(url, outClass, body));
    }

    /**
     * Performs an asynchronous PATCH request
     * @param url The Request URL
     * @param body The request POJO/Record body
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as deserialized POJO/Record
     * @throws RuntimeObjectMappingException if the serialization/deserialization fails
     */
    public Uni<MappedResponse> patch(String url, Class<?> outClass, String body) throws ObjectMappingException {
        return Uni.createFrom().completionStage(asyncRestClient.patch(url, outClass, body));
    }

    /**
     * Performs an asynchronous DELETE request
     * @param url The Request URL
     * @return Uni resolving to {@link MappedResponse object} containing the response code, response headers and the response body as deserialized POJO/Record
     * @throws RuntimeObjectMappingException if the deserialization fails
     */
    public Uni<MappedResponse> delete(String url, Class<?> outClass) {
        return Uni.createFrom().completionStage(asyncRestClient.delete(url, outClass));
    }

    /**
     * Downloads a file to specified location
     * @param url - The file URL
     * @param downloadPath - The location where the file to be stored
     * @return Uni resolving to {@link FileResponse object} containing the response code, response headers and the response body as {@link Path}
     */
    public Uni<FileResponse> downloadFile(String url, Path downloadPath) throws ExecutionException, InterruptedException {
        return Uni.createFrom().completionStage(asyncRestClient.downloadFile(url, downloadPath));
    }
}
