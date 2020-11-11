package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.strategy.FindEntityStrategy;
import com.epam.jwd.core_final.util.ShortReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public enum FindCrewMember implements FindEntityStrategy {
    INSTANCE;
    private final static Logger LOGGER = LoggerFactory.getLogger(FindCrewMember.class);

    @Override
    public void find() {
        try {
            Scanner scanner = new Scanner(System.in);
            CrewMemberCriteria criteria = new CrewMemberCriteria();
            boolean flag = true;
            while (flag) {
                short choice = ShortReader.read(
                        "choose criteria to search\n" +
                                "1 - Name\n" +
                                "2 - role\n" +
                                "3 - rank\n" +
                                "4 - isReadyForNextMissions\n" +
                                "0 - find");
                switch (choice) {
                    case 0:
                        if (criteria.getName() == null &&
                                criteria.getReadyForMission() == null &&
                                criteria.getRole() == null &&
                                criteria.getRank() == null) {
                            System.out.println("You dont choose anything. cannot start find");
                        } else {
                            findByCriteria(criteria);
                            flag = false;
                        }
                        break;
                    case 1:
                        criteria.setName(scanner.nextLine());
                        break;
                    case 2:
                        criteria.setRole(Role.valueOf(scanner.nextLine()));
                        break;
                    case 3:
                        criteria.setRank(Rank.valueOf(scanner.nextLine()));
                        break;
                    case 4:
                        criteria.setReadyForMission(Boolean.valueOf(scanner.nextLine()));
                        break;
                }
                if (!flag) break;
                System.out.println("Criteria:\n" + criteria);
            }
        } catch (IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void findByCriteria(CrewMemberCriteria criteria) {
        List<CrewMember> members = CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(criteria);
        if (members == null) {
            System.out.println("cannot find by this criteria");
        } else {
            System.out.println("Found: ");
            members.forEach(System.out::println);
        }
    }
}