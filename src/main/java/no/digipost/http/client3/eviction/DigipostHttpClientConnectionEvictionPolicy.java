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
package no.digipost.http.client3.eviction;

import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;


/**
 * Timeout values in milliseconds for the HTTP client.
 */
public final class DigipostHttpClientConnectionEvictionPolicy {

    public static DigipostHttpClientConnectionEvictionPolicy NONE = null;
    public static DigipostHttpClientConnectionEvictionPolicy ONLY_EVICT_EXPIRED_CONNECTIONS = new DigipostHttpClientConnectionEvictionPolicy(TimeValue.NEG_ONE_SECOND);
    public static DigipostHttpClientConnectionEvictionPolicy DEFAULT = closeConnectionsIdleLongerThan(20);

    public final Timeout checkInterval;

    /**
     * The idle timeout before evicting the connection.
     */
    public final TimeValue connectionsIdleLongerThanThreshold;

    private DigipostHttpClientConnectionEvictionPolicy(TimeValue closeIdleConnectionsAfter) {
        this.connectionsIdleLongerThanThreshold = closeIdleConnectionsAfter;
        this.checkInterval = Timeout.ofSeconds(1);
    }

    /**
     *
     * @param seconds negative to disable idle connection eviction
     */
    public static DigipostHttpClientConnectionEvictionPolicy closeConnectionsIdleLongerThan(int seconds) {
        return new DigipostHttpClientConnectionEvictionPolicy(TimeValue.ofSeconds(seconds));
    }

    @Override
    public String toString() {
        return "DigipostHttpClientConnectionEvictionPolicy{" +
                "checkInterval=" + checkInterval +
                ", connectionsIdleLongerThanThreshold=" + connectionsIdleLongerThanThreshold +
                '}';
    }
}
