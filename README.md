# Payara Jaspic Bug

This project can be used to reproduce a bug in Payara 6.2023.2 with ``Record`` and ASM.

## Brief Summary

If you deploy a project which has a ``Record`` than you will get an exception during the deployment. The application itself gets deployed and the record seems to work.

## Expected Outcome

You should not get an exception during deployment.

## Current Outcome

If you deploy this project you will see the following deployment error:

```
2023-02-27 14:59:46 [#|2023-02-27T13:59:46.920+0000|SEVERE|Payara 6.2023.3-SNAPSHOT|javax.enterprise.system.tools.deployment.common|_ThreadID=221;_ThreadName=payara-executor-service-task;_TimeMillis=1677506386920;_LevelValue=1000;|
2023-02-27 14:59:46   Exception while visiting WEB-INF/classes/de/adtelligence/jdk17asmbug/MyRecord.class of size 1698
2023-02-27 14:59:46 java.lang.UnsupportedOperationException: Record requires ASM8
2023-02-27 14:59:46     at org.objectweb.asm.ClassVisitor.visitRecordComponent(ClassVisitor.java:305)
2023-02-27 14:59:46     at org.objectweb.asm.ClassReader.readRecordComponent(ClassReader.java:953)
2023-02-27 14:59:46     at org.objectweb.asm.ClassReader.accept(ClassReader.java:731)
2023-02-27 14:59:46     at org.objectweb.asm.ClassReader.accept(ClassReader.java:424)
2023-02-27 14:59:46     at org.glassfish.hk2.classmodel.reflect.Parser$5.on(Parser.java:336)
2023-02-27 14:59:46     at com.sun.enterprise.v3.server.ReadableArchiveScannerAdapter.handleEntry(ReadableArchiveScannerAdapter.java:164)
2023-02-27 14:59:46     at com.sun.enterprise.v3.server.ReadableArchiveScannerAdapter.onSelectedEntries(ReadableArchiveScannerAdapter.java:130)
2023-02-27 14:59:46     at org.glassfish.hk2.classmodel.reflect.Parser.doJob(Parser.java:321)
2023-02-27 14:59:46     at org.glassfish.hk2.classmodel.reflect.Parser.access$300(Parser.java:44)
2023-02-27 14:59:46     at org.glassfish.hk2.classmodel.reflect.Parser$3.call(Parser.java:280)
2023-02-27 14:59:46     at org.glassfish.hk2.classmodel.reflect.Parser$3.call(Parser.java:269)
2023-02-27 14:59:46     at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
2023-02-27 14:59:46     at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
2023-02-27 14:59:46     at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
2023-02-27 14:59:46     at java.base/java.lang.Thread.run(Thread.java:833)
2023-02-27 14:59:46 |#]
```


## Reproducer

* Use the code under [https://github.com/TheOnlyAl/payara-jdk17-asm-bug](https://github.com/TheOnlyAl/payara-jdk17-asm-bug) project to build the `payara-jdk17-asm-bug-1.0.0-SNAPSHOT.war` Web Archive.
* Start a Payara Server using docker. For example: `docker run -p 8080:8080 -p4848:4848 payara/server-full:6.2023.2`.
* Deploy the Application
* The log should show the error
* You can browse to `http://localhost:8080/payara-jdk17-asm-bug/rest/resource` and see that the record can at least be used correctly

## Additional information

Seems to be related to this issue: [Bug Report: 6.2022.2 with Vaadin 24.0.0.alpha6 -> UnsupportedOperationException: PermittedSubclasses requires ASM9\FISH-6931](https://github.com/payara/Payara/issues/6127)

Also seems to be related to using the wrong opcode in the currently used HK2 payara patched package. Some discussion about this:
[https://github.com/eclipse-ee4j/glassfish-hk2/issues/779](https://github.com/eclipse-ee4j/glassfish-hk2/issues/779)

There seems to be a fix which was applied but not merged or patched in the currently used version:
[https://github.com/payara/patched-src-hk2/commit/568abf93115b2d224dc24823ab2906d18b6f8b86](https://github.com/payara/patched-src-hk2/commit/568abf93115b2d224dc24823ab2906d18b6f8b86)

## Operating System

Docker Image on Windows 10 using WSL 2

## JDK Version

Checked for JDK 17 (Payara 6.2023.2)

# Payara Distribution

Payara Server Full Profile