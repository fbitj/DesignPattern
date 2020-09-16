package com.fwj.design_pattern.metrics.show;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwj.design_pattern.metrics.Aggregator;
import com.fwj.design_pattern.metrics.model.RequestInfo;
import com.fwj.design_pattern.metrics.model.RequestStat;
import com.fwj.design_pattern.metrics.storage.MetricsStorage;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author FangWeiJiang
 * @date 2020/9/15
 */
public class ConsoleReporter {
    private MetricsStorage metricsStorage;
    private ScheduledExecutorService executor;

    public ConsoleReporter(MetricsStorage metricsStorage) {
        this.metricsStorage = metricsStorage;
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        executor.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                // 数据库取数据
                long endTimeInMillis = System.currentTimeMillis();
                long startTimeInMillis = endTimeInMillis - durationInSeconds;
                Map<String, List<RequestInfo>> requestInfos = metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
                // 计算数据
                long durationInMillis = durationInSeconds * 1000;
                HashMap<String, RequestStat> stats = new HashMap<>();
                for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
                    String apiName = entry.getKey();
                    List<RequestInfo> requestInfosPerApi = entry.getValue();
                    RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
                    stats.put(apiName, requestStat);
                }
                // 展示到命令行
                System.out.println("Time Span:[" + startTimeInMillis + ", " + endTimeInMillis + "]");
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(mapper.writeValueAsString(stats));
            }
        }, 0, periodInSeconds, TimeUnit.SECONDS);
    }
}
