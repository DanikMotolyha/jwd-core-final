package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.ServiceException.CannotBeAssignedToMissionException;
import com.epam.jwd.core_final.exception.ServiceException.CannotUpdateException;
import com.epam.jwd.core_final.exception.ServiceException.DuplicateObjectException;
import com.epam.jwd.core_final.exception.ServiceException.ServiceException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.CrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {
    private final static CrewService INSTANCE = new CrewServiceImpl();
    private final static Logger LOGGER = LoggerFactory.getLogger(SpaceshipFactory.class);

    private CrewServiceImpl() {

    }

    public static CrewService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>) NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(CrewMemberCriteria criteria) {
        Collection<CrewMember> stream = new ArrayList<>(NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class));
        if (criteria.getId() != null) {
            stream = stream.stream().filter(item ->
                    item.getId().equals(criteria.getId()))
                    .collect(Collectors.toList());
        }
        if (criteria.getObjectName() != null) {
            stream = stream.stream().filter(item ->
                    item.getName().equals(criteria.getObjectName()))
                    .collect(Collectors.toList());
        }
        if (criteria.getName() != null) {
            stream = stream.stream().filter(item ->
                    item.getMemberName().equals(criteria.getName()))
                    .collect(Collectors.toList());
        }
        if (criteria.getRank() != null) {
            stream = stream.stream().filter(item ->
                    item.getRank() == criteria.getRank())
                    .collect(Collectors.toList());
        }
        if (criteria.getRole() != null) {
            stream = stream.stream().filter(item ->
                    item.getRole() == criteria.getRole())
                    .collect(Collectors.toList());
        }
        if (criteria.getReadyForMission() != null) {
            stream = stream.stream().filter(item ->
                    item.getReadyForNextMissions() == criteria.getReadyForMission())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(stream);
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(CrewMemberCriteria criteria) {
        return findAllCrewMembersByCriteria(criteria).stream().findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(Role role, String name, Rank rank) {
        Optional<CrewMember> member = findCrewMemberByCriteria(
                CrewMemberCriteria
                        .builder()
                        .setName(name)
                        .build());
        if (member.isEmpty())
            throw new CannotUpdateException("Cannot find member with name", name);
        CrewMember updatedMember = member.get();
        if (role != null) {
            updatedMember.setRole(role);
        }
        if (rank != null) {
            updatedMember.setRank(rank);
        }
        return updatedMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {
        if (!crewMember.getReadyForNextMissions())
            throw new CannotBeAssignedToMissionException("crew member was assigned already", crewMember);
        crewMember.setReadyForNextMissions(false);
    }

    @Override
    public CrewMember createCrewMember(Role role, String name, Rank rank) throws RuntimeException {
        if (findCrewMemberByCriteria(CrewMemberCriteria.builder().setName(name).build()).isPresent())
            throw new ServiceException("Cannot create new object: duplicate name");
        CrewMember member = CrewMemberFactory.getInstance().create(
                Arrays.asList(role.getId().toString(), name, rank.getId().toString())
        );
        NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class).add(member);
        return member;
    }

    @Override
    public List<? super CrewMember> createAllCrewMember(List<List<String>> parameters) {
        for (List<String> params : parameters) {
            try {
                Optional<CrewMember> crewMember = Optional.ofNullable(CrewMemberFactory.getInstance().create(params));
                if (crewMember.isPresent()) {
                    if (findCrewMemberByCriteria(
                            CrewMemberCriteria
                                    .builder()
                                    .setName(crewMember
                                            .get()
                                            .getMemberName())
                                    .build()
                    ).isPresent()) {
                        throw new DuplicateObjectException("Duplicate :", crewMember.get());
                    }
                    NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class).add(crewMember.get());
                }
            } catch (DuplicateObjectException ex) {
                LOGGER.error(ex.getMessage());
            }

        }
        return (List<? super CrewMember>) NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);
    }
}
