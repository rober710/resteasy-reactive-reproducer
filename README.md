# Quarkus Reproducer project

This project is a simple reproducer for various Quarkus update errors.

When starting a Quarkus project that has controllers in other jars, the following error is thrown:

```text
2024-07-18 12:19:58,554 ERROR [io.qua.run.boo.StartupActionImpl] (Quarkus Main Thread) Error running Quarkus: java.lang.reflect.InvocationTargetException
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:118)
        at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:115)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.lang.ExceptionInInitializerError
        at java.base/jdk.internal.misc.Unsafe.ensureClassInitialized0(Native Method)
        at java.base/jdk.internal.misc.Unsafe.ensureClassInitialized(Unsafe.java:1160)
        at java.base/jdk.internal.reflect.MethodHandleAccessorFactory.ensureClassInitialized(MethodHandleAccessorFactory.java:300)
        at java.base/jdk.internal.reflect.MethodHandleAccessorFactory.newConstructorAccessor(MethodHandleAccessorFactory.java:103)
        at java.base/jdk.internal.reflect.ReflectionFactory.newConstructorAccessor(ReflectionFactory.java:200)
        at java.base/java.lang.reflect.Constructor.acquireConstructorAccessor(Constructor.java:549)
        at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499)
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:486)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:70)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
        at io.quarkus.runner.GeneratedMain.main(Unknown Source)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
        ... 3 more
Caused by: java.lang.RuntimeException: Failed to start quarkus
        at io.quarkus.runner.ApplicationImpl.<clinit>(Unknown Source)
        ... 16 more
Caused by: java.lang.IllegalStateException: Security annotation placed on resource method 'com.kenect.integrations.models.LibraryController#doSomething' wasn't detected by Quarkus during the build time.
Please report issue in Quarkus project.

        at io.quarkus.resteasy.reactive.server.runtime.security.EagerSecurityHandler$Customizer.handlers(EagerSecurityHandler.java:263)
        at org.jboss.resteasy.reactive.server.core.startup.RuntimeResourceDeployment.addHandlers(RuntimeResourceDeployment.java:629)
        at org.jboss.resteasy.reactive.server.core.startup.RuntimeResourceDeployment.buildResourceMethod(RuntimeResourceDeployment.java:208)
        at org.jboss.resteasy.reactive.server.core.startup.RuntimeDeploymentManager.deploy(RuntimeDeploymentManager.java:136)
        at io.quarkus.resteasy.reactive.server.runtime.ResteasyReactiveRecorder.createDeployment(ResteasyReactiveRecorder.java:154)
        at io.quarkus.deployment.steps.ResteasyReactiveProcessor$setupDeployment713137389.deploy_0(Unknown Source)
        at io.quarkus.deployment.steps.ResteasyReactiveProcessor$setupDeployment713137389.deploy(Unknown Source)
        ... 17 more
```

## How to reproduce
The repo contains two separate maven projects. First, get into the `library` folder, and install its artifact locally, running `mvn clean install`.
Then, get into the `app` folder and run the project with `mvn quarkus:dev`. After Quarkus loads, you'll get the error shown above.

Note that this only happens if you have the `quarkus-security` extension in the classpath. If you remove the following dependency:
```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-security</artifactId>
</dependency>
```

the project runs normally.

This doesn't happen in Quarkus 3.12.3.

This project was tested with the following environment:
- Open JDK 21.0.2
- Maven 3.9.6 (via the wrapper in this project)
- OS: Fedora Gnu/Linux 37
