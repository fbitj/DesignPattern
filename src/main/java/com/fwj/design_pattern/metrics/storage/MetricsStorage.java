package com.fwj.design_pattern.metrics.storage;

import com.fwj.design_pattern.metrics.model.RequestInfo;

import java.util.List;
import java.util.Map;

/**
 * @author FangWeiJiang
 * @date 2020/9/15
 */
public interface MetricsStorage {
    void saveReqeustInfo(RequestInfo requestInfo);

    List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp);

    Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp);
}
