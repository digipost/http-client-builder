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

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Close expired, and optionally, idle (optional) connections
 * https://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html
 */
class ConnectionMonitor extends Thread {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final PoolingHttpClientConnectionManager connMgr;
    private volatile boolean shutdown;

    private final Timeout threadTimeout;
    private final TimeValue closeIdleAfter;

    ConnectionMonitor(PoolingHttpClientConnectionManager connMgr, ConnectionEvictionPolicy policy) {
        super();
        this.connMgr = connMgr;
        this.threadTimeout = policy.checkInterval;
        this.closeIdleAfter = policy.connectionsIdleLongerThanThreshold;
    }

    @Override
    public void run() {
        addShutdownHook();
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(threadTimeout.toMilliseconds());
                    // Close expired connections
                    connMgr.closeExpired();
                    if (TimeValue.isNonNegative(closeIdleAfter)) {
                        connMgr.closeIdle(closeIdleAfter);
                    }

                    log.debug("PoolStats: {}", connMgr.getTotalStats());
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
