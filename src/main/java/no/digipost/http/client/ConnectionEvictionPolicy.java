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

import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;


/**
 * Eviction policy for the connections.
 */
public final class ConnectionEvictionPolicy {

    public static ConnectionEvictionPolicy NONE = null;
    public static ConnectionEvictionPolicy DEFAULT = closeConnectionsIdleLongerThan(60);

    final Timeout checkInterval;

    /**
     * The idle timeout before evicting the connection.
     */
    final TimeValue connectionsIdleLongerThanThreshold;

    private ConnectionEvictionPolicy(TimeValue closeIdleConnectionsAfter) {
        this.connectionsIdleLongerThanThreshold = closeIdleConnectionsAfter;
        this.checkInterval = closeIdleConnectionsAfter.compareTo(TimeValue.ofSeconds(6)) > 0 ? closeIdleConnectionsAfter.divide(6).toTimeout() : closeIdleConnectionsAfter.min(TimeValue.ofSeconds(1)).toTimeout();
    }

    /**
     *
     * @param seconds negative to disable idle connection eviction
     */
    public static ConnectionEvictionPolicy closeConnectionsIdleLongerThan(int seconds) {
        Validation.equalOrGreater(seconds, -1, "Max idle time before connection is closed.");
        return new ConnectionEvictionPolicy(TimeValue.ofSeconds(seconds));
    }

    @Override
    public String toString() {
        return "ConnectionEvictionPolicy{" +
                "checkInterval=" + checkInterval +
                ", connectionsIdleLongerThanThreshold=" + connectionsIdleLongerThanThreshold +
                '}';
    }
}
