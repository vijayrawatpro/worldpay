package com.worldpay.task.domain;

import java.math.BigDecimal;

public class Offer extends AbstractEntity {
    private String merchantId;
    private String description;
    private Currency currency;
    private BigDecimal amount;
    private OfferStatus offerStatus;
    private Long expiry;

    public Offer() {
    }

    public Offer(String id, Long dateCreated, Long lastModified, String merchantId, String description,
        Currency currency, BigDecimal amount, OfferStatus offerStatus, Long expiry) {
        super(id, dateCreated, lastModified);
        this.merchantId = merchantId;
        this.description = description;
        this.currency = currency;
        this.amount = amount;
        this.offerStatus = offerStatus;
        this.expiry = expiry;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    public static interface IdStep {
        DateCreatedStep withId(String id);
    }

    public static interface DateCreatedStep {
        LastModifiedStep withDateCreated(Long dateCreated);
    }

    public static interface LastModifiedStep {
        MerchantIdStep withLastModified(Long lastModified);
    }

    public static interface MerchantIdStep {
        DescriptionStep withMerchantId(String merchantId);
    }

    public static interface DescriptionStep {
        CurrencyStep withDescription(String description);
    }

    public static interface CurrencyStep {
        AmountStep withCurrency(Currency currency);
    }

    public static interface AmountStep {
        OfferStatusStep withAmount(BigDecimal amount);
    }

    public static interface OfferStatusStep {
        ExpiryStep withOfferStatus(OfferStatus offerStatus);
    }

    public static interface ExpiryStep {
        BuildStep withExpiry(Long expiry);
    }

    public static interface BuildStep {
        Offer build();
    }

    public static class Builder
        implements IdStep, DateCreatedStep, LastModifiedStep, MerchantIdStep, DescriptionStep, CurrencyStep, AmountStep,
        OfferStatusStep, ExpiryStep, BuildStep {
        private String id;
        private Long dateCreated;
        private Long lastModified;
        private String merchantId;
        private String description;
        private Currency currency;
        private BigDecimal amount;
        private OfferStatus offerStatus;
        private Long expiry;

        private Builder() {
        }

        public static IdStep offer() {
            return new Builder();
        }

        @Override
        public DateCreatedStep withId(String id) {
            this.id = id;
            return this;
        }

        @Override
        public LastModifiedStep withDateCreated(Long dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        @Override
        public MerchantIdStep withLastModified(Long lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        @Override
        public DescriptionStep withMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        @Override
        public CurrencyStep withDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public AmountStep withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        @Override
        public OfferStatusStep withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public ExpiryStep withOfferStatus(OfferStatus offerStatus) {
            this.offerStatus = offerStatus;
            return this;
        }

        @Override
        public BuildStep withExpiry(Long expiry) {
            this.expiry = expiry;
            return this;
        }

        @Override
        public Offer build() {
            return new Offer(
                this.id,
                this.dateCreated,
                this.lastModified,
                this.merchantId,
                this.description,
                this.currency,
                this.amount,
                this.offerStatus,
                this.expiry
            );
        }
    }
}
