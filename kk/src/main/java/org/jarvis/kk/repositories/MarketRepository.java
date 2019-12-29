package org.jarvis.kk.repositories;

import org.jarvis.kk.dto.Market;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MarketRepository
 */
public interface MarketRepository extends JpaRepository<Market, String> {

    
}