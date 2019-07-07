package fr.malt.feerulesengine.fee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mission {

    @JsonProperty
    private Person client;

    @JsonProperty
    private Person freelancer;

    @JsonProperty
    private long missionLength;

    @JsonProperty
    private CommercialRelation commercialRelation;

    public Mission(Person client, Person freelancer, long missionLength, CommercialRelation commercialRelation) {
        this.client = client;
        this.freelancer = freelancer;
        this.missionLength = missionLength;
        this.commercialRelation = commercialRelation;
    }

    public Person getClient() {
        return client;
    }

    public Person getFreelancer() {
        return freelancer;
    }

    public long getMissionLength() {
        return missionLength;
    }

    public CommercialRelation getCommercialRelation() {
        return commercialRelation;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    public void setFreelancer(Person freelancer) {
        this.freelancer = freelancer;
    }

    public void setMissionLength(long missionLength) {
        this.missionLength = missionLength;
    }

    public void setCommercialRelation(CommercialRelation commercialRelation) {
        this.commercialRelation = commercialRelation;
    }
}
