package org.jarvis.kk.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Product
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@Embeddable
public class Product {

    private String category, title;
    
    @Column(length = 1000)
    private String image, link;

    private Integer price, fee;
}