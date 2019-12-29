// package org.jarvis.kk.repositories;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import lombok.extern.slf4j.Slf4j;

// /**
//  * CommunityCrawlingRepositoryTests
//  */
// @Slf4j
// @SpringBootTest
// public class CommunityCrawlingRepositoryTests {

//     @Autowired
//     private CommunityCrawlingRepository communityCrawlingRepository;

//     @Test
//     public void findByRegdateBetweenTest(){
//         LocalDateTime from = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
//         LocalDateTime to = from.plusDays(1L);
//         // log.info(LocalDate.now().plusDays(1L).toString() );
//         log.info("from=============="+from.toString());
//         log.info("to=============="+to.toString());

//         communityCrawlingRepository.findByRegdateBetweenOrderByNoDesc(from, to).forEach(action->log.info(action.getProduct().getTitle()));
//     }
    
//     @Test
//     public void findPrevCrawling(){
//         // communityCrawlingRepository.findPrevCrawling();
//     }
// }