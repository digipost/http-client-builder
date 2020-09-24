/**
 * Copyright (C) Posten Norge AS
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

import static no.digipost.http.client.HttpClientDefaults.SOCKET_TIMEOUT_MS;

public final class HttpClientConnectionManagerFactory {

    public static PoolingHttpClientConnectionManager createDefault() {
        return create(HttpClientConnectionSettings.DEFAULT);
    }

    public static PoolingHttpClientConnectionManagerBuilder createDefaultBuilder() {
        return createBuilder(HttpClientConnectionSettings.DEFAULT);
    }


    public static PoolingHttpClientConnectionManager create(HttpClientConnectionSettings settings) {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultSocketConfig(createSocketConfig(settings.socketTimeoutMs))
                .setMaxConnTotal(settings.connectionAmount.maxTotal)
                .setMaxConnPerRoute(settings.connectionAmount.maxPerRoute)
                .build();
    }

    public static PoolingHttpClientConnectionManagerBuilder createBuilder(HttpClientConnectionSettings settings) {
        if (settings.isPotentiallyDangerous()) {
            settings.logger.warn("New http client connection manager with potential dangerous settings. These settings should probably not be used in production:\n{}", settings);
        } else {
            settings.logger.info("New http client connection manager:\n{}", settings);
        }

        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultSocketConfig(createSocketConfig(settings.socketTimeoutMs))
                .setValidateAfterInactivity(TimeValue.ofSeconds(settings.validateAfterInactivitySeconds))
                .setMaxConnTotal(settings.connectionAmount.maxTotal)
                .setMaxConnPerRoute(settings.connectionAmount.maxPerRoute);
    }

    public static SocketConfig createDefaultSocketConfig() {
        return createSocketConfig(SOCKET_TIMEOUT_MS);
    }

    public static SocketConfig createSocketConfig(int timeoutsMs) {
        return SocketConfig.custom().setSoTimeout(Timeout.ofMilliseconds(timeoutsMs)).build();
    }

    private HttpClientConnectionManagerFactory() {
    }

}
