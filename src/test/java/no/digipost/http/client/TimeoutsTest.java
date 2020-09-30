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

import org.junit.jupiter.api.Test;

import static no.digipost.http.client.HttpClientDefaults.CONNECTION_REQUEST_TIMEOUT_MS;
import static no.digipost.http.client.HttpClientDefaults.CONNECT_TIMEOUT_MS;
import static no.digipost.http.client.HttpClientDefaults.DEFAULT_TIMEOUTS_MS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TimeoutsTest {

    @Test
    public void defaultValuesAreGreaterThanZero() {
        assertThat(DEFAULT_TIMEOUTS_MS.connect, both(is(CONNECT_TIMEOUT_MS)).and(greaterThan(0)));
        assertThat(DEFAULT_TIMEOUTS_MS.connectionRequest, both(is(CONNECTION_REQUEST_TIMEOUT_MS)).and(greaterThan(0)));
    }

    @Test
    public void settingTimeoutValues() {
        assertThat(DEFAULT_TIMEOUTS_MS.connect(42).connect, is(42));
        assertThat(DEFAULT_TIMEOUTS_MS.connectionRequest(127).connectionRequest, is(127));

        HttpClientMillisecondTimeouts allTimeouts50ms = DEFAULT_TIMEOUTS_MS.all(50);
        assertThat(allTimeouts50ms.connect, is(50));
        assertThat(allTimeouts50ms.connectionRequest, is(50));
    }

    @Test
    public void negativeConnectTimeoutIsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> DEFAULT_TIMEOUTS_MS.connect(-1));
    }

    @Test
    public void negativeConnecttionRequestTimeoutIsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> DEFAULT_TIMEOUTS_MS.connectionRequest(-1));
    }
}
