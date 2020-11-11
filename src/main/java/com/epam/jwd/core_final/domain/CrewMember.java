package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private Role role;
    private String memberName;
    private Rank rank;
    private Boolean isReadyForNextMissions = true;

    public CrewMember(Role role, String name, Rank rank) {
        super("CrewMember");
        this.memberName = name;
        this.role = role;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s, role: %s, rank : %s, isReadyForNextMissions: %s",
                this.getName(), memberName, role, rank, isReadyForNextMissions);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMemberName() {
        return memberName;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public void setName(String name) {
        this.memberName = name;
    }
}
