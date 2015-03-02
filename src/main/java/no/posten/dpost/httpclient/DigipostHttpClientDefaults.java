package no.posten.dpost.httpclient;


public final class DigipostHttpClientDefaults {

	/**
	 * Socket timeout: {@value #SOCKET_TIMEOUT_MS} ms.
	 */
	public static final int SOCKET_TIMEOUT_MS = 10_000;

	/**
	 * Connect timeout: {@value #CONNECT_TIMEOUT_MS} ms.
	 */
	public static final int CONNECT_TIMEOUT_MS = 10_000;

	/**
	 * Connection request timeout: {@value #CONNECTION_REQUEST_TIMEOUT_MS} ms.
	 */
	public static final int CONNECTION_REQUEST_TIMEOUT_MS = 10_000;


	/**
	 * The default timeouts:
	 * <ul>
	 *   <li>{@link #SOCKET_TIMEOUT_MS}: {@value #SOCKET_TIMEOUT_MS} ms</li>
	 *   <li>{@link #CONNECT_TIMEOUT_MS}: {@value #CONNECT_TIMEOUT_MS} ms</li>
	 *   <li>{@link #CONNECTION_REQUEST_TIMEOUT_MS}: {@value #CONNECTION_REQUEST_TIMEOUT_MS} ms</li>
	 * </ul>
	 */
	public static final DigipostHttpClientMillisecondTimeouts DEFAULT_TIMEOUTS_MS = new DigipostHttpClientMillisecondTimeouts(SOCKET_TIMEOUT_MS, CONNECT_TIMEOUT_MS, CONNECTION_REQUEST_TIMEOUT_MS);



	/**
	 * Maximum <strong>{@value #MAX_CONNECTIONS_PER_ROUTE_NORMAL}</strong> connections
	 * <em>per route</em> for normal amount of traffic.
	 * <p>
	 * Apache HttpClient default: 2
	 */
	public static final int MAX_CONNECTIONS_PER_ROUTE_NORMAL = 10;

	/**
	 * Maximum <strong>{@value #MAX_CONNECTIONS_TOTAL_NORMAL}</strong>
	 * total connections for normal amount of traffic.
	 * <p>
	 * Apache HttpClient default: 20
	 */
	public static final int MAX_CONNECTIONS_TOTAL_NORMAL = MAX_CONNECTIONS_PER_ROUTE_NORMAL;



	/**
	 * Maximum <strong>{@value #MAX_CONNECTIONS_PER_ROUTE_MEDIUM}</strong> connections
	 * <em>per route</em> for medium amount of traffic.
	 * <p>
	 * Apache HttpClient default: 2
	 */
	public static final int MAX_CONNECTIONS_PER_ROUTE_MEDIUM = 50;

	/**
	 * Maximum <strong>{@value #MAX_CONNECTIONS_TOTAL_MEDIUM}</strong>
	 * total connections for medium amount of traffic.
	 * <p>
	 * Apache HttpClient default: 20
	 */
	public static final int MAX_CONNECTIONS_TOTAL_MEDIUM = MAX_CONNECTIONS_PER_ROUTE_MEDIUM;



	/**
	 * Maximum <strong>{@value #MAX_CONNECTIONS_PER_ROUTE_HIGH}</strong> connections
	 * <em>per route</em> for high amount of traffic.
	 * <p>
	 * Apache HttpClient default: 2
	 */
	public static final int MAX_CONNECTIONS_PER_ROUTE_HIGH = 100;

	/**
	 * Maximum <strong>{@value #MAX_CONNECTIONS_TOTAL_HIGH}</strong>
	 * total connections for high amount of traffic.
	 * <p>
	 * Apache HttpClient default: 20
	 */
	public static final int MAX_CONNECTIONS_TOTAL_HIGH = MAX_CONNECTIONS_PER_ROUTE_HIGH;




	public static final ConnectionAmount CONNECTION_AMOUNT_NORMAL = new ConnectionAmount(MAX_CONNECTIONS_TOTAL_NORMAL, MAX_CONNECTIONS_PER_ROUTE_NORMAL);
	public static final ConnectionAmount CONNECTION_AMOUNT_MEDIUM = new ConnectionAmount(MAX_CONNECTIONS_TOTAL_MEDIUM, MAX_CONNECTIONS_PER_ROUTE_MEDIUM);
	public static final ConnectionAmount CONNECTION_AMOUNT_HIGH = new ConnectionAmount(MAX_CONNECTIONS_TOTAL_HIGH, MAX_CONNECTIONS_PER_ROUTE_HIGH);


	public static final class ConnectionAmount {

		public final int maxTotal;
		public final int maxPerRoute;

		private ConnectionAmount(int maxTotal, int maxPerRoute) {
			this.maxTotal = maxTotal;
			this.maxPerRoute = maxPerRoute;
		}
	}

	private DigipostHttpClientDefaults() {}
}
