package com.fwj.design_pattern.metrics.model;

import lombok.Data;

/**
 * @author FangWeiJiang
 * @date 2020/9/15
 * 处理后的统计结果信息
 */
@Data
public class RequestStat {

    private double maxResponseTime;
    private double minResponseTime;
    private double avgResponseTime;
    private double p999ResponseTime;
    private double p99responseTime;
    private long count;
    private long tps;

}
