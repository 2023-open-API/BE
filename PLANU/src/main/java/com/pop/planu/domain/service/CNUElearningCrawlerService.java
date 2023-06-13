package com.pop.planu.domain.service;

import com.pop.planu.domain.controller.response.schedule.ScheduleResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CNUElearningCrawlerService {

    //WebDriver
    private WebDriver driver;

    //Chrome Options;
    private ChromeOptions options;

    //Properties
    private final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private String WEB_DRIVER_PATH = "chromedriver";

    //크롤링 할 URL
    private String base_url;

    public CNUElearningCrawlerService() {
//        super();
        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--single-process");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--remote-debugging-port=9222");

        base_url = "https://dcs-lcms.cnu.ac.kr/login?redirectUrl=https://dcs-learning.cnu.ac.kr/";
    }

    public List<ScheduleResponse> getTodoList(String id, String password) {
        //Driver SetUp
        driver = new ChromeDriver(options);
        List<ScheduleResponse> crawlingList = crawling(id, password);
        driver.close();
        return crawlingList;
    }

    private List<ScheduleResponse> crawling(String id, String password) {
        List<ScheduleResponse> todoList = new ArrayList<>();
        try {
            // 이러닝 사이트 접근
            driver.get(base_url);
            Thread.sleep(3000);

            // 소속대학, id, password element
            WebElement univElement = driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.univ_select_box > a"));
            WebElement idElement = driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.inputbox > input.id_insert"));
            WebElement passwdElement = driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.inputbox > input.pw_insert"));
            Thread.sleep(3000);

            // 충남대 선택
            univElement.click();
            WebElement cnuBtn = driver.findElement(By.cssSelector("#drawUniv_list > li:nth-child(4) > a > span.univ_name"));
            cnuBtn.click();

            // 아이디 비밀번호 입력 -> 로그인
            idElement.sendKeys(id);
            passwdElement.sendKeys(password);
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.inputbox > button")).click();

            // 이러닝 입장 -> 투두 입장
            Thread.sleep(3000);
            WebElement gotoTodoBtn = driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > div.todolist > a"));
            Thread.sleep(5000);
            gotoTodoBtn.click();

            // 투두 미완료 입장
            Thread.sleep(3000);
            WebElement uncompletedBtn = driver.findElement(By.cssSelector("#learningTab > li:nth-child(4)"));
            Thread.sleep(4000);
            uncompletedBtn.click();

            // 투두 목록 수집
            Thread.sleep(4000);
            List<WebElement> elements = driver.findElements(By.className("todoLR"));
            System.out.println("크롤링 총 과제 개수 : "+elements.size());
            for(WebElement element: elements) {
                String text = element.findElement(By.tagName("a")).getText();
                String course = text.substring(1, text.indexOf('('));
                String title = text.substring(text.indexOf(']')+1);
                String lastDay = element.findElement(By.className("text-end")).getText()
                        .split(" ")[0].substring(1);
                ScheduleResponse todo = ScheduleResponse.builder()
                        .scheduleId(0L)
                        .title("["+course+"]"+title)
                        .startDate(lastDay)
                        .endDate(lastDay)
                        .color("#ff0000")
                        .build();
                todoList.add(todo);
                System.out.println("["+course+"] "+title + " : " + lastDay);
            }
        } catch (Exception e) {
            System.out.println("크롤링 에러 : "+e.getMessage());
            return crawling(id,password);
        }
        return todoList;
    }
}
