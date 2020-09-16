package com.fwj.design_pattern.metrics;

import com.fwj.design_pattern.metrics.model.RequestInfo;
import com.fwj.design_pattern.metrics.model.RequestStat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author FangWeiJiang
 * @date 2020/9/15
 */
public class Aggregator {
    public static RequestStat aggregate(List<RequestInfo> requestInfos, long durationInMillis) {
        double maxRespTime = Double.MIN_VALUE;
        double minRespTime = Double.MAX_VALUE;
        double avgRespTime = -1;
        double p999RespTime = -1;
        double p99RespTime = -1;
        double sumRespTime = 0;
        long count = 0;

        for (RequestInfo requestInfo : requestInfos) {
            ++count;
            double respTime = requestInfo.getReaponseTime();
            if (maxRespTime < respTime) {
                maxRespTime = respTime;
            }
            if (minRespTime > respTime) {
                minRespTime = respTime;
            }
            sumRespTime += respTime;
        }
        if (count != 0) {
            avgRespTime = sumRespTime / count;
        }
        long tps = count / durationInMillis * 1000;
        requestInfos.sort((o1, o2) -> {
            double diff = o1.getReaponseTime() - o2.getReaponseTime();
            if (diff > 0.0) {
                return 1;
            } else if (diff < 0.0) {
                return -1;
            } else {
                return 0;
            }
        });
        int idx999 = (int) (count * 0.999);
        int idx99 = (int) (count * 0.99);
        if (count != 0) {
            p999RespTime = requestInfos.get(idx999).getReaponseTime();
            p99RespTime = requestInfos.get(idx99).getReaponseTime();
        }
        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(maxRespTime);
        requestStat.setMinResponseTime(minRespTime);
        requestStat.setAvgResponseTime(avgRespTime);
        requestStat.setP999ResponseTime(p999RespTime);
        requestStat.setP99responseTime(p99RespTime);
        requestStat.setCount(count);
        requestStat.setTps(tps);
        return requestStat;
    }
}
