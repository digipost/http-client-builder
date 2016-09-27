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
package no.digipost.http.client3;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static no.digipost.http.client3.DigipostHttpClientDefaults.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TimeoutsTest {

    @Test
    public void defaultValuesAreGreaterThanZero() {
        assertThat(DEFAULT_TIMEOUTS_MS.connect, both(is(CONNECT_TIMEOUT_MS)).and(greaterThan(0)));
        assertThat(DEFAULT_TIMEOUTS_MS.socket, both(is(SOCKET_TIMEOUT_MS)).and(greaterThan(0)));
        assertThat(DEFAULT_TIMEOUTS_MS.connectionRequest, both(is(CONNECTION_REQUEST_TIMEOUT_MS)).and(greaterThan(0)));
    }

    @Test
    public void settingTimeoutValues() {
        assertThat(DEFAULT_TIMEOUTS_MS.connect(42).connect, is(42));
        assertThat(DEFAULT_TIMEOUTS_MS.connectionRequest(127).connectionRequest, is(127));
        assertThat(DEFAULT_TIMEOUTS_MS.socket(1337).socket, is(1337));

        DigipostHttpClientMillisecondTimeouts allTimeouts50ms = DEFAULT_TIMEOUTS_MS.all(50);
        assertThat(allTimeouts50ms.connect, is(50));
        assertThat(allTimeouts50ms.connectionRequest, is(50));
        assertThat(allTimeouts50ms.socket, is(50));
    }

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void negativeConnectTimeoutIsNotAllowed() {
        expectedException.expect(IllegalArgumentException.class);
        DEFAULT_TIMEOUTS_MS.connect(-1);
    }

    @Test
    public void negativeConnecttionRequestTimeoutIsNotAllowed() {
        expectedException.expect(IllegalArgumentException.class);
        DEFAULT_TIMEOUTS_MS.connectionRequest(-1);
    }

    @Test
    public void negativeSocketTimeoutIsNotAllowed() {
        expectedException.expect(IllegalArgumentException.class);
        DEFAULT_TIMEOUTS_MS.socket(-1);
    }
}
