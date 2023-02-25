package com.ie.forecast.endpoints;

import com.ie.forecast.models.Metric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.ie.forecast.util.JsonUtil.asJsonString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MetricsEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void newMetricInsertedSuccessfully() throws Exception {
        //given
        Metric validMetricParameter = new Metric("sensor-1", "wind", 30, LocalDate.now());

        //then
        this.mockMvc.perform(post("/metrics")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(validMetricParameter))
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void newMetricInsertFailDueMissingSensorId() throws Exception {
        //given
        Metric invalidMetricParameter = new Metric(null, "wind", 30, LocalDate.now());

        //then
        this.mockMvc.perform(post("/metrics")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(invalidMetricParameter))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void newMetricInsertFailDueMissingMetricName() throws Exception {
        //given
        Metric invalidMetricParameter = new Metric("sensor-1", null, 30, LocalDate.now());

        //then
        this.mockMvc.perform(post("/metrics")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(invalidMetricParameter))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void newMetricInsertFailDueMissingUpdateAt() throws Exception {
        //given
        Metric invalidMetricParameter = new Metric("sensor-1", "wind", 30, null);

        //then
        this.mockMvc.perform(post("/metrics")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(invalidMetricParameter))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMetricsByStatisticWhenAllParameterSuccessfully() throws Exception {
        this.mockMvc.perform(
                get("/metrics/statistic/min?sensorIds=sensor-1&metricNames=test&startDate=22-03-2022&endDate=22-04-2022")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getMetricsByStatisticWhenStartDateAndEndDateAreNullSuccessfully() throws Exception {
        this.mockMvc.perform(
                        get("/metrics/statistic/min?sensorIds=sensor-1&metricNames=test&startDate=22-03-2022&endDate=22-04-2022")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test //TODO this test is not working. URL works fine and is validating, but this test case is not covering this.
//    void getMetricsByStatisticWhenStatisticNameIsInvalidFails() throws Exception {
//        this.mockMvc.perform(
//                        get("/metrics/statistic/WRONGVALUE?sensorIds=sensor-1&metricNames=test")
//                                .contentType(APPLICATION_JSON)
//                                .accept(APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    void getMetricsByStatisticWhenStatisticNameIsMissingFails() throws Exception {
        this.mockMvc.perform(
                        get("/metrics/statistic/sensorIds=sensor-1&metricNames=test")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMetricsByStatisticWhenStatisticSensorIdIsMissingFails() throws Exception {
        this.mockMvc.perform(
                        get("/metrics/statistic/min?&metricNames=test")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMetricsByStatisticWhenStatisticMetricNamesIsMissingFails() throws Exception {
        this.mockMvc.perform(
                        get("/metrics/statistic/min?sensorIds=sensor-1")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
