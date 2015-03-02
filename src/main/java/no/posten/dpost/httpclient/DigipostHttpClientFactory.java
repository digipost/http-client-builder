package no.posten.dpost.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import static no.posten.dpost.httpclient.DigipostHttpClientDefaults.DEFAULT_TIMEOUTS_MS;
import static org.apache.http.config.ConnectionConfig.DEFAULT;

public final class DigipostHttpClientFactory {


	/**
	 * Creates an {@link CloseableHttpClient HttpClient} with safe and sensible
	 * {@link DigipostHttpClientSettings#DEFAULT defaults}.
	 *
	 * @return a safe and sensible HttpClient
	 */
	public static CloseableHttpClient createDefault() {
		return create(DigipostHttpClientSettings.DEFAULT);
	}

	/**
	 * Creates an {@link CloseableHttpClient HttpClient} with given settings.
	 *
	 * @param settings configuration parameters
	 * @return a new HttpClient
	 */
	public static CloseableHttpClient create(DigipostHttpClientSettings settings) {
		return createBuilder(settings).build();
	}




	/**
	 * Create an {@link HttpClientBuilder} with safe and sensible
	 * {@link DigipostHttpClientSettings#DEFAULT defaults}.
	 *
	 * @return a safe and sensible HttpClientBuilder
	 */
	public static HttpClientBuilder createDefaultBuilder() {
		return createBuilder(DigipostHttpClientSettings.DEFAULT);
	}




	/**
	 * Create an {@link HttpClientBuilder} with given settings.
	 *
	 * @param settings configuration parameters
	 * @return a new HttpClientBuilder
	 */
	public static HttpClientBuilder createBuilder(DigipostHttpClientSettings settings) {
		if (settings.timeoutsMs.isPotentiallyDangerous()) {
			settings.logger.warn("New http client with potential dangerous settings. These settings should probably not be used in production:\n{}", settings);
		} else {
			settings.logger.info("New http client:\n{}", settings);
		}

		return HttpClientBuilder.create()
				.setDefaultConnectionConfig(settings.connectionConfig)
				.setDefaultSocketConfig(createSocketConfig(settings.timeoutsMs))
				.setDefaultRequestConfig(createRequestConfig(settings.timeoutsMs))
				.setMaxConnTotal(settings.connectionAmount.maxTotal)
				.setMaxConnPerRoute(settings.connectionAmount.maxPerRoute)
				.setProxy(settings.httpProxy);
	}



	public static RequestConfig createDefaultRequestConfig() {
		return createRequestConfig(DEFAULT_TIMEOUTS_MS);
	}

	public static RequestConfig createRequestConfig(DigipostHttpClientMillisecondTimeouts timeoutsMs) {
		return RequestConfig.custom()
				.setConnectionRequestTimeout(timeoutsMs.connectionRequest)
				.setConnectTimeout(timeoutsMs.connect)
				.setSocketTimeout(timeoutsMs.socket)
				.build();
	}


	public static SocketConfig createDefaultSocketConfig() {
		return createSocketConfig(DEFAULT_TIMEOUTS_MS);
	}

	public static SocketConfig createSocketConfig(DigipostHttpClientMillisecondTimeouts timeoutsMs) {
		return SocketConfig.custom().setSoTimeout(timeoutsMs.socket).build();
	}


	public static ConnectionConfig createDefaultConnectionConfig() {
		return DEFAULT;
	}

	private DigipostHttpClientFactory() {}

}
