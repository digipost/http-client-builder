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

/**
 * Timeout values in milliseconds for the HTTP client.
 */
public final class DigipostHttpClientMillisecondTimeouts {

    /**
     * Socket timeout is used for both requests and, if any,
     * underlying layered sockets (typically for
     * secure sockets).
     */
    public final int socket;

    /**
     * The connect timeout for requests.
     */
    public final int connect;

    /**
     * The connection request timeout for requests.
     */
    public final int connectionRequest;

    DigipostHttpClientMillisecondTimeouts(int socket, int connect, int connectionRequest) {
        this.socket = Validation.equalOrGreater(socket, 0, "socket timeout");
        this.connect = Validation.equalOrGreater(connect, 0, "connect timeout");
        this.connectionRequest = Validation.equalOrGreater(connectionRequest, 0, "connection request timeout");
    }


    public DigipostHttpClientMillisecondTimeouts all(int timeoutMs) {
        return new DigipostHttpClientMillisecondTimeouts(timeoutMs, timeoutMs, timeoutMs);
    }

    public DigipostHttpClientMillisecondTimeouts socket(int timeoutMs) {
        return new DigipostHttpClientMillisecondTimeouts(timeoutMs, connect, connectionRequest);
    }

    public DigipostHttpClientMillisecondTimeouts connect(int timeoutMs) {
        return new DigipostHttpClientMillisecondTimeouts(socket, timeoutMs, connectionRequest);
    }

    public DigipostHttpClientMillisecondTimeouts connectionRequest(int timeoutMs) {
        return new DigipostHttpClientMillisecondTimeouts(socket, connect, timeoutMs);
    }

    boolean isPotentiallyDangerous() {
        return socket == 0 || connect == 0 || connectionRequest == 0;
    }

}
