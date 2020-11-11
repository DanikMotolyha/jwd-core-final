package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.FactoryExceptions.InvalidFactoryParamsAmount;
import com.epam.jwd.core_final.factory.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {
    private final static CrewMemberFactory INSTANCE = new CrewMemberFactory();
    private final static Logger LOGGER = LoggerFactory.getLogger(CrewMemberFactory.class);
    private CrewMemberFactory() {

    }

    public static CrewMemberFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public CrewMember create(List<?> params) {
        try {
            if (params.size() != 3)
                throw new InvalidFactoryParamsAmount("params amount not 3");
            Role role = Role.resolveRoleById(Long.valueOf((String) params.get(0)));
            String name = (String) params.get(1);
            Rank rank = Rank.resolveRankById(Long.valueOf((String) params.get(2)));
            return new CrewMember(role, name, rank);
        } catch (InvalidFactoryParamsAmount ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
}
