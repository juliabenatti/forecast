package com.ie.forecast.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticMetric {

    @Column(name = "sensorId")
    private String sensorId;

    @Column(name = "metricName")
    private String metricName;

    @Column(name = "statisticValue")
    private double statisticValue;

}
