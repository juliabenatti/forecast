package com.ie.forecast.endpoints;

import com.ie.forecast.models.Metric;
import com.ie.forecast.models.StatisticMetric;
import com.ie.forecast.models.dto.MetricDto;
import com.ie.forecast.services.MetricsService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@RestController
public class MetricsEndpoints {

    @Autowired
    MetricsService metricsService;


    @RequestMapping(path="/metrics", method = RequestMethod.POST)
    public ResponseEntity<Metric> newMetric(@Valid @RequestBody MetricDto metric) {
        metricsService.newMetric(metric);

        return new ResponseEntity("Metric created for sensor id "+metric.getSensorId(), HttpStatus.CREATED);

    }

    @RequestMapping(path="metrics/statistic/{statisticType}", method = RequestMethod.GET)
    public ResponseEntity<List<StatisticMetric>> getMetricsByStatistic(
            @NotNull @Pattern(regexp = "min|max|sum|avg", message = "Invalid value") @PathVariable String statisticType,
            @NotNull @RequestParam List<String> sensorIds,
            @NotNull @RequestParam List<String> metricNames,
            @Nullable @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(required = false) LocalDate startDate,
            @Nullable @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(required = false) LocalDate endDate) {


            return ResponseEntity.ok(metricsService.getMetricsStatistics(statisticType, sensorIds, metricNames, startDate, endDate));


    }
}
