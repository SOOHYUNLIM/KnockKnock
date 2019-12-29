package org.jarvis.kk.dto;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Category
 */
@Getter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    private String code;

    private String keyword;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_subCategory", joinColumns = @JoinColumn(name="code"))
    private List<SubCategory> subCategorys;
}