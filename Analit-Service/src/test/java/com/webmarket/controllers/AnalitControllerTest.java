package com.webmarket.controllers;

import com.webmarket.services.AnalitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import webmarket.analitic.StatisticDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for AnalitController.
 * Since @KafkaListener was extracted to AnalitKafkaListener, the controller
 * is purely REST and works cleanly with @WebMvcTest.
 *
 * @see AnalitController
 */
@WebMvcTest(AnalitController.class)
class AnalitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalitService analitService;

    // -----------------------------------------------------------------------
    // GET /api/v1/analit – получить всю статистику
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /api/v1/analit — данных нет, возвращается пустой список
    void allData_whenNoData_shouldReturnEmptyList() throws Exception {
        when(analitService.getData()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/analit")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    // Сценарий: GET /api/v1/analit — есть данные, возвращается список StatisticDto
    void allData_whenDataExists_shouldReturnStatisticList() throws Exception {
        StatisticDto dto1 = new StatisticDto("Apple", 15);
        StatisticDto dto2 = new StatisticDto("Banana", 10);
        List<StatisticDto> expectedList = Arrays.asList(dto1, dto2);

        when(analitService.getData()).thenReturn(expectedList);

        mockMvc.perform(get("/api/v1/analit")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nameProduct").value("Apple"))
                .andExpect(jsonPath("$[0].count").value(15))
                .andExpect(jsonPath("$[1].nameProduct").value("Banana"))
                .andExpect(jsonPath("$[1].count").value(10));
    }

    @Test
    // Сценарий: GET /api/v1/analit — проверка, что сервис вызван ровно один раз
    void allData_shouldCallServiceOnce() throws Exception {
        when(analitService.getData()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/analit")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(analitService, times(1)).getData();
    }
}