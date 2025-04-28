/*
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

import org.junit.jupiter.api.Test;

import static no.digipost.http.client.HttpClientConnectionSettings.DEFAULT;
import static no.digipost.http.client.HttpClientDefaults.SOCKET_TIMEOUT_MS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpClientConnectionSettingsTest {


    @Test
    public void negativeSocketTimeoutIsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> DEFAULT.socketTimeout(-1));
    }

    @Test
    public void defaultValuesAreGreaterThanZero() {
        assertThat(DEFAULT.socketTimeoutMs, both(is(SOCKET_TIMEOUT_MS)).and(greaterThan(0)));
    }

    @Test
    public void settingTimeoutValues() {
        assertThat(DEFAULT.socketTimeout(1337).socketTimeoutMs, is(1337));
    }


}
