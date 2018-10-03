package com.worldpay.task.service;

import com.worldpay.task.domain.Offer;
import com.worldpay.task.domain.OfferStatus;
import com.worldpay.task.domain.repository.OfferRepository;
import com.worldpay.task.dto.AddOfferDto;
import com.worldpay.task.exception.DoesNotExistException;
import com.worldpay.task.exception.InvalidDateException;
import com.worldpay.task.exception.OfferExpiredException;
import com.worldpay.task.util.DateUtil;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.worldpay.task.util.DateUtil.DEFAULT_FORMAT;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public List<Offer> getOffers(String merchantId) {
        return offerRepository.getAllNonExpiredOffersByMerchantId(merchantId);
    }

    public Offer getOffer(String offerId) {
        return offerRepository.getOffer(offerId);
    }

    public Offer addOffer(String merchantId, AddOfferDto addOfferDto) throws InvalidDateException {
        Long now = Instant.now().toEpochMilli();
        Long expiryDate = DateUtil.parse(addOfferDto.getExpiry(), DEFAULT_FORMAT);
        if (DateUtil.getStartOfDay(expiryDate) <= DateUtil.getStartOfDay(now)) {
            throw new InvalidDateException("Expiry date must be greater than today's date.");
        }
        Offer offer = Offer.Builder.offer()
            .withId(null)
            .withDateCreated(now)
            .withLastModified(now)
            .withMerchantId(merchantId)
            .withDescription(addOfferDto.getDescription())
            .withCurrency(addOfferDto.getCurrency())
            .withAmount(addOfferDto.getAmount())
            .withOfferStatus(OfferStatus.OPEN)
            .withExpiry(DateUtil.getStartOfDay(expiryDate))
            .build();
        return offerRepository.save(offer);
    }

    public Offer cancelOffer(String offerId) throws DoesNotExistException, OfferExpiredException {
        Offer offer = offerRepository.getOffer(offerId);
        if (offer == null) {
            throw new DoesNotExistException("Offer not found");
        }
        if (DateUtil.getStartOfDay(Instant.now().toEpochMilli()) > offer.getExpiry()) {
            throw new OfferExpiredException("Offer has expired");
        }
        offer.setOfferStatus(OfferStatus.CANCELLED);
        offer.setLastModified(Instant.now().toEpochMilli());
        return offerRepository.save(offer);
    }

    public void deleteAll() {
        offerRepository.deleteAll();
    }
}
