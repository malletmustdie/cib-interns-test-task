package ru.malletmustdie.cibinternstesttask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.malletmustdie.cibinternstesttask.dto.SockDto;
import ru.malletmustdie.cibinternstesttask.service.SockService;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.API_V_1;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.INCOME;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.OUTCOME;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.SOCKS;

@WebMvcTest(SockController.class)
@AutoConfigureMockMvc(addFilters = false)
class SockControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SockService sockService;

    @Test
    void whenPostRequestOnIncomeThenReturnOk() throws Exception {
        var dto = getDto();
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + INCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    void whenPostRequestWithLargeCottonPartValueOnIncomeThenReturnBadRequest() throws Exception {
        var dto = getDto();
        dto.setCottonPart(101);
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + INCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostRequestWithNullParameterOnIncomeThenReturnBadRequest() throws Exception {
        var dto = getDto();
        dto.setCottonPart(null);
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + INCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostRequestWithBlankParameterOnIncomeThenReturnBadRequest() throws Exception {
        var dto = getDto();
        dto.setColor("");
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + INCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostRequestOnOutcomeThenReturnOk() throws Exception {
        var dto = getDto();
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + OUTCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    void whenPostRequestWithLargeCottonPartValueOnOutcomeThenReturnBadRequest() throws Exception {
        var dto = getDto();
        dto.setCottonPart(101);
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + OUTCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostRequestWithNullParameterOnOutcomeThenReturnBadRequest() throws Exception {
        var dto = getDto();
        dto.setCottonPart(null);
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + OUTCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenPostRequestWithBlankParameterOnOutcomeThenReturnBadRequest() throws Exception {
        var dto = getDto();
        dto.setColor("");
        var request = objectMapper.writeValueAsString(dto);
        when(sockService.createIncomeOperation(any()))
                .thenReturn(ResponseEntity.ok(dto.getQuantity()));
        mockMvc.perform(post(API_V_1 + SOCKS + OUTCOME)
                                .content(request)
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    private SockDto getDto() {
        return new SockDto().setColor("some-color").setCottonPart(80).setQuantity(3);
    }

}