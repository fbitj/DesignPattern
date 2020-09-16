package com.fwj.design_pattern.metrics.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author FangWeiJiang
 * @date 2020/9/15
 */
@Data
@AllArgsConstructor
public class RequestInfo {

    private String apiName;

    private double reaponseTime;

    private long timestamp;
}
