package org.jarvis.kk.dto;

import java.util.Map;

import org.jarvis.kk.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2Attributes
 */
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Slf4j
public class OAuth2Attributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String mid;
    private String sex;
    private String ageGroup;

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        log.info(registrationId+"/"+userNameAttributeName);
        attributes.entrySet().forEach(x->log.info(x.getKey()+"/"+x.getValue()));
        OAuth2Attributes result = null;
        if(registrationId.equals("naver"))  result = ofNaver(userNameAttributeName, attributes);
        else if(registrationId.equals("google")) result = ofGoogle(userNameAttributeName, attributes);
        else if(registrationId.equals("kakao")) result = ofKakao(userNameAttributeName, attributes);
        else if(registrationId.equals("facebook")) result = ofFacebook(userNameAttributeName, attributes);
        return result;
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
            .mid((String) attributes.get("email"))
            .sex((String) attributes.get("email"))
            .ageGroup((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuth2Attributes ofFacebook(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
            .mid((String) attributes.get("email"))
            .sex((String) attributes.get("email"))
            .ageGroup((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuth2Attributes.builder()
            .mid((String) response.get("email"))
            .sex((String) response.get("gender"))
            .ageGroup((String) response.get("age"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
            .mid((String) attributes.get("kaccount_email"))
            .sex((String) attributes.get("kaccount_gender"))
            .ageGroup((String) attributes.get("kaccount_age_range"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    public Member toEntity(){
        return Member.builder().mid(mid)
        // .sex(sex).ageGroup(ageGroup)
        .role(Role.MEMBER).build();
    }
    
}