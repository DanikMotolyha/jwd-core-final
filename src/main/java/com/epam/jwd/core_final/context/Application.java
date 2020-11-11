package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Application {
    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void start() {
        try {
            LOGGER.info("Start app.");
            PropertyReaderUtil.loadProperties();
            NassaContext.getInstance().init();
            ApplicationMenu.start();
            LOGGER.info("End app.");
        } catch (InvalidStateException | IOException ex) {
            LOGGER.error("App cant start :( " + ex.getMessage());
        }
    }
}
