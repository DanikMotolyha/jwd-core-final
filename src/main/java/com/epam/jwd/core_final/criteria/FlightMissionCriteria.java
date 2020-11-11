package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;

import java.util.Optional;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private String missionName;
    private Long distance;
    private MissionResult missionResult;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        String name = Optional.ofNullable(missionName).isPresent() ? Optional.of(missionName).get() : "---";
        String distance = Optional.ofNullable(this.distance).isPresent() ? Optional.of(this.distance).get().toString() : "---";
        String missionResult = Optional.ofNullable(this.missionResult).isPresent() ? Optional.of(this.missionResult).get().toString() : "---";
        return String.format("--- Name %s\n--- distance %s\n--- mission Result %s",
                name, distance, missionResult);
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    public static final class Builder extends Criteria.Builder<FlightMissionCriteria, Builder> {
        protected FlightMissionCriteria createObject() {
            return new FlightMissionCriteria();
        }

        protected Builder thisObject() {
            return this;
        }

        public Builder setMissionName(String name) {
            object.missionName = name;
            return thisObject;
        }

        public Builder setDistance(Long distance) {
            object.distance = distance;
            return thisObject;
        }
    }
}
