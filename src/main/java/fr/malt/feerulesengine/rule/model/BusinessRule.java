package fr.malt.feerulesengine.rule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;


public class BusinessRule {

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private float fee;

    @JsonProperty
    private List<Restriction> restrictions;

    public BusinessRule(String id, String name, float fee, List<Restriction> restrictions) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.restrictions = restrictions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getFee() {
        return fee;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessRule rule = (BusinessRule) o;
        return Objects.equals(id, rule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
