package com.go.gauss.rest;

/**
 * 功能描述
 *
 * @since 2023-07-31
 */
public interface RestClient {
    // void initialize(RestClientContext context);

    //RestResponse send(RestRequest request);

    //RestResponse send(RestRequest restRequest, long time);

   // CompletableFuture<RestResponse> asyncSend(RestRequest request);

    void shutdown();

    int priority();
}
