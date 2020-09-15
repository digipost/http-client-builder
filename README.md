# Digipost HTTP Client Builder

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.digipost/http-client-builder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/no.digipost/http-client-builder)
![](https://github.com/digipost/http-client-builder/workflows/Build%20snapshot/badge.svg)
[![License](https://img.shields.io/badge/license-Apache%202-blue)](https://github.com/digipost/http-client-builder/blob/master/LICENSE)

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

CloseableHttpClient client = HttpClientFactory.createDefault(connectionManager);
```

### Disable connection monitor
The connection monitor evicts idle and expired connections. It is by default started when the client is built.

It can be disabled by the following code:
```java
CloseableHttpClient client = HttpClientFactory.create(HttpClientSettings.DEFAULT.connectionEvictionPolicy(ConnectionEvictionPolicy.NONE));
```

