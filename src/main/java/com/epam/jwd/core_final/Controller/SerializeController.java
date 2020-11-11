package com.epam.jwd.core_final.Controller;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.ShortReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class SerializeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CrewMemberFactory.class);

    public static void start() {
        Short choice = ShortReader.read("1 - CrewMembers to json\n" +
                "2 - Spaceships to json\n" +
                "3 - FlightMissions to json\n");
        switch (choice) {
            case 1:
                serializeByType(CrewMember.class);
                break;
            case 2:
                serializeByType(Spaceship.class);
                break;
            case 3:
                serializeByType(FlightMission.class);
                break;
            default:
                System.out.println("Incorrect input!!!\n");
                break;
        }
    }

    private static <T extends BaseEntity> void serializeByType(Class<T> tClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(
                            ApplicationProperties.getOutputRootDir() + "/" + tClass.getSimpleName() + ".json"),
                    NassaContext.getInstance().retrieveBaseEntityList(tClass));

        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
