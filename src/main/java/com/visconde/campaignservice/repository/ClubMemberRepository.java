package com.visconde.campaignservice.repository;

import com.visconde.campaignservice.entities.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    Optional<ClubMember> findByClubMemberId(Long clubMemberId);

}
