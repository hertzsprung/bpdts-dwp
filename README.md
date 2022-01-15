## Compile and test

```shell
./gradlew build
```

## Run

```shell
./gradlew bootRun
```

And in another terminal:
```shell
curl http://localhost:8080/users/inOrNearLondon
```

## Potential improvements
* Log calls this API and the underlying BPDTS API
* Since [`RestTemplate` will be deprecated](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html), learn how to use non-blocking `WebClient` in production code