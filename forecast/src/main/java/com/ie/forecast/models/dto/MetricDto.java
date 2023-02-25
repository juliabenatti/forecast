package com.ie.forecast.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {

    @NotBlank(message = "sensorId is mandatory")
    private String sensorId;

    @NotBlank(message = "metricName is mandatory")
    private String metricName;

    @NotNull(message = "metricName is mandatory")
    private double metricValue;

    @NotNull(message = "updatedAt is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updatedAt;
}
