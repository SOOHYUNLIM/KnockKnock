// package org.jarvis.kk.repositories;

// import java.util.Optional;

// import javax.transaction.Transactional;

// import org.jarvis.kk.domain.Member;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.annotation.Commit;

// import lombok.extern.slf4j.Slf4j;

// // import lombok.extern.slf4j.Slf4j;

// /**
//  * MemberRepositoryTests
//  */
// // @Slf4j
// @Slf4j
// @SpringBootTest
// public class MemberRepositoryTests {

//     @Autowired
//     private MemberRepository memberRepository;

//     @Commit
//     @Transactional
//     @Test
//     public void findByMidToInterest(){
//         // Member member = memberRepository.findById("tjsvndrlskfk@gmail.com").get();
//         // Member member = memberRepository.getOne("tjsvndrlskfk@gmail.com");
//         // List<Interest> list = new ArrayList<>();
//         // list.add(Interest.builder().keyword("C1").build());
//         // list.add(Interest.builder().keyword("C2").build());
//         // list.add(Interest.builder().keyword("C3").build());
//         // memberRepository.save(member.setInterests(list));
//         // log.info(member.getInterests().size()+"");
//         // member.getInterests().forEach(i->log.info(i.getKeyword()));
//         // memberRepository.getOne("tjsvndrlskfk@gmail.com");
//         // memberRepository.findById("tjsvndrlskfk@gmail.com").get().getInterests().toString();
        
//         Optional<Member> result = memberRepository.findByMIdToInterest("tjsvndrlskfk@gmail.com");
        
//         log.info("=============================");
//         log.info(result.get().getInterests().size()+"");
//     }
    
// }