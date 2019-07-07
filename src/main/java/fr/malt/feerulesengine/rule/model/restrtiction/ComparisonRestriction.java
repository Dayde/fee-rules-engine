package fr.malt.feerulesengine.rule.model.restrtiction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComparisonRestriction extends Restriction {

    @JsonProperty
    private String attributeName;

    @JsonProperty
    private ComparisonOperatorEnum operator;

    @JsonProperty
    private Object value;

    public ComparisonRestriction() {
    }

    public ComparisonRestriction(String attributeName, ComparisonOperatorEnum operator, Object value) {
        this.attributeName = attributeName;
        this.operator = operator;
        this.value = value;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public ComparisonOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(ComparisonOperatorEnum operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
