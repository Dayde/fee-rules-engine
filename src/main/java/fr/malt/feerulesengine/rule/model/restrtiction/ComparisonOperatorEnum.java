package fr.malt.feerulesengine.rule.model.restrtiction;

import fr.malt.feerulesengine.fee.model.Mission;
import org.apache.commons.lang3.StringUtils;

public enum ComparisonOperatorEnum {
    EQ {
        @Override
        public boolean isFulfilledBy(Mission mission, String attributeName, Object value) {
            Object current = retrieveAttributeValue(mission, attributeName);
            return current.equals(value);
        }
    },
    GT {
        @Override
        public boolean isFulfilledBy(Mission mission, String attributeName, Object value) {
            Object current = retrieveAttributeValue(mission, attributeName);
            if (current instanceof Comparable) {
                return ((Comparable) current).compareTo(value) >= 0;
            }
            return false;
        }

    },
    LT {
        @Override
        public boolean isFulfilledBy(Mission mission, String attributeName, Object value) {
            Object current = retrieveAttributeValue(mission, attributeName);
            if (current instanceof Comparable) {
                return ((Comparable) current).compareTo(value) <= 0;
            }
            return false;
        }
    };

    private static Object retrieveAttributeValue(Mission mission, String attributeName) {
        String[] fieldPath = attributeName.split("\\.");
        Object current = mission;
        for (String fieldName : fieldPath) {
            if (current != null) {
                try {
                    current = current.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName)).invoke(current);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return current;
    }

    public abstract boolean isFulfilledBy(Mission mission, String attributeName, Object value);
}
