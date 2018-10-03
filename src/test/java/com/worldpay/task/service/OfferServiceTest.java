package com.worldpay.task.service;

import com.worldpay.task.domain.Currency;
import com.worldpay.task.domain.Offer;
import com.worldpay.task.domain.OfferStatus;
import com.worldpay.task.dto.AddOfferDto;
import com.worldpay.task.exception.DoesNotExistException;
import com.worldpay.task.exception.InvalidDateException;
import com.worldpay.task.exception.OfferExpiredException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.worldpay.task.util.DateUtil.format;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceTest {

    @Autowired
    private OfferService offerService;

    @Before
    public void init() {
        offerService.deleteAll();
    }

    @Test
    public void shouldAddOffer() throws InvalidDateException {
        String tomorrow = format(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli());
        String merchantId = UUID.randomUUID().toString();
        AddOfferDto addOfferDto = AddOfferDto.Builder
            .addOfferDto()
            .withDescription("Test offer")
            .withCurrency(Currency.USD)
            .withAmount(new BigDecimal(100))
            .withExpiry(tomorrow)
            .build();
        Offer offer = offerService.addOffer(merchantId, addOfferDto);
        Assert.assertNotNull(offer);
        Assert.assertNotNull(offer.getId());
        Assert.assertEquals("Test offer", offer.getDescription());
        Assert.assertEquals(Currency.USD, offer.getCurrency());
        Assert.assertEquals(OfferStatus.OPEN, offer.getOfferStatus());
        Assert.assertEquals(merchantId, offer.getMerchantId());
    }

    @Test(expected = InvalidDateException.class)
    public void shouldNotAddOffer() throws InvalidDateException {
        String merchantId = UUID.randomUUID().toString();
        String today = format(Instant.now().toEpochMilli());
        AddOfferDto addOfferDto = AddOfferDto.Builder
            .addOfferDto()
            .withDescription("Test offer")
            .withCurrency(Currency.USD)
            .withAmount(new BigDecimal(100))
            .withExpiry(today)
            .build();
        offerService.addOffer(merchantId, addOfferDto);
    }

    @Test
    public void shouldCancelOffer() throws InvalidDateException, OfferExpiredException, DoesNotExistException {
        String tomorrow = format(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli());
        String merchantId = UUID.randomUUID().toString();
        AddOfferDto addOfferDto = AddOfferDto.Builder
            .addOfferDto()
            .withDescription("Test offer")
            .withCurrency(Currency.USD)
            .withAmount(new BigDecimal(100))
            .withExpiry(tomorrow)
            .build();
        Offer offer = offerService.addOffer(merchantId, addOfferDto);
        offerService.cancelOffer(offer.getId());
        Offer cancelledOffer = offerService.getOffer(offer.getId());
        Assert.assertEquals(OfferStatus.CANCELLED, cancelledOffer.getOfferStatus());
    }
}
