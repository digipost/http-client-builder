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

public class HttpClientConnectionAmount {

    public final int maxTotal;
    public final int maxPerRoute;

    HttpClientConnectionAmount(int maxTotal, int maxPerRoute) {
        this.maxTotal = Validation.equalOrGreater(maxTotal, 1, "Max total connections");
        this.maxPerRoute = Validation.equalOrGreater(maxPerRoute, 1, "Max connections per route");
    }

    public HttpClientConnectionAmount maxTotalAndPerRoute(int amount) {
        return new HttpClientConnectionAmount(amount, amount);
    }

    public HttpClientConnectionAmount maxTotal(int maxTotal) {
        return new HttpClientConnectionAmount(maxTotal, this.maxPerRoute);
    }

    public HttpClientConnectionAmount maxPerRoute(int maxPerRoute) {
        return new HttpClientConnectionAmount(this.maxTotal, maxPerRoute);
    }

}
