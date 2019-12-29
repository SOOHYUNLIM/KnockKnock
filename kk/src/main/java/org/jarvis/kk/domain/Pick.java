package org.jarvis.kk.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;
import org.jarvis.kk.dto.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pick
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_pick")
public class Pick extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pno;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name="currentPrice"))
    private Product product;
y
    private Integer wantedPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Boolean state;

    @Column(columnDefinition = "BOOLEAN default 1")
    private Boolean receipt;

    @Builder
    public Pick(Member member, Product product, Integer wantedPrice){
        this.member = member;
        this.product = product;
        this.wantedPrice = wantedPrice;
    }
}