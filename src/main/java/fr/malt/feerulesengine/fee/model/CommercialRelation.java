package fr.malt.feerulesengine.fee.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

public class CommercialRelation {

    @JsonProperty
    private DateTime firstMission;

    @JsonProperty
    private DateTime lastMission;

    public CommercialRelation(DateTime firstMission, DateTime lastMission) {
        this.firstMission = firstMission;
        this.lastMission = lastMission;
    }

    public DateTime getFirstMission() {
        return firstMission;
    }

    public void setFirstMission(DateTime firstMission) {
        this.firstMission = firstMission;
    }

    public DateTime getLastMission() {
        return lastMission;
    }

    public void setLastMission(DateTime lastMission) {
        this.lastMission = lastMission;
    }
}
