# Quarkus NPE Reproducer project

This project is a simple reproducer for a NPE when using the `resteasy-reactive` extension.

The exception I get when calling the `/hello` endpoint is:

```text
jakarta.ws.rs.ProcessingException: java.lang.NullPointerException: Cannot invoke "org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl.getRestClientRequestContext()" because "requestContext" is null
        at org.jboss.resteasy.reactive.client.handlers.ClientResponseFilterRestHandler.handle(ClientResponseFilterRestHandler.java:25)
        at org.jboss.resteasy.reactive.client.handlers.ClientResponseFilterRestHandler.handle(ClientResponseFilterRestHandler.java:10)
        at org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.invokeHandler(AbstractResteasyReactiveContext.java:231)
        at org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)
        at org.jboss.resteasy.reactive.client.impl.RestClientRequestContext$1.lambda$execute$0(RestClientRequestContext.java:314)
        at io.vertx.core.impl.ContextInternal.dispatch(ContextInternal.java:279)
        at io.vertx.core.impl.ContextInternal.dispatch(ContextInternal.java:261)
        at io.vertx.core.impl.ContextInternal.lambda$runOnContext$0(ContextInternal.java:59)
        at io.netty.util.concurrent.AbstractEventExecutor.runTask(AbstractEventExecutor.java:173)
        at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:166)
        at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:470)
        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:566)
        at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
        at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.lang.NullPointerException: Cannot invoke "org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl.getRestClientRequestContext()" because "requestContext" is null
        at io.quarkus.rest.client.reactive.runtime.MicroProfileRestClientResponseFilter.filter(MicroProfileRestClientResponseFilter.java:38)
        at org.jboss.resteasy.reactive.client.handlers.ClientResponseFilterRestHandler.handle(ClientResponseFilterRestHandler.java:21)
        ... 15 more
```

## How to reproduce
Run the project and call the `/hello` endpoint with curl.
Note: this project was tested with the following environment:
- Open JDK 21.0.2
- Maven 3.9.6 (via the wrapper in this project)
- OS: Fedora Gnu/Linux 37

```shell
curl http://127.0.0.1:8080/hello
```
The expected result from the endpoint is the following text message:
```text

    -=[ teapot ]=-

       _...._
     .'  _ _ `.
    | ."` ^ `". _,
    \_;`"---"`|//
      |       ;/
      \_     _/
        `"""`
```
Instead, I get an exception response:
```text
{"details":"Error id e26b4cf0-d92b-4536-bffb-fd174166c4f9-1, jakarta.ws.rs.ProcessingException: java.lang.NullPointerException: Cannot invoke \"org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl.getRestClientRequestContext()\" because \"requestContext\" is null","stack":"jakarta.ws.rs.ProcessingException: java.lang.NullPointerException: Cannot invoke \"org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl.getRestClientRequestContext()\" because \"requestContext\" is null\n\tat org.jboss.resteasy.reactive.client.handlers.ClientResponseFilterRestHandler.handle(ClientResponseFilterRestHandler.java:25)\n\tat org.jboss.resteasy.reactive.client.handlers.ClientResponseFilterRestHandler.handle(ClientResponseFilterRestHandler.java:10)\n\tat org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.invokeHandler(AbstractResteasyReactiveContext.java:231)\n\tat org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)\n\tat org.jboss.resteasy.reactive.client.impl.RestClientRequestContext$1.lambda$execute$0(RestClientRequestContext.java:314)\n\tat io.vertx.core.impl.ContextInternal.dispatch(ContextInternal.java:279)\n\tat io.vertx.core.impl.ContextInternal.dispatch(ContextInternal.java:261)\n\tat io.vertx.core.impl.ContextInternal.lambda$runOnContext$0(ContextInternal.java:59)\n\tat io.netty.util.concurrent.AbstractEventExecutor.runTask(AbstractEventExecutor.java:173)\n\tat io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:166)\n\tat io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:470)\n\tat io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:566)\n\tat io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)\n\tat io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)\n\tat io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)\n\tat java.base/java.lang.Thread.run(Thread.java:1583)\nCaused by: java.lang.NullPointerException: Cannot invoke \"org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl.getRestClientRequestContext()\" because \"requestContext\" is null\n\tat io.quarkus.rest.client.reactive.runtime.MicroProfileRestClientResponseFilter.filter(MicroProfileRestClientResponseFilter.java:38)\n\tat org.jboss.resteasy.reactive.client.handlers.ClientResponseFilterRestHandler.handle(ClientResponseFilterRestHandler.java:21)\n\t... 15 more"}
```

When I make the change in the PR https://github.com/quarkusio/quarkus/pull/41749, the exception is not thrown anymore and the expected response is returned.
After compiling the extension and installing the jar locally, just add this to the pom to test the fix:
```xml
<dependency>
    <groupId>io.quarkus.resteasy.reactive</groupId>
    <artifactId>resteasy-reactive-client</artifactId>
    <version>[999-SNAPSHOT]</version>
</dependency>
```