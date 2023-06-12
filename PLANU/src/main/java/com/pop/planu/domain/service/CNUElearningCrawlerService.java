package com.pop.planu.domain.service;

import com.pop.planu.domain.controller.response.schedule.ScheduleResponse;
import com.pop.planu.domain.controller.response.todo.TodoResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:/Users/MIRAE/CNU_LectureMaterial/2023/2023 CNU 공공데이터 경진대회/repos/selenium/chromedriver_win32/chromedriver.exe";

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

        base_url = "https://dcs-lcms.cnu.ac.kr/login?redirectUrl=https://dcs-learning.cnu.ac.kr/";
    }

    public List<ScheduleResponse> getTodoList(String id, String password) {
        //Driver SetUp
        driver = new ChromeDriver(options);
        List<ScheduleResponse> todoList = new ArrayList<>();
        try {
            // 이러닝 사이트 접근
            driver.get(base_url);
            Thread.sleep(4000);

            // 소속대학 선택
            driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.univ_select_box > a"))
                    .click();
            // 충남대 선택
            driver.findElement(By.cssSelector("#drawUniv_list > li:nth-child(4) > a > span.univ_name"))
                    .click();
            // 아이디 비밀번호 입력 -> 로그인
            driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.inputbox > input.id_insert"))
                    .sendKeys(id);
            driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.inputbox > input.pw_insert"))
                    .sendKeys(password);
            driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > form > div > div.inputbox > button"))
                    .click();
            Thread.sleep(5000);

            // 이러닝 입장 -> 투두 입장
            driver.findElement(By.cssSelector("#wrapper > div.main_top > div > div > div.main_loginbox > div > div.todolist > a"))
                    .click();
            Thread.sleep(5000);


            // 투두 미완료 입장
            driver.findElement(By.cssSelector("#learningTab > li:nth-child(4)"))
                    .click();
            Thread.sleep(5000);

            // 투두 목록 수집
            List<WebElement> elements = driver.findElements(By.className("todoLR"));
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
            driver.close();
            return getTodoList(id,password);
        }
        driver.close();
        return todoList;
    }
}
