package org.jarvis.kk.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

/**
 * Market
 */
@Entity
@Getter
@Table(name = "tbl_market")
public class Market {

    @Id
    private String mcode;

    private String urlTitle;
}