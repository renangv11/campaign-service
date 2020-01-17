package com.visconde.campaignservice.repository;

import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.model.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    Optional<ClubMember> findByClubMemberId(Long clubMemberId);

}
