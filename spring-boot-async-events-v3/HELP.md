# Getting Started

### Reference Documentation
Spring Boot Async Application Events

* [Swagger UI](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#web)

https://medium.com/@junfeng0828/d3dfffc7c21e

- http://localhost:8081/spring-boot-async-events/api/v1/info
- http://localhost:8081/spring-boot-async-events/swagger-ui/index.html


```Java
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return buildExecutor();
    }

    @Bean(name = "applicationEventExecutor")
    public ThreadPoolTaskExecutor buildExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // ✅ Core threads — always alive, ready to process
        executor.setCorePoolSize(10);

        // ✅ Max threads — scales up under burst load
        executor.setMaxPoolSize(20);

        // ✅ Smaller queue — forces thread scale-up sooner
        // Rule of thumb: queue = maxPoolSize * 2  (not 500!)
        executor.setQueueCapacity(40);

        // ✅ Thread naming — essential for thread dump analysis
        executor.setThreadNamePrefix("AppEvent-");

        // ✅ Keep alive for threads above core size
        executor.setKeepAliveSeconds(60);

        // ✅ Allow core threads to timeout when idle
        executor.setAllowCoreThreadTimeOut(false);

        // ✅ Rejection policy — don't silently drop tasks
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // ✅ Wait for tasks to complete on shutdown
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);

        executor.initialize();
        return executor;
    }

    // ✅ Handle async exceptions — never swallow them silently
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, params) -> {
            log.error("Async exception in method: {}, params: {}", 
                method.getName(), Arrays.toString(params), throwable);
            // Send alert to monitoring system
        };
    }
}
```

```Java
@Configuration
@EnableAsync
public class AsyncConfig {

    // ✅ High priority — order processing, payments
    @Bean(name = "criticalEventExecutor")
    public ThreadPoolTaskExecutor criticalEventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("Critical-Event-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    // ✅ Low priority — emails, notifications, audit logs
    @Bean(name = "notificationEventExecutor")
    public ThreadPoolTaskExecutor notificationEventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("Notification-Event-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }
}

// Usage
@Async("criticalEventExecutor")
@EventListener
public void handleOrderEvent(OrderPlacedEvent event) { ... }

@Async("notificationEventExecutor")
@EventListener
public void handleEmailNotification(UserRegisteredEvent event) { ... }
```

```Java
@Bean
public ThreadPoolTaskExecutor buildExecutor(MeterRegistry meterRegistry) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // ... config ...
    executor.initialize();

    // Bind metrics — visible in /actuator/metrics
    ExecutorServiceMetrics.monitor(
        meterRegistry, 
        executor.getThreadPoolExecutor(), 
        "app.event.executor"
    );
    return executor;
}
```

```CSS
Summary of What to Fix
Parameter           Current     Recommended         Why    
corePoolSize        2           8–10                Too few always-on threads
maxPoolSize         5           16–20               Can't scale under burst
queueCapacity       500         30–50               Delays thread scale-up too long
rejectionPolicy     default     CallerRunsPolicy    Prevents silent task drops
```

```Java
```