# Getting Started

### Reference URLs

- http://localhost:8081/weather-app/api/v1/info
- http://localhost:8081/weather-app/swagger-ui.html
- http://localhost:8081/weather-app/api/v1/sensor/average?startTime=2025-10-01T22:11:41.400Z&endTime=2025-10-02T00:13:41.400Z
- http://localhost:8081/weather-app/api/v1/sensor/Sensor-101/average?metric=temperature&startTime=2025-10-01T22%3A11%3A41.400Z&endTime=2025-10-02T00%3A13%3A41.400Z

```json
{
  "sensorId": "Sensor-101",
  "temperature": 25.5,
  "humidity": 60.0,
  "windSpeed": 15.0,
  "timestamp": "2025-10-05T22:11:41.400Z"
}
```

```json
- Date Range Example:
- start: 2025-10-01T22:11:41.400Z 
- end: 2025-10-02T00:13:41.400Z
```
```sql
CREATE TABLE sensor_readings (
    id SERIAL PRIMARY KEY,
    sensor_id VARCHAR(255) NOT NULL,
    temperature decimal NOT NULL,
    humidity decimal NOT NULL,
    wind_speed decimal NOT NULL,
    timestamp TIMESTAMP NOT NULL
);
```
* [INFO URL](http://localhost:8081/weather-app/api/v1/info)
* [Swagger UI](http://localhost:8081/weather-app/swagger-ui.html)

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