package fr.malt.feerulesengine.fee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

    @JsonProperty
    private String ip;

    private String countryCode;

    public Person() {
    }

    public Person(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
