package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.strategy.impl.FindFlightMission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ShortReader {
    private final static Logger LOGGER = LoggerFactory.getLogger(ShortReader.class);

    public static Short read(String menu) {
        try {
            System.out.println(menu);
            Scanner scanner = new Scanner(System.in);
            return Short.parseShort(scanner.nextLine());
        } catch (NumberFormatException ex) {
            LOGGER.error(ex.getMessage());
        }
        return -1;
    }
}
