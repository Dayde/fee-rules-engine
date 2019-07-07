package fr.malt.feerulesengine.rule.model.restrtiction;

import fr.malt.feerulesengine.fee.model.Mission;

import java.util.List;

public enum BooleanOperatorEnum {
    AND {
        @Override
        public boolean isFulfilledBy(Mission mission, List<Restriction> restrictions) {
            return restrictions.stream().allMatch(restriction -> restriction.isFulfilledBy(mission));
        }
    },
    OR {
        @Override
        public boolean isFulfilledBy(Mission mission, List<Restriction> restrictions) {
            return restrictions.stream().anyMatch(restriction -> restriction.isFulfilledBy(mission));
        }
    };

    public abstract boolean isFulfilledBy(Mission mission, List<Restriction> restrictions);
}
