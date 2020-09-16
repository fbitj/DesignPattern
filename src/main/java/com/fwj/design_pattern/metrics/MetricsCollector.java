package com.fwj.design_pattern.metrics;

import com.fwj.design_pattern.metrics.model.RequestInfo;
import com.fwj.design_pattern.metrics.storage.MetricsStorage;
import org.springframework.util.StringUtils;

/**
 * @author FangWeiJiang
 * @date 2020/9/15
 */
public class MetricsCollector {

    private MetricsStorage metricsStorage;// 基于接口而非实现编程

    // 依赖注入
    public MetricsCollector(MetricsStorage metricsStorage) {
        this.metricsStorage = metricsStorage;
    }

    public void recordRequest(RequestInfo requestInfo) {
        if (requestInfo == null || StringUtils.isEmpty(requestInfo.getApiName())) {
            return;
        }
        metricsStorage.saveReqeustInfo(requestInfo);
    }
}
