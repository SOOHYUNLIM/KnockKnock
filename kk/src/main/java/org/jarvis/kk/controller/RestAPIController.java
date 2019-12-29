package org.jarvis.kk.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.jarvis.kk.domain.ClickHistory;
import org.jarvis.kk.domain.CommunityCrawling;
import org.jarvis.kk.domain.ExecuteHistory;
import org.jarvis.kk.domain.Member;
import org.jarvis.kk.domain.Pick;
import org.jarvis.kk.domain.Token;
import org.jarvis.kk.dto.Category;
import org.jarvis.kk.dto.SessionMember;
import org.jarvis.kk.repositories.CategoryDTORepository;
import org.jarvis.kk.repositories.ClickHistoryRepository;
import org.jarvis.kk.repositories.CommunityCrawlingRepository;
import org.jarvis.kk.repositories.ExecuteHistoryRepository;
import org.jarvis.kk.repositories.MarketRepository;
import org.jarvis.kk.repositories.MemberRepository;
import org.jarvis.kk.repositories.TokenRepository;
import org.jarvis.kk.service.FCMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RestController
 */
@CrossOrigin
@RestController
@RequestMapping("/kk")
@RequiredArgsConstructor
@Slf4j
public class RestAPIController {

    private final MemberRepository memberRepository;

    private final TokenRepository tokenRepository;

    private final CommunityCrawlingRepository communityCrawlingRepository;

    private final ClickHistoryRepository clickHistoryRepository;

    private final ExecuteHistoryRepository executeHistoryRepository;

    private final CategoryDTORepository categoryDTORepository;
    private final MarketRepository marketRepository;

    private final FCMService fcmService;

    private final HttpSession session;

    private List<Category> categories;

    private Set<String> markets;

    @PostConstruct
    public void init() {
        this.categories = categoryDTORepository.findAll();
        this.markets = marketRepository.findAll().stream().map(market -> market.getUrlTitle())
                .collect(Collectors.toSet());
    }

    private Object callCheckingLambda(String url) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        Object response = template.getForObject(
                "https://773hnhyh4j.execute-api.ap-northeast-2.amazonaws.com/Knock-Knock/check/" + url, Object.class);
        return response;
    }

    @GetMapping("/logout")
    public void logout() {
        // 토큰 레파지토리에서 where delete
        log.info("=============================");
    }

    @PostMapping("/pick")
    public Integer pick(@RequestBody Pick pick){
        log.info(pick.getWantedPrice()+"");
        return null;
    }

    @GetMapping("/navershopping")
    public ResponseEntity<Object> checkNaver(@RequestParam String title) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        Object response = template.getForObject(
                "https://773hnhyh4j.execute-api.ap-northeast-2.amazonaws.com/Knock-Knock/navershopping/" + title,
                Object.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<Object> checkUrl(@RequestParam String url) {
        String decodeUrl = null;
        try {
            decodeUrl = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return markets.contains(decodeUrl.split("\\.")[1])
                ? new ResponseEntity<>(callCheckingLambda(url), HttpStatus.OK)
                : new ResponseEntity<>("FAIL", HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping("/updateInterest")
    public Integer updateInterest(@RequestBody String[] interests) {
        SessionMember member = (SessionMember) session.getAttribute("member");
        Member realMember = memberRepository.getOne(member.getMid());
        memberRepository.save(realMember.setInterestList(interests));
        return HttpStatus.OK.value();
    }

    @GetMapping("/interestChecking")
    public Integer interestChecking() {
        SessionMember member = (SessionMember) session.getAttribute("member");
        // log.info("=============================");
        // log.info(member.isExistInterest()+"");
        // return member.isExistInterest() ? HttpStatus.OK.value() :
        // HttpStatus.MOVED_PERMANENTLY.value();
        return 200;
    }

    @PostMapping("/token")
    public ResponseEntity<String> registerToken(@RequestBody String token) {
        SessionMember member = (SessionMember) session.getAttribute("member");
        fcmService.addAllTopics(token);
        tokenRepository.save(Token.builder().token(token).mid(member.getMid()).build());
        executeHistoryRepository.save(new ExecuteHistory(memberRepository.getOne(member.getMid())));
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CommunityCrawling>> getList() {
        SessionMember member = (SessionMember) session.getAttribute("member");
        Member realMember = memberRepository.getOne(member.getMid());
        List<String> totalInterest = new ArrayList<>();
        List<String> analysisData = clickHistoryRepository.groupByCategoryCount(realMember);
        realMember.getInterests().forEach(interest -> {
            String category = interest.getKeyword();
            totalInterest.add(category);
            analysisData.remove(category);
        });

        totalInterest.addAll(analysisData);

        LocalDateTime from = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        LocalDateTime to = from.plusDays(1L);
        List<CommunityCrawling> result = new ArrayList<>();
        Map<String, List<CommunityCrawling>> codeToData = new HashMap<>();
        this.categories.forEach(category -> codeToData.put(category.getCode(), new ArrayList<>()));
        Set<String> totalCode = codeToData.keySet();

        communityCrawlingRepository.findByRegdateBetweenOrderByNoDesc(from, to).forEach(data -> {
            if (data.isLastCrawling() && codeToData.values().stream().mapToInt(list -> list.size()).sum() > 20)
                return;
            codeToData.get(data.getProduct().getCategory()).add(data);
        });

        totalInterest.forEach(code -> {
            result.addAll(codeToData.get(code));
            totalCode.remove(code);
        });
        totalCode.forEach(code -> result.addAll(codeToData.get(code)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/click")
    public void pileUpClickHistory(@RequestParam("no") Integer communityCrawlingNo) {
        SessionMember member = (SessionMember) session.getAttribute("member");

        clickHistoryRepository.save(ClickHistory.builder().member(memberRepository.getOne(member.getMid()))
                .communityCrawling(communityCrawlingRepository.getOne(communityCrawlingNo)).build());
    }

    @GetMapping("/msg")
    public void pushAllFcm() {
        fcmService.pushAllFcm();
    }
}