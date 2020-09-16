package com.fwj.design_pattern.metrics;

import com.fwj.design_pattern.metrics.model.RequestInfo;
import com.fwj.design_pattern.metrics.show.ConsoleReporter;
import com.fwj.design_pattern.metrics.show.EmailReporter;
import com.fwj.design_pattern.metrics.storage.MetricsStorage;
import com.fwj.design_pattern.metrics.storage.RedisMetricsStorage;

/**
 * @author FangWeiJiang
 * @date 2020/9/15
 */
public class StartEntrance {

    public static void main(String[] args) {
        MetricsStorage storage = new RedisMetricsStorage();
        ConsoleReporter consoleReporter = new ConsoleReporter(storage);
        consoleReporter.startRepeatedReport(60, 60);

        EmailReporter emailReporter = new EmailReporter(storage);
        emailReporter.addToAddress("2227840410@qq.com");
        emailReporter.startDailyReport();

        MetricsCollector collector = new MetricsCollector(storage);
        collector.recordRequest(new RequestInfo("register", 123, 10234));
        collector.recordRequest(new RequestInfo("register", 223, 11234));
        collector.recordRequest(new RequestInfo("register", 323, 12433));
        collector.recordRequest(new RequestInfo("login", 23, 12434));
        collector.recordRequest(new RequestInfo("login", 1223, 14234));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
