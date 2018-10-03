package com.worldpay.task.dto;

import com.worldpay.task.domain.Currency;
import java.math.BigDecimal;

public class AddOfferDto {
    private String description;
    private Currency currency;
    private BigDecimal amount;
    private String expiry;

    public AddOfferDto() {
    }

    public AddOfferDto(String description, Currency currency, BigDecimal amount, String expiry) {
        this.description = description;
        this.currency = currency;
        this.amount = amount;
        this.expiry = expiry;
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

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public static interface DescriptionStep {
        CurrencyStep withDescription(String description);
    }

    public static interface CurrencyStep {
        AmountStep withCurrency(Currency currency);
    }

    public static interface AmountStep {
        ExpiryStep withAmount(BigDecimal amount);
    }

    public static interface ExpiryStep {
        BuildStep withExpiry(String expiry);
    }

    public static interface BuildStep {
        AddOfferDto build();
    }

    public static class Builder implements DescriptionStep, CurrencyStep, AmountStep, ExpiryStep, BuildStep {
        private String description;
        private Currency currency;
        private BigDecimal amount;
        private String expiry;

        private Builder() {
        }

        public static DescriptionStep addOfferDto() {
            return new Builder();
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
        public ExpiryStep withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public BuildStep withExpiry(String expiry) {
            this.expiry = expiry;
            return this;
        }

        @Override
        public AddOfferDto build() {
            return new AddOfferDto(
                this.description,
                this.currency,
                this.amount,
                this.expiry
            );
        }
    }
}
