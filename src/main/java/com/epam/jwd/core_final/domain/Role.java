package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Role implements BaseEntity {
    MISSION_SPECIALIST(1L),
    FLIGHT_ENGINEER(2L),
    PILOT(3L),
    COMMANDER(4L);
    private final Long id;

    Role(Long id) {
        this.id = id;
    }

    /**
     * todo via java.lang.enum methods!
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static Role resolveRoleById(Long id) {
        try {
            for (Role role : values()) {
                if (role.getId().equals(id)) return role;
            }
            throw new UnknownEntityException("Role");
        } catch (UnknownEntityException ex) {
            Logger logger = LoggerFactory.getLogger(Role.class);
            logger.error(String.format("Cannot find role by id %d", id));
        }
        return null;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * todo via java.lang.enum methods!
     */
    @Override
    public String getName() {
        return name();
    }
}
