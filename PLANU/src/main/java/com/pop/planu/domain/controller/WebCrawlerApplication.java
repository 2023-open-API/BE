package com.pop.planu.domain.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebCrawlerApplication.class, args);
    }

    public static void crawlWebsite() {
        // 웹 사이트 URL 및 로그인 정보 설정
        String websiteUrl  = "https://dcs-lcms.cnu.ac.kr/";
        String username = "SSO_USER=201902679";
        String password = "SSO_PASS=201902679123!";

//        https://cnu.utime.kr/wp/wp-login.php?action=wp-saml-auth&EID_SP=cnu-panopto&SSO_USER=201902679&SSO_PASS=201902679123!

        // HTTP 클라이언트 생성
        RestTemplate restTemplate = new RestTemplate();

        // 로그인 요청을 위한 파라미터 설정
        String loginUrl = websiteUrl + "/user/login";
        String data = loginUrl+"&"+username+"&"+password;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(data, headers);

        try {
            // 로그인 요청
            ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // 로그인 성공
                String sessionCookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
                HttpHeaders headersWithCookie = new HttpHeaders();
                headersWithCookie.add(HttpHeaders.COOKIE, sessionCookie);
                System.out.println(sessionCookie);
//
//                // 크롤링할 페이지 요청
//                String targetUrl = websiteUrl + "/target-page";
//                HttpEntity<String> targetRequest = new HttpEntity<>(headersWithCookie);
//                ResponseEntity<String> targetResponse = restTemplate.exchange(targetUrl, HttpMethod.GET, targetRequest, String.class);
//
//                if (targetResponse.getStatusCode() == HttpStatus.OK) {
//                    // 크롤링한 데이터 파싱
//                    String html = targetResponse.getBody();
//                    Document document = Jsoup.parse(html);
//                    // 데이터 추출 및 처리
//                    // ...
//
//                } else {
//                    System.out.println("Failed to crawl the target page.");
//                }
            } else {
                System.out.println("Login failed.");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
}
