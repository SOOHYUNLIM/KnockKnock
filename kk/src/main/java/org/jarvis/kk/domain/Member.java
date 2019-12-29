package org.jarvis.kk.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jarvis.kk.dto.BaseTimeEntity;
import org.jarvis.kk.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Member
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_member")
public class Member extends BaseTimeEntity {

    @Id
    private String mid;

    private String sex;

    private String ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private List<Token> tokens;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_Interest", joinColumns = @JoinColumn(name="mid"))
    private List<Interest> interests;

    public Member update(String sex, String ageGroup){
        this.sex = sex;
        this.ageGroup = ageGroup;

        return this;
    }

    public Member setInterestList(String[] interests) {
        List<Interest> list = new ArrayList<>();
        Arrays.stream(interests).forEach(interest->list.add(new Interest(interest)));
        this.interests = list;
        return this;
    }

    public Member setTest(List<Interest> interests) {
        this.interests = interests;
        return this;
    }
}