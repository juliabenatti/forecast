package com.ie.forecast.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="metrics")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sensorId")
    private String sensorId;

    @Column(name = "metricName")
    private String metricName;

    @Column(name = "metricValue")
    private double metricValue;

    @Column(name = "updatedAt")
    private LocalDate updatedAt;

    public Metric(String sensorId, String metricName, double metricValue, LocalDate updatedAt) {
        this.sensorId = sensorId;
        this.metricName = metricName;
        this.metricValue = metricValue;
        this.updatedAt = updatedAt;
    }

}
