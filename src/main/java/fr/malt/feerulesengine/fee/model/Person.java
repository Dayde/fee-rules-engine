package fr.malt.feerulesengine.fee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Person {

    @JsonProperty
    private String countryCode;

    public Person() {
    }

    public Person(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(countryCode, person.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode);
    }
}
