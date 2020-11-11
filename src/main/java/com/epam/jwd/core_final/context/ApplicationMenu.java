package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.Controller.*;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.util.ShortReader;

import java.time.Duration;
import java.time.LocalDateTime;

// todo replace Object with your own types
public class ApplicationMenu {


    public static void start() {
        boolean flag = true;
        LocalDateTime now = LocalDateTime.now();
        while (flag) {
            short shortInput = ShortReader.read("1 - Show . . .\n" +
                    "2 - Update . . .\n" +
                    "3 - Create mission\n" +
                    "4 - Serialize . . .\n" +
                    "5 - Cancel mission\n" +
                    "0 - Close app\n");
            switch (shortInput) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    FindController.start();
                    break;
                case 2:
                    UpdateController.start();
                    break;
                case 3:
                    CreateMissionController.start();
                    break;
                case 4:
                    SerializeController.start();
                    break;
                case 5:
                    CancelMissionController.start();
                    break;
                default:
                    System.out.println("Incorrect input!!!\n");
                    break;
            }
            Duration duration = Duration.between(now, LocalDateTime.now());
            Long long1 = duration.getSeconds();
            if (duration.getSeconds() >= ApplicationProperties.getFileRefreshRate()) {
                MissionResultController.checkMissionsResult();
                System.out.println("CHECK MISSIONS RESULT");
                now = LocalDateTime.now();
            }
        }
    }


}
