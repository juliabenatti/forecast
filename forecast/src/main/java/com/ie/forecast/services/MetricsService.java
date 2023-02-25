package com.ie.forecast.services;

import com.ie.forecast.models.StatisticMetric;
import com.ie.forecast.models.dto.MetricDto;

import java.time.LocalDate;
import java.util.List;

public interface MetricsService {
    /**
     * Create a new metric
     * @param metric, containing sensor id, metric name, metric value and updatedAt
     * @return sensor id created
     */
    long newMetric(MetricDto metric);

    /**
     * Get statistic according to the selected option
     * @param statisticType Statistic type, being min, max, sum or avg
     * @param sensorIds List of sensor IDs
     * @param metricNames List of metric names
     * @param startDate Date range for search start
     * @param endDate Date range for search end
     * @return List of Statistic metrics, having the statistic groped by metric name and sensor id
     */
    List<StatisticMetric> getMetricsStatistics(String statisticType,
                                               List<String> sensorIds,
                                               List<String> metricNames,
                                               LocalDate startDate,
                                               LocalDate endDate);
}
