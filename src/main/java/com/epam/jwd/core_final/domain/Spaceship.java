package com.epam.jwd.core_final.domain;

import java.util.Map;
import java.util.Objects;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    private String spaceshipName;
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMissions = true;

    //todo
    public Spaceship(String name, Long distance, Map<Role, Short> crew) {
        super("SpaceShip");
        this.spaceshipName = name;
        flightDistance = distance;
        this.crew = crew;
    }


    public Long getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Long flightDistance) {
        this.flightDistance = flightDistance;
    }

    @Override
    public String toString() {
        StringBuffer crewMembers = new StringBuffer();
        crew.forEach(
                (role, count) -> crewMembers
                        .append(Objects.requireNonNull(Role.resolveRoleById(role.getId())).getName())
                        .append(": ")
                        .append(count)
                        .append("\n")
        );
        return String.format(
                "%s %s, flightDistance : %s, isReadyForNextMissions: %s\n",
                this.getName(), spaceshipName, flightDistance, isReadyForNextMissions.toString()) + crewMembers;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public void setCrew(Map<Role, Short> crew) {
        this.crew = crew;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public String getSpaceshipName() {
        return spaceshipName;
    }

    public void setSpaceshipName(String spaceshipName) {
        this.spaceshipName = spaceshipName;
    }
}
