package fr.malt.feerulesengine.rule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.malt.feerulesengine.rule.model.restrtiction.Restriction;

import java.util.Objects;


public class BusinessRule {

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private float fee;

    @JsonProperty
    private Restriction restriction;

    public BusinessRule(String id, String name, float fee, Restriction restriction) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.restriction = restriction;
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

    public Restriction getRestriction() {
        return restriction;
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

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
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
