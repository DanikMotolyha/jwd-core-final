package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

import java.util.Optional;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private Role role;
    private String name;
    private Rank rank;
    private Boolean isReadyForMission;

    public static Builder builder() {
        return new Builder();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Boolean getReadyForMission() {
        return isReadyForMission;
    }

    public void setReadyForMission(Boolean readyForMission) {
        isReadyForMission = readyForMission;
    }

    @Override
    public String toString() {
        String name = Optional.ofNullable(this.name).isPresent() ? Optional.of(this.name).get() : "---";
        String rankName = Optional.ofNullable(rank).isPresent() ? Optional.of(rank).get().getName() : "---";
        String roleName = Optional.ofNullable(role).isPresent() ? Optional.of(role).get().getName() : "---";
        String isReady = Optional.ofNullable(isReadyForMission).isPresent() ?
                Optional.of(isReadyForMission).get().toString() : "---";
        return String.format("--- Name %s\n--- Rank %s\n--- Role %s\n--- IsReadyForNextMission %s",
                name, rankName, roleName, isReady);
    }

    public static final class Builder extends Criteria.Builder<CrewMemberCriteria, Builder> {
        protected CrewMemberCriteria createObject() {
            return new CrewMemberCriteria();
        }

        protected Builder thisObject() {
            return this;
        }

        public Builder setName(String name) {
            object.name = name;
            return thisObject;
        }

        public Builder setRole(Role role) {
            object.role = role;
            return thisObject;
        }

        public Builder setRank(Rank rank) {
            object.rank = rank;
            return thisObject;
        }

        public Builder setIsReadyForMission(Boolean bool) {
            object.isReadyForMission = bool;
            return thisObject;
        }
    }
}
