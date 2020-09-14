# Digipost HTTP Client Builder

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.digipost/http-client-builder3/badge.svg)](https://maven-badges.herokuapp.com/maven-central/no.digipost/http-client-builder3)
![](https://github.com/digipost/http-client-builder3/workflows/Build%20snapshot/badge.svg)
[![License](https://img.shields.io/badge/license-Apache%202-blue)](https://github.com/digipost/http-client-builder3/blob/master/LICENCE)

A tiny library for building instances of [Apache HttpClient](https://hc.apache.org/httpcomponents-client-ga/) configured with sensible defaults.


## Example usage
With self controlled connection monitor:
```java
PoolingHttpClientConnectionManagerBuilder connectionManager = DigipostHttpClientConnectionManagerFactory.createDefaultBuilder();
DigipostHttpClientConnectionMonitor connectionMonitor = new DigipostHttpClientConnectionMonitor(connectionManager);
HttpClientBuilder clientBuilder = DigipostHttpClientFactory.createBuilder(connectionManager);

//Somewhere
connectionMonitor.start();
```

With connection monitor started at client creation:
```java
DigipostHttpClientSettings settings = DigipostHttpClientSettings.DEFAULT.connectionMonitorPolicy(ONLY_EVICT_EXPIRED_CONNECTIONS);
HttpClient client = DigipostHttpClientFactory.create(settings);
```
