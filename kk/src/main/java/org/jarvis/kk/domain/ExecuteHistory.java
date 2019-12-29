package org.jarvis.kk.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * ExecuteHistory
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "tbl_ExecuteHistory")
public class ExecuteHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Member member;
}