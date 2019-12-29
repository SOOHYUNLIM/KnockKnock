package org.jarvis.kk.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.jarvis.kk.domain.CommunityCrawling;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CommunotyCrawlingRepository
 */
public interface CommunityCrawlingRepository extends JpaRepository<CommunityCrawling, Integer>{

    public List<CommunityCrawling> findByRegdateBetweenOrderByNoDesc(LocalDateTime from, LocalDateTime to);
}