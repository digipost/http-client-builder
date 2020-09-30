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


public final class HttpClientDefaults {

    /**
     * Socket timeout: {@value #SOCKET_TIMEOUT_MS} ms.
     */
    public static final int SOCKET_TIMEOUT_MS = 10_000;

    /**
     * Connect timeout: {@value #CONNECT_TIMEOUT_MS} ms.
     */
    public static final int CONNECT_TIMEOUT_MS = 10_000;

    /**
     * Connection request timeout: {@value #CONNECTION_REQUEST_TIMEOUT_MS} ms.
     */
    public static final int CONNECTION_REQUEST_TIMEOUT_MS = 10_000;


    /**
     * The default timeouts:
     * <ul>
     *   <li>{@link #SOCKET_TIMEOUT_MS}: {@value #SOCKET_TIMEOUT_MS} ms</li>
     *   <li>{@link #CONNECT_TIMEOUT_MS}: {@value #CONNECT_TIMEOUT_MS} ms</li>
     *   <li>{@link #CONNECTION_REQUEST_TIMEOUT_MS}: {@value #CONNECTION_REQUEST_TIMEOUT_MS} ms</li>
     * </ul>
     */
    public static final HttpClientMillisecondTimeouts DEFAULT_TIMEOUTS_MS = new HttpClientMillisecondTimeouts(CONNECT_TIMEOUT_MS, CONNECTION_REQUEST_TIMEOUT_MS);

    /**
     * Validate connections after inactivity:  {@value #VALIDATE_CONNECTION_AFTER_INACTIVITY_SECOND} seconds.
     */
    public static final int VALIDATE_CONNECTION_AFTER_INACTIVITY_SECOND = 5;



    /**
     * Maximum <strong>{@value #MAX_CONNECTIONS_PER_ROUTE_NORMAL}</strong> connections
     * <em>per route</em> for normal amount of traffic.
     * <p>
     * Apache HttpClient default: 2
     */
    public static final int MAX_CONNECTIONS_PER_ROUTE_NORMAL = 10;

    /**
     * Maximum <strong>{@value #MAX_CONNECTIONS_TOTAL_NORMAL}</strong>
     * total connections for normal amount of traffic.
     * <p>
     * Apache HttpClient default: 20
     */
    public static final int MAX_CONNECTIONS_TOTAL_NORMAL = MAX_CONNECTIONS_PER_ROUTE_NORMAL;


    /**
     * Maximum <strong>{@value #MAX_CONNECTIONS_PER_ROUTE_MEDIUM}</strong> connections
     * <em>per route</em> for medium amount of traffic.
     * <p>
     * Apache HttpClient default: 2
     */
    public static final int MAX_CONNECTIONS_PER_ROUTE_MEDIUM = 50;

    /**
     * Maximum <strong>{@value #MAX_CONNECTIONS_TOTAL_MEDIUM}</strong>
     * total connections for medium amount of traffic.
     * <p>
     * Apache HttpClient default: 20
     */
    public static final int MAX_CONNECTIONS_TOTAL_MEDIUM = MAX_CONNECTIONS_PER_ROUTE_MEDIUM;


    /**
     * Maximum <strong>{@value #MAX_CONNECTIONS_PER_ROUTE_HIGH}</strong> connections
     * <em>per route</em> for high amount of traffic.
     * <p>
     * Apache HttpClient default: 2
     */
    public static final int MAX_CONNECTIONS_PER_ROUTE_HIGH = 100;

    /**
     * Maximum <strong>{@value #MAX_CONNECTIONS_TOTAL_HIGH}</strong>
     * total connections for high amount of traffic.
     * <p>
     * Apache HttpClient default: 20
     */
    public static final int MAX_CONNECTIONS_TOTAL_HIGH = MAX_CONNECTIONS_PER_ROUTE_HIGH;


    public static final HttpClientConnectionAmount CONNECTION_AMOUNT_NORMAL = new HttpClientConnectionAmount(MAX_CONNECTIONS_TOTAL_NORMAL, MAX_CONNECTIONS_PER_ROUTE_NORMAL);
    public static final HttpClientConnectionAmount CONNECTION_AMOUNT_MEDIUM = new HttpClientConnectionAmount(MAX_CONNECTIONS_TOTAL_MEDIUM, MAX_CONNECTIONS_PER_ROUTE_MEDIUM);
    public static final HttpClientConnectionAmount CONNECTION_AMOUNT_HIGH = new HttpClientConnectionAmount(MAX_CONNECTIONS_TOTAL_HIGH, MAX_CONNECTIONS_PER_ROUTE_HIGH);


    private HttpClientDefaults() {
    }
}
