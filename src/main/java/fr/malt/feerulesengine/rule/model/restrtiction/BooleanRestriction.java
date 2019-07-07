package fr.malt.feerulesengine.rule.model.restrtiction;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.malt.feerulesengine.fee.model.Mission;

import java.util.List;

public class BooleanRestriction extends Restriction {

    @JsonProperty
    private BooleanOperatorEnum operator;

    @JsonProperty
    private List<Restriction> restrictions;

    public BooleanRestriction() {
    }

    public BooleanRestriction(BooleanOperatorEnum operator, List<Restriction> restrictions) {
        this.operator = operator;
        this.restrictions = restrictions;
    }

    public BooleanOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(BooleanOperatorEnum operator) {
        this.operator = operator;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public boolean isFulfilledBy(Mission mission) {
        return operator.isFulfilledBy(mission, restrictions);
    }
}
