package com.ie.forecast.dao;

import com.ie.forecast.models.Metric;
import com.ie.forecast.models.StatisticMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface MetricsDAO extends JpaRepository<Metric, Long> {

    @Query( "SELECT new com.ie.forecast.models.StatisticMetric(m.sensorId, m.metricName, MIN(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "AND m.sensorId IN :sensorIds " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> minStatistic(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       @Param("metricNames") List<String> metricNames,
                                       @Param("sensorIds") List<String> sensorIds);

    @Query( "SELECT new com.ie.forecast.models.StatisticMetric(m.sensorId, m.metricName, MIN(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> minStatisticForAllSensors(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate,
                                                    @Param("metricNames") List<String> metricNames);


    @Query("SELECT new com.ie.forecast.models.StatisticMetric (m.sensorId, m.metricName, MAX(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "AND m.sensorId IN :sensorIds " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> maxStatistic(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       @Param("metricNames") List<String> metricNames,
                                       @Param("sensorIds") List<String> sensorIds);


    @Query("SELECT new com.ie.forecast.models.StatisticMetric (m.sensorId, m.metricName, MAX(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> maxStatisticForAllSensors(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate,
                                                    @Param("metricNames") List<String> metricNames);


    @Query("SELECT new com.ie.forecast.models.StatisticMetric (m.sensorId, m.metricName, SUM(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "AND m.sensorId IN :sensorIds " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> sumStatistic(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       @Param("metricNames") List<String> metricNames,
                                       @Param("sensorIds") List<String> sensorIds);


    @Query("SELECT new com.ie.forecast.models.StatisticMetric (m.sensorId, m.metricName, SUM(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> sumStatisticForAllSensors(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate,
                                                    @Param("metricNames") List<String> metricNames);


    @Query("SELECT new com.ie.forecast.models.StatisticMetric (m.sensorId, m.metricName, AVG(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "AND m.sensorId IN :sensorIds " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> avgStatistic(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       @Param("metricNames") List<String> metricNames,
                                       @Param("sensorIds") List<String> sensorIds);


    @Query("SELECT new com.ie.forecast.models.StatisticMetric (m.sensorId, m.metricName, AVG(m.metricValue)) " +
            "FROM Metric AS m " +
            "WHERE m.updatedAt BETWEEN :startDate AND :endDate " +
            "AND m.metricName IN :metricNames " +
            "GROUP BY m.sensorId, m.metricName")
    List<StatisticMetric> avgStatisticAllSensors(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate,
                                                 @Param("metricNames") List<String> metricNames);

}
