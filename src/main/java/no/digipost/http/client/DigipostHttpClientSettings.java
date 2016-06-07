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

import no.digipost.http.client.DigipostHttpClientDefaults.ConnectionAmount;
import org.apache.http.HttpHost;
import org.apache.http.config.ConnectionConfig;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;

/**
 * A subset of configuration parameters for new {@link org.apache.http.impl.client.CloseableHttpClient http clients}.
 *
 * For complete configuration facilities, use the builder acquired from
 * {@link DigipostHttpClientFactory#createDefaultBuilder()}.
 */
public class DigipostHttpClientSettings {

    public static final DigipostHttpClientSettings DEFAULT = new DigipostHttpClientSettings(
            NOPLogger.NOP_LOGGER,
            DigipostHttpClientFactory.createDefaultConnectionConfig(),
            DigipostHttpClientDefaults.CONNECTION_AMOUNT_NORMAL,
            null,
            DigipostHttpClientDefaults.DEFAULT_TIMEOUTS_MS);


    public DigipostHttpClientSettings logConfigurationTo(Logger logger) {
        return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
    }

    public DigipostHttpClientSettings connections(ConnectionAmount connectionAmount) {
        return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
    }

    public DigipostHttpClientSettings useProxy(String proxyHostUrl) {
        return useProxy(HttpHost.create(proxyHostUrl));
    }

    public DigipostHttpClientSettings useProxy(HttpHost httpProxy) {
        return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
    }

    public DigipostHttpClientSettings timeouts(DigipostHttpClientMillisecondTimeouts timeoutsMs) {
        return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
    }


    @Override
    public String toString() {
        return String.format(
                      " - max total connections %s\n"
                    + " - max connections per route %s\n"
                    + " - so timeout %s ms\n"
                    + " - socket timeout %s ms\n"
                    + " - connect timeout %s ms\n"
                    + " - connection request timeout %s ms\n"
                    + " - proxy: %s",
                    connectionAmount.maxTotal,
                    connectionAmount.maxPerRoute,
                    timeoutsMs.socket != 0 ? timeoutsMs.socket : "[infinite]",
                    timeoutsMs.socket != 0 ? timeoutsMs.socket : "[infinite]",
                    timeoutsMs.connect != 0 ? timeoutsMs.connect : "[infinite]",
                    timeoutsMs.connectionRequest != 0 ? timeoutsMs.connectionRequest : "[infinite]",
                    httpProxy != null ? httpProxy : "no configured proxy host");
    }





    final HttpHost httpProxy;
    final Logger logger;
    final ConnectionConfig connectionConfig;
    final ConnectionAmount connectionAmount;
    final DigipostHttpClientMillisecondTimeouts timeoutsMs;

    private DigipostHttpClientSettings(
            Logger instantiationLogger,
            ConnectionConfig connectionConfig,
            ConnectionAmount connectionAmount,
            HttpHost proxy,
            DigipostHttpClientMillisecondTimeouts timeoutsMs) {

        this.logger = instantiationLogger;
        this.connectionConfig = connectionConfig;
        this.connectionAmount = connectionAmount;
        this.httpProxy = proxy;
        this.timeoutsMs = timeoutsMs;
    }

}
