package org.jarvis.kk.repositories;

import java.util.Optional;

import org.jarvis.kk.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * MemberRepository
 */
public interface MemberRepository extends JpaRepository<Member, String> {


    @Query("select m from Member m left join m.interests i where m.mid = :mid")
    public Optional<Member> findByMIdToInterest(String mid);
}