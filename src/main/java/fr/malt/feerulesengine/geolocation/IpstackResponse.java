package fr.malt.feerulesengine.geolocation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpstackResponse {

    @JsonProperty("country_code")
    private String countryCode;

    public IpstackResponse() {
    }

    public IpstackResponse(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
