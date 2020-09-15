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

import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;

/**
 * A subset of configuration parameters for new {@link org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder http client client connection managers}.
 *
 * For complete configuration facilities, use the builder acquired from
 * {@link HttpClientConnectionManagerFactory#createDefaultBuilder()}.
 */
public class HttpClientConnectionSettings implements PotentiallyDangerous {

    public static final HttpClientConnectionSettings DEFAULT = new HttpClientConnectionSettings(
            NOPLogger.NOP_LOGGER
            , HttpClientDefaults.CONNECTION_AMOUNT_NORMAL
            , HttpClientDefaults.SOCKET_TIMEOUT_MS
    );

    public HttpClientConnectionSettings connections(HttpClientConnectionAmount connectionAmount) {
        return new HttpClientConnectionSettings(logger, connectionAmount, socketTimeoutMs);
    }

    /**
     * Socket timeout is used for both requests and, if any,
     * underlying layered sockets (typically for
     * secure sockets).
     */
    public HttpClientConnectionSettings socketTimeout(int timeoutsMs) {
        return new HttpClientConnectionSettings(logger, connectionAmount, timeoutsMs);
    }

    @Override
    public String toString() {
        return String.format(
                " - max total connections %s\n"
                        + " - max connections per route %s\n"
                        + " - socket timeout %s ms\n",
                connectionAmount.maxTotal,
                connectionAmount.maxPerRoute,
                socketTimeoutMs != 0 ? socketTimeoutMs : "[infinite]"
        );
    }


    final Logger logger;
    final HttpClientConnectionAmount connectionAmount;
    final int socketTimeoutMs;

    private HttpClientConnectionSettings(
            Logger instantiationLogger
            , HttpClientConnectionAmount connectionAmount
            , int socketTimeoutMs) {

        this.logger = instantiationLogger;
        this.connectionAmount = connectionAmount;
        this.socketTimeoutMs = Validation.equalOrGreater(socketTimeoutMs, 0, "socket timeout");
    }

    @Override
    public boolean isPotentiallyDangerous() {
        return socketTimeoutMs == 0;
    }
}
