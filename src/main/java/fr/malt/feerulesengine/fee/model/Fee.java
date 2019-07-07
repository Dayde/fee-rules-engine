package fr.malt.feerulesengine.fee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fee {

    @JsonProperty
    private float fee;

    @JsonProperty
    private String reason;

    public Fee(float fee) {
        this.fee = fee;
    }

    public Fee(float fee, String reason) {
        this.fee = fee;
        this.reason = reason;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
