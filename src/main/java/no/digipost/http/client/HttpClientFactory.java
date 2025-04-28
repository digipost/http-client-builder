/*
 * Copyright (C) Posten Norge AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.digipost.http.client;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;

import static no.digipost.http.client.HttpClientDefaults.DEFAULT_TIMEOUTS_MS;

public final class HttpClientFactory {

    /**
     * Creates an {@link CloseableHttpClient HttpClient} with safe and sensible
     * {@link HttpClientSettings#DEFAULT defaults}
     * and a default {@link HttpClientConnectionManagerFactory#createDefault()} connection manager..
     *
     * @return a safe and sensible HttpClient
     */
    public static CloseableHttpClient createDefault() {
        return create(HttpClientSettings.DEFAULT, HttpClientConnectionManagerFactory.createDefault());
    }

    /**
     * Creates an {@link CloseableHttpClient HttpClient} with given settings
     * and a default {@link HttpClientConnectionManagerFactory#createDefault()} connection manager.
     *
     * @return a safe and sensible HttpClient
     */
    public static CloseableHttpClient create(HttpClientSettings settings) {
        return create(settings, HttpClientConnectionManagerFactory.createDefault());
    }

    /**
     * Creates an {@link CloseableHttpClient HttpClient} with safe and sensible
     * {@link HttpClientSettings#DEFAULT defaults} and the given connection manager.
     *
     * @return a safe and sensible HttpClient
     */
    public static CloseableHttpClient create(PoolingHttpClientConnectionManager clientConnectionManager) {
        return create(HttpClientSettings.DEFAULT, clientConnectionManager);
    }

    /**
     * Creates an {@link CloseableHttpClient HttpClient} with given settings and connection manager.
     *
     * @param settings configuration parameters
     * @param clientConnectionManager the client connection manager
     * @return a new HttpClient
     */
    public static CloseableHttpClient create(HttpClientSettings settings, PoolingHttpClientConnectionManager clientConnectionManager) {
        return createBuilder(settings, clientConnectionManager).build();
    }

    /**
     * Create an {@link HttpClientBuilder} with safe and sensible
     * {@link HttpClientSettings#DEFAULT defaults},
     * and a {@link PoolingHttpClientConnectionManager} with safe and sensible defaults.
     *
     * @return a safe and sensible HttpClientBuilder
     */
    public static HttpClientBuilder createDefaultBuilder() {
        return createBuilder(HttpClientSettings.DEFAULT, HttpClientConnectionManagerFactory.createDefault());
    }


    /**
     * Create an {@link HttpClientBuilder} with given connection manager.
     *
     * @param clientConnectionManager the client connection manager
     * @return a new HttpClientBuilder
     */
    public static HttpClientBuilder createBuilder(PoolingHttpClientConnectionManager clientConnectionManager) {
        return createBuilder(HttpClientSettings.DEFAULT, clientConnectionManager);
    }

    /**
     * Create an {@link HttpClientBuilder} with given settings,
     * and a {@link PoolingHttpClientConnectionManager} with safe and sensible defaults.
     *
     * @param settings configuration parameters
     * @return a new HttpClientBuilder
     */
    public static HttpClientBuilder createBuilder(HttpClientSettings settings) {
        return createBuilder(settings, HttpClientConnectionManagerFactory.createDefault());
    }

    /**
     * Create an {@link HttpClientBuilder} with given settings and connection manager.
     *
     * @param settings configuration parameters
     * @param clientConnectionManager the client connection manager
     * @return a new HttpClientBuilder
     */
    public static HttpClientBuilder createBuilder(HttpClientSettings settings, PoolingHttpClientConnectionManager clientConnectionManager) {
        Timeout soTimeout = clientConnectionManager.getDefaultSocketConfig().getSoTimeout();
        boolean isSocketTimeoutPotentiallyDangerous = soTimeout.isDisabled();
        if (settings.timeoutsMs.isPotentiallyDangerous() || isSocketTimeoutPotentiallyDangerous) {
            settings.logger.warn("New http client with potential dangerous settings. These settings should probably not be used in production:\n{},\n soTimeout (client connection): {}", settings, soTimeout);
        } else {
            settings.logger.info("New http client:\n{}", settings);
        }


        final HttpClientBuilder builder = HttpClientBuilder.create()
                .setDefaultRequestConfig(createRequestConfig(settings.timeoutsMs))
                .setConnectionManager(clientConnectionManager)
                .setProxy(settings.httpProxy);

        if (settings.evictionPolicy != ConnectionEvictionPolicy.NONE) {
            builder.evictIdleConnections(settings.evictionPolicy.connectionsIdleLongerThanThreshold);
            builder.evictExpiredConnections();
            settings.logger.info("Enabling Apache HttpClient IdleConnectionEvictor with eviction policy: {}", settings.evictionPolicy);
        }

        return builder;
    }


    public static RequestConfig createDefaultRequestConfig() {
        return createRequestConfig(DEFAULT_TIMEOUTS_MS);
    }

    public static RequestConfig createRequestConfig(HttpClientMillisecondTimeouts timeoutsMs) {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(timeoutsMs.connectionRequest))
                .setConnectTimeout(Timeout.ofMilliseconds(timeoutsMs.connect))
                .build();
    }

    private HttpClientFactory() {
    }

}
