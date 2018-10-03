package com.worldpay.task.domain.repository;

import com.worldpay.task.domain.Offer;
import com.worldpay.task.domain.OfferStatus;
import com.worldpay.task.util.DateUtil;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class OfferRepository {

    Map<String, Offer> offers = new ConcurrentHashMap<>();

    public Offer save(Offer offer) {
        String offerId = UUID.randomUUID().toString();
        offer.setId(offerId);
        offers.put(offerId, offer);
        return offer;
    }

    public Offer getOffer(String offerId) {
        return offers.get(offerId);
    }

    public List<Offer> getAllNonExpiredOffersByMerchantId(String merchantId) {
        List<Offer> offers = new ArrayList<>(this.offers.values());
        return offers.stream()
            .filter(o -> o.getMerchantId().equals(merchantId))
            .map(o -> {
                //we can remove this step if its okay to get incorrect data while scheduler is running
                if (DateUtil.getStartOfDay(Instant.now().toEpochMilli()) <= o.getExpiry()
                    && !o.getOfferStatus().equals(OfferStatus.CANCELLED)) {
                    o.setOfferStatus(OfferStatus.EXPIRED);
                }
                return o;
            })
            .collect(Collectors.toList());
    }

    public void deleteAll() {
        offers = new ConcurrentHashMap<>();
    }

    /**
     * Optionally expire job explicitly at 12:01 am in the morning
     */
    @Scheduled(cron = "0 1 * * * *")
    private void expireOffers() {
        List<Offer> offers = this.offers.values().stream()
            .filter(o -> DateUtil.getStartOfDay(Instant.now().toEpochMilli()) > o.getExpiry())
            .collect(Collectors.toList());
        for (Offer offer : offers) {
            offer.setOfferStatus(OfferStatus.EXPIRED);
            offer.setLastModified(Instant.now().toEpochMilli());
        }
    }
}
