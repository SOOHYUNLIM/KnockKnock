package org.jarvis.kk.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SubCategory
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SubCategory {

    @Column(unique = true)
    private String title;
}