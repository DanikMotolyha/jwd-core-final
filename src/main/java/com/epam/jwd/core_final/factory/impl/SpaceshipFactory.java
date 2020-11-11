package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.FactoryExceptions.InvalidFactoryParamsAmount;
import com.epam.jwd.core_final.exception.FactoryExceptions.InvalidFactoryParamsStructure;
import com.epam.jwd.core_final.factory.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SpaceshipFactory implements EntityFactory<Spaceship> {
    private final static SpaceshipFactory INSTANCE = new SpaceshipFactory();
    private final static Logger LOGGER = LoggerFactory.getLogger(SpaceshipFactory.class);

    private SpaceshipFactory() {

    }

    public static SpaceshipFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Spaceship create(List<?> params) {
        try {
            if (params.size() != 6)
                throw new InvalidFactoryParamsAmount("params amount not 6");
            String name = (String) params.get(0);
            Long distance = Long.valueOf((String) params.get(1));
            Map<Role, Short> crew = new LinkedHashMap<>();
            List<String> str;
            for (int i = 2; i <= 5; i++) {
                str = Arrays.asList(params.get(i).toString().split(":"));
                if (str.size() != 2)
                    throw new InvalidFactoryParamsStructure("invalid crew parameter");
                crew.put(Role.resolveRoleById(Long.valueOf(str.get(0))), Short.parseShort(str.get(1)));
            }
            return new Spaceship(name, distance, crew);
        } catch (InvalidFactoryParamsAmount | InvalidFactoryParamsStructure ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
}
