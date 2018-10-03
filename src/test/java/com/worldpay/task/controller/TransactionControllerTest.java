package com.worldpay.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldpay.task.domain.Currency;
import com.worldpay.task.dto.AddOfferDto;
import com.worldpay.task.service.OfferService;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.worldpay.task.util.DateUtil.format;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String BASE_URL = "/api/v1/offers";

    @Autowired
    private OfferService offerService;

    @Before
    public void init() {
        offerService.deleteAll();
    }

    @Test
    public void shouldAddOffer() throws Exception {

        //add
        String tomorrow = format(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli());
        String merchantId = UUID.randomUUID().toString();
        AddOfferDto addOfferDto = AddOfferDto.Builder
            .addOfferDto()
            .withDescription("Test offer")
            .withCurrency(Currency.USD)
            .withAmount(new BigDecimal(100))
            .withExpiry(tomorrow)
            .build();

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/" + merchantId)
            .content(OBJECT_MAPPER.writeValueAsString(addOfferDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", CoreMatchers.any(String.class)))
            .andExpect(jsonPath("$.amount", is(100)))
            .andExpect(jsonPath("$.currency", is("USD")))
            .andExpect(jsonPath("$.offerStatus", is("OPEN")));
    }

    @Test
    public void shouldNotAddOffer() throws Exception {

        //add
        String merchantId = UUID.randomUUID().toString();
        String today = format(Instant.now().toEpochMilli());
        AddOfferDto addOfferDto = AddOfferDto.Builder
            .addOfferDto()
            .withDescription("Test offer")
            .withCurrency(Currency.USD)
            .withAmount(new BigDecimal(100))
            .withExpiry(today)
            .build();
        mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/" + merchantId)
            .content(OBJECT_MAPPER.writeValueAsString(addOfferDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetOfferByMerchantId() throws Exception {

        //add
        String tomorrow = format(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli());
        String merchantId = UUID.randomUUID().toString();
        AddOfferDto addOfferDto = AddOfferDto.Builder
            .addOfferDto()
            .withDescription("Test offer")
            .withCurrency(Currency.USD)
            .withAmount(new BigDecimal(100))
            .withExpiry(tomorrow)
            .build();

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/" + merchantId)
            .content(OBJECT_MAPPER.writeValueAsString(addOfferDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/" + merchantId)
            .content(OBJECT_MAPPER.writeValueAsString(addOfferDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + merchantId)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", CoreMatchers.any(String.class)))
            .andExpect(jsonPath("$[1].id", CoreMatchers.any(String.class)));
    }
}