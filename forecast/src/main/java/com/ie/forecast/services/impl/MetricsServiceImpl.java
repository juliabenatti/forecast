package com.ie.forecast.services.impl;

import com.ie.forecast.dao.MetricsDAO;
import com.ie.forecast.models.Metric;
import com.ie.forecast.models.StatisticMetric;
import com.ie.forecast.models.dto.MetricDto;
import com.ie.forecast.services.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsServiceImpl implements MetricsService {

    @Autowired
    MetricsDAO metricsDAO;


    public long newMetric(MetricDto metric) {
        Metric metricToBeInsert = new Metric(metric.getSensorId(), metric.getMetricName(), metric.getMetricValue(), metric.getUpdatedAt());

        Metric insertedMetric = metricsDAO.save(metricToBeInsert);

        return insertedMetric.getId();
    }

    public List<StatisticMetric> getMetricsStatistics(String statisticType,
                                                      List<String> sensorIds,
                                                      List<String> metricNames,
                                                      LocalDate startDate,
                                                      LocalDate endDate) {

        boolean searchAllSensorIds = false;
        if (sensorIds.size() == 1 && sensorIds.get(0).equals("all")){
            searchAllSensorIds = true;
        }

        // Validate dates
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null)) {
            throw new IllegalArgumentException("When Start Date is selected, End date is expected (and vice versa)");
        }
        else if (startDate == null && endDate == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = LocalDate.now().format(formatter);

            startDate = LocalDate.parse(formattedDate, formatter);
            endDate = startDate;
        }
        else if (ChronoUnit.MONTHS.between(startDate, endDate) > 1) {
            startDate = endDate.minusMonths(1);
        }

        List<StatisticMetric> metricStatisticsList = new ArrayList<>();
        switch (statisticType) {
            case "min":
                if (searchAllSensorIds)
                    metricStatisticsList = metricsDAO.minStatisticForAllSensors(startDate, endDate, metricNames);
                else
                    metricStatisticsList = metricsDAO.minStatistic(startDate, endDate, metricNames, sensorIds);
                break;
            case "max":
                if (searchAllSensorIds)
                    metricStatisticsList = metricsDAO.maxStatisticForAllSensors(startDate, endDate, metricNames);
                else
                    metricStatisticsList = metricsDAO.maxStatistic(startDate, endDate, metricNames, sensorIds);
                break;
            case "sum":
                if (searchAllSensorIds)
                    metricStatisticsList = metricsDAO.sumStatisticForAllSensors(startDate, endDate, metricNames);
                else
                    metricStatisticsList = metricsDAO.sumStatistic(startDate, endDate, metricNames, sensorIds);
                break;
            case "avg":
                if (searchAllSensorIds)
                    metricStatisticsList = metricsDAO.avgStatisticAllSensors(startDate, endDate, metricNames);
                else
                    metricStatisticsList = metricsDAO.avgStatistic(startDate, endDate, metricNames, sensorIds);
                break;
        }

        return metricStatisticsList;
    }

}
