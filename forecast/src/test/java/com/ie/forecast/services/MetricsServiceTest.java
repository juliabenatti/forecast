package com.ie.forecast.services;

import com.ie.forecast.dao.MetricsDAO;
import com.ie.forecast.models.Metric;
import com.ie.forecast.models.StatisticMetric;
import com.ie.forecast.models.dto.MetricDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MetricsServiceTest {

    @Autowired
    private MetricsDAO metricsDAO;

    @Autowired
    private MetricsService metricsService;

    @Test
    @Transactional
    void newMetricInsertedSuccessfully() {
        //given
        MetricDto validMetricParameter = new MetricDto("sensor-1", "wind", 30, LocalDate.now());

        //when
        long metricId = metricsService.newMetric(validMetricParameter);

        //then
        assertTrue(metricId != 0);
    }

    @Test
    @Transactional
    void getMetricsStatisticsWithAllSensorsSelectedSuccess() {
        //given
        Metric metric1 = new Metric("sensor-1", "wind", 30, LocalDate.now());
        Metric metric2 = new Metric("sensor-2", "wind", 15, LocalDate.now());
        metricsDAO.save(metric1);
        metricsDAO.save(metric2);

        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("all");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        List<StatisticMetric> actualValue = metricsService.getMetricsStatistics("min", sensorIds, metricNames, null, null);

        //then
        List<StatisticMetric> expectedValue = new ArrayList<>();
        expectedValue.add(new StatisticMetric("sensor-1", "wind", 30));
        expectedValue.add(new StatisticMetric("sensor-2", "wind", 15));

        assertTrue(actualValue.size() == expectedValue.size());
    }

    @Test
    @Transactional
    void getMetricsStatisticsWithStartDateAndEndDateNullSuccessfully() {
        //given
        Metric metric1 = new Metric("sensor-1", "wind", 30, LocalDate.now()); //only ID that matches with today's date.
        Metric metric2 = new Metric("sensor-2", "wind", 15, LocalDate.of(2022,02,13));
        metricsDAO.save(metric1);
        metricsDAO.save(metric2);

        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("sensor-1");
        sensorIds.add("sensor-2");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        List<StatisticMetric> actualValue = metricsService.getMetricsStatistics("min", sensorIds, metricNames, null, null);

        //then
        List<StatisticMetric> expectedValue = new ArrayList<>();
        expectedValue.add(new StatisticMetric("sensor-1", "wind", 30));

        assertTrue(actualValue.size() == expectedValue.size());
        assertTrue(actualValue.get(0).getSensorId().equals("sensor-1"));
    }

    @Test
    @Transactional
    void getMetricsStatisticsWithStartDateAndEndLongerThanAMonthSuccessfully() {
        //given

        //Ids which matches with a month description
        Metric metric1 = new Metric("sensor-1", "wind", 30, LocalDate.of(2022,02,13));
        Metric metric2 = new Metric("sensor-1", "wind", 15, LocalDate.of(2022,03,13));

        Metric metric3 = new Metric("sensor-1", "wind", 7, LocalDate.of(2022,01,13));
        metricsDAO.save(metric1);
        metricsDAO.save(metric2);
        metricsDAO.save(metric3);

        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("sensor-1");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        List<StatisticMetric> actualValue = metricsService.getMetricsStatistics("min", sensorIds, metricNames, LocalDate.of(2022, 01, 13), LocalDate.of(2022, 03, 13));

        //then
        List<StatisticMetric> expectedValue = new ArrayList<>();
        expectedValue.add(new StatisticMetric("sensor-1", "wind", 15));

        assertTrue(actualValue.size() == expectedValue.size());
        assertTrue(actualValue.get(0).getSensorId().equals(expectedValue.get(0).getSensorId()));
        assertTrue(actualValue.get(0).getStatisticValue() == expectedValue.get(0).getStatisticValue());
    }

    @Test
    @Transactional
    void getMetricsStatisticsForMinValueSuccessfully() {
        //given

        //Ids which matches with a month description
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new Metric("sensor-1", "wind", 4, LocalDate.now()));
        metrics.add(new Metric("sensor-1", "wind", 30, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 8, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 10, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 2, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 3, LocalDate.now()));

        for(Metric metric : metrics){
            metricsDAO.save(metric);
        }

        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("sensor-1");
        sensorIds.add("sensor-2");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        List<StatisticMetric> actualValue = metricsService.getMetricsStatistics("min", sensorIds, metricNames, LocalDate.now(), LocalDate.now());

        //then
        List<StatisticMetric> expectedValue = new ArrayList<>();
        expectedValue.add(new StatisticMetric("sensor-1", "wind", 4));
        expectedValue.add(new StatisticMetric("sensor-2", "wind", 8));

        assertTrue(actualValue.size() == expectedValue.size());
        assertTrue(actualValue.get(0).getSensorId().equals(expectedValue.get(0).getSensorId()));
        assertTrue(actualValue.get(0).getStatisticValue() == expectedValue.get(0).getStatisticValue());
    }

    @Test
    @Transactional
    void getMetricsStatisticsForMaxValueSuccessfully() {
        //given

        //Ids which matches with a month description
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new Metric("sensor-1", "wind", 4, LocalDate.now()));
        metrics.add(new Metric("sensor-1", "wind", 30, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 8, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 10, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 2, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 3, LocalDate.now()));

        for(Metric metric : metrics){
            metricsDAO.save(metric);
        }

        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("sensor-1");
        sensorIds.add("sensor-2");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        List<StatisticMetric> actualValue = metricsService.getMetricsStatistics("max", sensorIds, metricNames, LocalDate.now(), LocalDate.now());

        //then
        List<StatisticMetric> expectedValue = new ArrayList<>();
        expectedValue.add(new StatisticMetric("sensor-1", "wind", 30));
        expectedValue.add(new StatisticMetric("sensor-2", "wind", 10));

        assertTrue(actualValue.size() == expectedValue.size());
        assertTrue(actualValue.get(0).getSensorId().equals(expectedValue.get(0).getSensorId()));
        assertTrue(actualValue.get(0).getStatisticValue() == expectedValue.get(0).getStatisticValue());
    }

    @Test
    @Transactional
    void getMetricsStatisticsForSumValueSuccessfully() {
        //given

        //Ids which matches with a month description
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new Metric("sensor-1", "wind", 4, LocalDate.now()));
        metrics.add(new Metric("sensor-1", "wind", 30, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 8, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 10, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 2, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 3, LocalDate.now()));

        for(Metric metric : metrics){
            metricsDAO.save(metric);
        }

        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("sensor-1");
        sensorIds.add("sensor-2");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        List<StatisticMetric> actualValue = metricsService.getMetricsStatistics("sum", sensorIds, metricNames, LocalDate.now(), LocalDate.now());

        //then
        List<StatisticMetric> expectedValue = new ArrayList<>();
        expectedValue.add(new StatisticMetric("sensor-1", "wind", 34));
        expectedValue.add(new StatisticMetric("sensor-2", "wind", 18));

        assertTrue(actualValue.size() == expectedValue.size());
        assertTrue(actualValue.get(0).getSensorId().equals(expectedValue.get(0).getSensorId()));
        assertTrue(actualValue.get(0).getStatisticValue() == expectedValue.get(0).getStatisticValue());
    }

    @Test
    @Transactional
    void getMetricsStatisticsForAvgValueSuccessfully() {
        //given

        //Ids which matches with a month description
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new Metric("sensor-1", "wind", 4, LocalDate.now()));
        metrics.add(new Metric("sensor-1", "wind", 30, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 8, LocalDate.now()));
        metrics.add(new Metric("sensor-2", "wind", 10, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 2, LocalDate.now()));
        metrics.add(new Metric("sensor-3", "wind", 3, LocalDate.now()));

        for(Metric metric : metrics){
            metricsDAO.save(metric);
        }

        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("sensor-1");
        sensorIds.add("sensor-2");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        List<StatisticMetric> actualValue = metricsService.getMetricsStatistics("avg", sensorIds, metricNames, LocalDate.now(), LocalDate.now());

        //then
        List<StatisticMetric> expectedValue = new ArrayList<>();
        expectedValue.add(new StatisticMetric("sensor-1", "wind", 17));
        expectedValue.add(new StatisticMetric("sensor-2", "wind", 9));

        assertTrue(actualValue.size() == expectedValue.size());
        assertTrue(actualValue.get(0).getSensorId().equals(expectedValue.get(0).getSensorId()));
        assertTrue(actualValue.get(0).getStatisticValue() == expectedValue.get(0).getStatisticValue());
    }

    @Test
    @Transactional
    void getMetricsStatisticsWithStartDateValidAndEndDateNullFails() {
        //given
        List<String> sensorIds = new ArrayList<>();
        sensorIds.add("sensor-1");
        List<String> metricNames = new ArrayList<>();
        metricNames.add("wind");

        //when
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            metricsService.getMetricsStatistics("min", sensorIds, metricNames, LocalDate.now(), null);
        }, "End Date value was expected");

        //then
        assertTrue(thrown.getMessage().contains("When Start Date is selected, End date is expected (and vice versa)"));

    }
}
