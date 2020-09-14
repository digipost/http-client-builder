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

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;

import static no.digipost.http.client.HttpClientDefaults.DEFAULT_TIMEOUTS_MS;

public final class HttpClientConnectionManagerFactory {

    public static PoolingHttpClientConnectionManager createDefault() {
        return create(HttpClientSettings.DEFAULT);
    }

    public static PoolingHttpClientConnectionManagerBuilder createDefaultBuilder() {
        return createBuilder(HttpClientSettings.DEFAULT);
    }


    public static PoolingHttpClientConnectionManager create(HttpClientSettings settings) {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultSocketConfig(createSocketConfig(settings.timeoutsMs))
                .setMaxConnTotal(settings.connectionAmount.maxTotal)
                .setMaxConnPerRoute(settings.connectionAmount.maxPerRoute)
                .build();
    }

    public static PoolingHttpClientConnectionManagerBuilder createBuilder(HttpClientSettings settings) {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultSocketConfig(createSocketConfig(settings.timeoutsMs))
                .setMaxConnTotal(settings.connectionAmount.maxTotal)
                .setMaxConnPerRoute(settings.connectionAmount.maxPerRoute);
    }

    public static SocketConfig createDefaultSocketConfig() {
        return createSocketConfig(DEFAULT_TIMEOUTS_MS);
    }

    public static SocketConfig createSocketConfig(HttpClientMillisecondTimeouts timeoutsMs) {
        return SocketConfig.custom().setSoTimeout(Timeout.ofMilliseconds(timeoutsMs.socket)).build();
    }

    private HttpClientConnectionManagerFactory() {}

}
