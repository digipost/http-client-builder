package no.posten.dpost.httpclient;

import no.posten.dpost.httpclient.DigipostHttpClientDefaults.ConnectionAmount;
import org.apache.http.HttpHost;
import org.apache.http.config.ConnectionConfig;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;

/**
 * A subset of configuration parameters for new {@link org.apache.http.impl.client.CloseableHttpClient http clients}.
 *
 * For complete configuration facilities, use the builder acquired from
 * {@link DigipostHttpClientFactory#createDefaultBuilder()}.
 */
public class DigipostHttpClientSettings {

	public static final DigipostHttpClientSettings DEFAULT = new DigipostHttpClientSettings(
			NOPLogger.NOP_LOGGER,
			DigipostHttpClientFactory.createDefaultConnectionConfig(),
			DigipostHttpClientDefaults.CONNECTION_AMOUNT_NORMAL,
			null,
			DigipostHttpClientDefaults.DEFAULT_TIMEOUTS_MS);


	public DigipostHttpClientSettings logConfigurationTo(Logger logger) {
		return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
	}

	public DigipostHttpClientSettings connections(ConnectionAmount connectionAmount) {
		return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
	}

	public DigipostHttpClientSettings useProxy(HttpHost httpProxy) {
		return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
	}

	public DigipostHttpClientSettings timeouts(DigipostHttpClientMillisecondTimeouts timeoutsMs) {
		return new DigipostHttpClientSettings(logger, connectionConfig, connectionAmount, httpProxy, timeoutsMs);
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
					+ " - proxy: %s",
					connectionAmount.maxTotal,
					connectionAmount.maxPerRoute,
					timeoutsMs.socket != 0 ? timeoutsMs.socket : "[infinite]",
					timeoutsMs.socket != 0 ? timeoutsMs.socket : "[infinite]",
					timeoutsMs.connect != 0 ? timeoutsMs.connect : "[infinite]",
					timeoutsMs.connectionRequest != 0 ? timeoutsMs.connectionRequest : "[infinite]",
					httpProxy != null ? httpProxy : "no configured proxy host");
	}





	final HttpHost httpProxy;
	final Logger logger;
	final ConnectionConfig connectionConfig;
	final ConnectionAmount connectionAmount;
	final DigipostHttpClientMillisecondTimeouts timeoutsMs;

	private DigipostHttpClientSettings(
			Logger instantiationLogger,
			ConnectionConfig connectionConfig,
			ConnectionAmount connectionAmount,
			HttpHost proxy,
			DigipostHttpClientMillisecondTimeouts timeoutsMs) {

		this.logger = instantiationLogger;
		this.connectionConfig = connectionConfig;
		this.connectionAmount = connectionAmount;
		this.httpProxy = proxy;
		this.timeoutsMs = timeoutsMs;
	}

}
