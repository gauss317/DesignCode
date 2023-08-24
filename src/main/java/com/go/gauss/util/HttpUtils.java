package com.go.gauss.util;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * HttpUtils
 */
@Slf4j
public class HttpUtils {
    private static final int MAX_CONN = 200;

    private static final int MAX_CONN_PER_ROUTE = 200;

    private static final int SOCKET_TIMEOUT = 5000;

    private static final int CONN_TIMEOUT = 5000;

    private static final int RET_CODE_OK = 200;

    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        RequestConfig config = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONN_TIMEOUT).build();
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry());
        manager.setDefaultMaxPerRoute(MAX_CONN_PER_ROUTE);
        manager.setMaxTotal(MAX_CONN);

        HTTP_CLIENT = HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(config).build();
    }

    public static String commonInvoke(HttpRequestBase request) {
        long startTime = System.currentTimeMillis();
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            if (response == null || response.getStatusLine() == null) {
                log.error("Http Response error, response:" + response);
                throw new RuntimeException();
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != RET_CODE_OK) {
                log.error("Http Response error, httpStatus is: {}", statusCode);
                throw new RuntimeException("http response status code is " + response.getStatusLine().getStatusCode());
            }

            if (response.getEntity() == null) {
                throw new RuntimeException("http response entity is null");
            }
            log.info("call api(url is {}, method is {}) takes {} ms", request.getURI().toURL().getPath(),
                request.getMethod(), System.currentTimeMillis() - startTime);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("call api exception", e);
        }
    }

    private static Registry<ConnectionSocketFactory> socketFactoryRegistry() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certs, s) -> true).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        } catch (KeyManagementException e) {
            log.error("RequestManager.socketFactoryRegistry:KeyManagementException");
        } catch (NoSuchAlgorithmException e) {
            log.error("RequestManager.socketFactoryRegistry:NoSuchAlgorithmException");
        } catch (KeyStoreException e) {
            log.error("RequestManager.socketFactoryRegistry:KeyStoreException");
        }
        return socketFactoryRegistry;
    }

    public static String doPut(String url, Map<String, String> headers, HttpEntity httpEntity) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(httpPut::setHeader);
        }
        if (httpEntity != null) {
            httpPut.setEntity(httpEntity);
        }
        return commonInvoke(httpPut);
    }

    /**
     * http restful post
     *
     * @param url url
     * @param headers head
     * @param reqBody 请求体
     * @return 响应
     */
    public static String doPost(String url, Map<String, String> headers, String reqBody) {
        HttpPost httpPost = new HttpPost(url);

        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(httpPost::setHeader);
        }

        StringEntity entity = new StringEntity(reqBody, StandardCharsets.UTF_8);
        entity.setContentEncoding(StandardCharsets.UTF_8.name());
        entity.setContentType(ContentType.APPLICATION_JSON.toString());
        httpPost.setEntity(entity);

        return commonInvoke(httpPost);
    }

    /**
     * http restful get
     *
     * @param url url
     * @param headers 请求头
     * @return 响应
     */
    public static String doGet(String url, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            headers.forEach(httpGet::setHeader);
        }
        return commonInvoke(httpGet);
    }
}
