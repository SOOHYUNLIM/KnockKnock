package org.jarvis.kk.dto;

import java.io.Serializable;

import org.jarvis.kk.domain.Member;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * SessionMember
 */
@Getter
@Slf4j
public class SessionMember implements Serializable{

	private static final long serialVersionUID = 1L;

    private final String mid;

    // private final boolean existInterest;

    public SessionMember(Member member){
        this.mid = member.getMid();
        // log.info(member.getInterests().size()+"");
        // member.getInterests().forEach(action->log.info(action.getKeyword()));
        // this.existInterest = member.getInterests() == null ? false : true;
    }
}