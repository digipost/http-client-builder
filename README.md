# Digipost HTTP Client Builder

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.digipost/http-client-builder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/no.digipost/http-client-builder5)
![](https://github.com/digipost/http-client-builder/workflows/Build%20and%20deploy/badge.svg)
[![License](https://img.shields.io/badge/license-Apache%202-blue)](https://github.com/digipost/http-client-builder/blob/main/LICENSE)

A tiny library for building instances of [Apache HttpClient](https://hc.apache.org/httpcomponents-client-ga/) configured with sensible defaults.

## Usage

Here are some common use cases to quickly get you started.

Client with sensible defaults:
```java
CloseableHttpClient client = HttpClientFactory.createDefault();
```

### Custom ssl context

```java
PoolingHttpClientConnectionManager connectionManager = HttpClientConnectionManagerFactory
        .createDefaultBuilder()
        .setSSLSocketFactory(
                SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(getSslContext())
                        .setTlsVersions(TLS.V_1_3)
                        .build()
        )
        .build();

CloseableHttpClient client = HttpClientFactory.create(connectionManager);
```

### Disable connection monitor
The connection monitor evicts idle and expired connections. It is by default started when the client is built.

It can be disabled by the following code:
```java
CloseableHttpClient client = HttpClientFactory.create(HttpClientSettings.DEFAULT.connectionEvictionPolicy(ConnectionEvictionPolicy.NONE));
```

PS: If connection monitor is not disabled, and a new connection manager is set after the client builder has been created (i.e `clientBuilder.setConnectionManager(other)`), the 'old' connection monitor will still run and monitor the old connection manager.
