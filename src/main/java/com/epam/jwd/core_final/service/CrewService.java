package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

import java.util.List;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface CrewService {

    List<CrewMember> findAllCrewMembers();

    List<CrewMember> findAllCrewMembersByCriteria(CrewMemberCriteria criteria);

    Optional<CrewMember> findCrewMemberByCriteria(CrewMemberCriteria criteria);

    CrewMember updateCrewMemberDetails(Role role, String name, Rank rank);

    // todo create custom exception for case, when crewMember is not able to be assigned
    void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException;

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // crewmember unique criteria - only name!
    CrewMember createCrewMember(Role role, String name, Rank rank) throws RuntimeException;

    List<? super CrewMember> createAllCrewMember(List<List<String>> parameters);
}