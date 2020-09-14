/**
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
     * {@link HttpClientSettings#DEFAULT defaults}.
     *
     * @return a safe and sensible HttpClient
     */
    public static CloseableHttpClient createDefault() {
        return create(HttpClientSettings.DEFAULT);
    }

    /**
     * Creates an {@link CloseableHttpClient HttpClient} with given settings.
     *
     * @param settings configuration parameters
     * @return a new HttpClient
     */
    public static CloseableHttpClient create(HttpClientSettings settings) {
        return createBuilder(settings, HttpClientConnectionManagerFactory.create(settings)).build();
    }

    /**
     * Create an {@link HttpClientBuilder} with safe and sensible
     * {@link HttpClientSettings#DEFAULT defaults}.
     *
     * @return a safe and sensible HttpClientBuilder
     */
    public static HttpClientBuilder createDefaultBuilder() {
        return createBuilder(HttpClientSettings.DEFAULT, HttpClientConnectionManagerFactory.createDefault());
    }


    public static HttpClientBuilder createBuilder(PoolingHttpClientConnectionManager clientConnectionManager) {
        return createBuilder(HttpClientSettings.DEFAULT, clientConnectionManager);
    }

    public static HttpClientBuilder createBuilder(HttpClientSettings settings) {
        return createBuilder(settings, HttpClientConnectionManagerFactory.create(settings));
    }

    /**
     * Create an {@link HttpClientBuilder} with given settings.
     *
     * @param settings configuration parameters
     * @return a new HttpClientBuilder
     */
    public static HttpClientBuilder createBuilder(HttpClientSettings settings, PoolingHttpClientConnectionManager clientConnectionManager) {
        if (settings.timeoutsMs.isPotentiallyDangerous()) {
            settings.logger.warn("New http client with potential dangerous settings. These settings should probably not be used in production:\n{}", settings);
        } else {
            settings.logger.info("New http client:\n{}", settings);
        }

        if (settings.evictionPolicy != HttpClientConnectionEvictionPolicy.NONE) {
            settings.logger.info("Starting HttpClientConnectionMonitor-thread");
            new HttpClientConnectionMonitor(clientConnectionManager, settings.evictionPolicy).start();
        }

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(createRequestConfig(settings.timeoutsMs))
                .setConnectionManager(clientConnectionManager)
                .setProxy(settings.httpProxy);
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

    private HttpClientFactory() {}

}