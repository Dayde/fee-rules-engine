package fr.malt.feerulesengine.rule.model.restrtiction;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.malt.feerulesengine.fee.model.Mission;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BooleanRestriction.class, name = "boolean"),

        @JsonSubTypes.Type(value = ComparisonRestriction.class, name = "comparison")}
)
public abstract class Restriction {

    public abstract boolean isFulfilledBy(Mission mission);
}
