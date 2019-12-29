package org.jarvis.kk.repositories;

import org.jarvis.kk.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TokenRepository
 */
public interface TokenRepository extends JpaRepository<Token, String>{

    
}