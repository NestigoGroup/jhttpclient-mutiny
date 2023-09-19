package io.github.nestigogroup.jhttpclient.mutiny;

import io.github.nestigogroup.jhttpclient.AsyncRestClient;
import io.github.nestigogroup.jhttpclient.responses.FileResponse;
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
 * Simplified Async Rest Http Client with Mutiny bindings for working with Rest services
 * <b>Content-Type</b> is set to <i>application/json</i> by default (but can be overridden)
 */
public class UniRestClient {

    private final AsyncRestClient asyncRestClient;

    /**
     * Creates an instance of the {@link UniRestClient} with HTTP version 1.1, preventing redirects from <i>Https</i> to <i>Http</i>, 30 seconds timeout and UTF-8 as Charset and <b>Content-Type</b> as <i>application/json</i>
     */
    public UniRestClient() {
        asyncRestClient = new AsyncRestClient();
    }

    /**
     * Creates an instance of the {@link UniRestClient} with the specified parameters and <b>Content-Type</b> as <i>application/json</i>
     * @param version the HTTP version (refer: {@link java.net.http.HttpClient.Version})
     * @param redirectPolicy the redirect policy (refer: {@link java.net.http.HttpClient.Redirect})
     * @param timeout the timeout as {@link Duration}
     * @param sslContext the {@link SSLContext}
     * @param headers {@link Map} of header key/value pairs to be included in all requests
     * @param charset The specified {@link Charset}
     */
    public UniRestClient(HttpClient.Version version, HttpClient.Redirect redirectPolicy, Duration timeout, SSLContext sslContext, Map<String, String> headers, Charset charset) {
        asyncRestClient = new AsyncRestClient(version, redirectPolicy, timeout, sslContext, headers, charset);
    }

    /**
     * Creates an instance of the {@link UniRestClient} with the specified parameters and <b>Content-Type</b> as <i>application/json</i>
     * @param version the HTTP version (refer: {@link java.net.http.HttpClient.Version})
     * @param executor the underlining executor to use
     * @param redirectPolicy the redirect policy (refer: {@link java.net.http.HttpClient.Redirect})
     * @param timeout the timeout as {@link Duration}
     * @param sslContext the {@link SSLContext}
     * @param headers {@link Map} of header key/value pairs to be included in all requests
     * @param charset The specified {@link Charset}
     */
    public UniRestClient(HttpClient.Version version, Executor executor, HttpClient.Redirect redirectPolicy, Duration timeout, SSLContext sslContext, Map<String, String> headers, Charset charset) {
        asyncRestClient = new AsyncRestClient(version, executor, redirectPolicy, timeout, sslContext, headers, charset);
    }

    /**
     * Add a new header to be sent for every request after
     * @param name the header name
     * @param value tne header value
     */
    public void addHeader(String name, String value) {
        asyncRestClient.addHeader(name, value);
    }

    /**
     * Remove a header, so it is no longer send with every request.
     * Has no effect if the header doesn't exist
     * @param name the header name
     */
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
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as {@link String}
     */
    public Uni<StringResponse> get(String url) {
        return Uni.createFrom().completionStage(asyncRestClient.get(url));
    }

    /**
     * Performs an asynchronous POST request
     * @param url The Request URL
     * @param body The request String body
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as {@link String}
     */
    public Uni<StringResponse> post(String url, String body) {
        return Uni.createFrom().completionStage(asyncRestClient.post(url, body));
    }

    /**
     * Performs an asynchronous PUT request
     * @param url The Request URL
     * @param body The request String body
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as {@link String}
     */
    public Uni<StringResponse> put(String url, String body) {
        return Uni.createFrom().completionStage(asyncRestClient.put(url, body));
    }

    /**
     * Performs an asynchronous PATCH request
     * @param url The Request URL
     * @param body The request String body
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as {@link String}
     */
    public Uni<StringResponse> patch(String url, String body) {
        return Uni.createFrom().completionStage(asyncRestClient.patch(url, body));
    }

    /**
     * Performs an asynchronous DELETE request
     * @param url The Request URL
     * @return Uni resolving to {@link StringResponse object} containing the response code, response headers and the response body as {@link String}
     */
    public Uni<StringResponse> delete(String url) {
        return Uni.createFrom().completionStage(asyncRestClient.delete(url));
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
