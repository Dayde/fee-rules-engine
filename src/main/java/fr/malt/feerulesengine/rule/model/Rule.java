package fr.malt.feerulesengine.rule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;


public class Rule {

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private float fee;

    @JsonProperty
    private List<Restriction> restrictions;

    public Rule(String id, String name, float fee, List<Restriction> restrictions) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.restrictions = restrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(id, rule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
