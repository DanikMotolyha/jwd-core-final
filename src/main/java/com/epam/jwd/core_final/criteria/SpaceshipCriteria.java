package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Optional;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private String name;
    private Long flightDistance;
    private Boolean isReadyForMission;


    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Long flightDistance) {
        this.flightDistance = flightDistance;
    }

    @Override
    public String toString() {
        String name = Optional.ofNullable(this.name).isPresent() ? Optional.of(this.name).get() : "---";
        String distance = Optional.ofNullable(this.flightDistance).isPresent() ?
                Optional.of(this.flightDistance).get().toString() : "---";
        String isReady = Optional.ofNullable(isReadyForMission).isPresent() ?
                Optional.of(isReadyForMission).get().toString() : "---";
        return String.format("--- Name %s\n--- distance %s\n--- IsReadyForNextMission %s",
                name, distance, isReady);
    }

    public Boolean getReadyForMission() {
        return isReadyForMission;
    }

    public void setReadyForMission(Boolean readyForMission) {
        isReadyForMission = readyForMission;
    }

    public static final class Builder extends Criteria.Builder<SpaceshipCriteria, Builder> {
        protected SpaceshipCriteria createObject() {
            return new SpaceshipCriteria();
        }

        protected Builder thisObject() {
            return this;
        }

        public Builder setName(String name) {
            object.name = name;
            return thisObject;
        }

        public Builder setFlightDistance(Long distance) {
            object.flightDistance = distance;
            return thisObject;
        }

        public Builder setIsReadyForMission(Boolean bool) {
            object.isReadyForMission = bool;
            return thisObject;
        }
    }
}
