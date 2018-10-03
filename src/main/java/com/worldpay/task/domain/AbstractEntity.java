package com.worldpay.task.domain;

public class AbstractEntity {
    private String id;
    private Long dateCreated;
    private Long lastModified;

    public AbstractEntity() {
    }

    public AbstractEntity(String id, Long dateCreated, Long lastModified) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public static interface IdStep {
        DateCreatedStep withId(String id);
    }

    public static interface DateCreatedStep {
        LastModifiedStep withDateCreated(Long dateCreated);
    }

    public static interface LastModifiedStep {
        BuildStep withLastModified(Long lastModified);
    }

    public static interface BuildStep {
        AbstractEntity build();
    }

    public static class Builder implements IdStep, DateCreatedStep, LastModifiedStep, BuildStep {
        private String id;
        private Long dateCreated;
        private Long lastModified;

        private Builder() {
        }

        public static IdStep abstractEntity() {
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
        public BuildStep withLastModified(Long lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        @Override
        public AbstractEntity build() {
            return new AbstractEntity(
                this.id,
                this.dateCreated,
                this.lastModified
            );
        }
    }
}
