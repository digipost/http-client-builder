package no.posten.dpost.httpclient;

/**
 * Timeout values in milliseconds for the HTTP client.
 */
public final class DigipostHttpClientMillisecondTimeouts {

	/**
	 * Socket timeout is used for both requests and, if any,
	 * underlying layered sockets (typically for
	 * secure sockets).
	 */
	public final int socket;

	/**
	 * The connect timeout for requests.
	 */
	public final int connect;

	/**
	 * The connection request timeout for requests.
	 */
	public final int connectionRequest;

	DigipostHttpClientMillisecondTimeouts(int socket, int connect, int connectionRequest) {
	    this.socket = zeroOrGreater(socket, "socket timeout");
	    this.connect = zeroOrGreater(connect, "connect timeout");
	    this.connectionRequest = zeroOrGreater(connectionRequest, "connection request timeout");
    }

	private int zeroOrGreater(int value, String nameOfValue) {
		if (value < 0) {
			throw new IllegalArgumentException(nameOfValue + " must be 0 or greater, but was " + value);
		}
		return value;
	}

	public DigipostHttpClientMillisecondTimeouts all(int timeoutMs) {
		return new DigipostHttpClientMillisecondTimeouts(timeoutMs, timeoutMs, timeoutMs);
	}

	public DigipostHttpClientMillisecondTimeouts socket(int timeoutMs) {
		return new DigipostHttpClientMillisecondTimeouts(timeoutMs, connect, connectionRequest);
	}

	public DigipostHttpClientMillisecondTimeouts connect(int timeoutMs) {
		return new DigipostHttpClientMillisecondTimeouts(socket, timeoutMs, connectionRequest);
	}

	public DigipostHttpClientMillisecondTimeouts connectionRequest(int timeoutMs) {
		return new DigipostHttpClientMillisecondTimeouts(socket, connect, timeoutMs);
	}

    boolean isPotentiallyDangerous() {
		return socket == 0 || connect == 0 || connectionRequest == 0;
	}

}
