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

import org.apache.hc.core5.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;

import java.net.URISyntaxException;

/**
 * A subset of configuration parameters for new {@link org.apache.hc.client5.http.impl.classic.CloseableHttpClient http clients}.
 *
 * For complete configuration facilities, use the builder acquired from
 * {@link HttpClientFactory#createDefaultBuilder()}.
 */
public class HttpClientSettings {

    public static final HttpClientSettings DEFAULT = new HttpClientSettings(
            NOPLogger.NOP_LOGGER,
            HttpClientDefaults.CONNECTION_AMOUNT_NORMAL,
            null,
            HttpClientDefaults.DEFAULT_TIMEOUTS_MS,
            ConnectionEvictionPolicy.DEFAULT);


    public HttpClientSettings logConfigurationTo(Logger logger) {
        return new HttpClientSettings(logger, connectionAmount, httpProxy, timeoutsMs, evictionPolicy);
    }

    public HttpClientSettings connections(HttpClientConnectionAmount connectionAmount) {
        return new HttpClientSettings(logger, connectionAmount, httpProxy, timeoutsMs, evictionPolicy);
    }

    public HttpClientSettings useProxy(String proxyHostUrl) {
        try {
            return useProxy(HttpHost.create(proxyHostUrl));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpClientSettings useProxy(HttpHost httpProxy) {
        return new HttpClientSettings(logger, connectionAmount, httpProxy, timeoutsMs, evictionPolicy);
    }

    public HttpClientSettings timeouts(HttpClientMillisecondTimeouts timeoutsMs) {
        return new HttpClientSettings(logger, connectionAmount, httpProxy, timeoutsMs, evictionPolicy);
    }

    public HttpClientSettings connectionEvictionPolicy(ConnectionEvictionPolicy policy) {
        return new HttpClientSettings(logger, connectionAmount, httpProxy, timeoutsMs, policy);
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
                        + " - connection eviction policy %s \n"
                        + " - proxy: %s",
                connectionAmount.maxTotal,
                connectionAmount.maxPerRoute,
                timeoutsMs.socket != 0 ? timeoutsMs.socket : "[infinite]",
                timeoutsMs.socket != 0 ? timeoutsMs.socket : "[infinite]",
                timeoutsMs.connect != 0 ? timeoutsMs.connect : "[infinite]",
                timeoutsMs.connectionRequest != 0 ? timeoutsMs.connectionRequest : "[infinite]",
                evictionPolicy != null? evictionPolicy.toString() : "[none]",
                httpProxy != null ? httpProxy : "no configured proxy host");
    }


    final HttpHost httpProxy;
    final Logger logger;
    final HttpClientConnectionAmount connectionAmount;
    final HttpClientMillisecondTimeouts timeoutsMs;
    final ConnectionEvictionPolicy evictionPolicy;

    private HttpClientSettings(
            Logger instantiationLogger,
            HttpClientConnectionAmount connectionAmount,
            HttpHost proxy,
            HttpClientMillisecondTimeouts timeoutsMs,
            ConnectionEvictionPolicy evictionPolicy) {

        this.logger = instantiationLogger;
        this.connectionAmount = connectionAmount;
        this.httpProxy = proxy;
        this.timeoutsMs = timeoutsMs;
        this.evictionPolicy = evictionPolicy;
    }

}
