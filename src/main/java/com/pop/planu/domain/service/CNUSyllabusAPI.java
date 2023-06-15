package com.pop.planu.domain.service;

import com.pop.planu.domain.service.dto.CourseProcess.CourseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CNUSyllabusAPI {

    @Value("${cnu.key}")
    String AUTH_KEY;

    private final String url = "https://api.cnu.ac.kr/svc/offcam/pub/lsnSmry";

    private List<CourseDto> courseDtos;

    public List<CourseDto> getCourseList(Long year, Long semester) throws ParseException, UnsupportedEncodingException {
        this.courseDtos = new ArrayList<>();
        getPageResult(year, semester, 1);
        return courseDtos;
    }

    private void getPageResult(Long year, Long semester, int page) throws ParseException, UnsupportedEncodingException {
        String response = response(year, semester, page);
        JSONParser parser = new JSONParser();
        Object jsonObject = parser.parse(response);
        JSONObject jsonResponse = (JSONObject) jsonObject;
        JSONArray data = (JSONArray) jsonResponse.get("RESULT");
        int fullPage = 1;
        for(Object object: data) {
            JSONObject course = (JSONObject) object;
            fullPage = Integer.parseInt(course.get("PAGECNT").toString());
            CourseDto dto = CourseDto.builder()
                    .grade(course.get("TRGT_SHYR").toString())
                    .name(course.get("OPEN_SBJT_NM").toString())
                    .code(course.get("OPEN_SBJT_NO").toString())
                    .room(course.get("OPEN_DCLSS").toString())
                    .credit(Double.parseDouble(course.get("PNT").toString()))
                    .courseTime(course.get("TMTBL_INFO").toString())
                    .department(course.get("DEGR_NM_SUST").toString())
                    .professor(course.get("PROF_INFO").toString())
                    .build();
            courseDtos.add(dto);
        }
        if(fullPage == page) {
            return;
        }
        getPageResult(year, semester, page+1);
    }

    private String response(Long year, Long semester, int page) throws UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        httpHeaders.add("AUTH_KEY", AUTH_KEY);
        Map<String, String> body = new HashMap<>();
        body.put("row", "100");
        body.put("P_YR", year.toString());
        body.put("P_OPEN_SHTM_CD", semester.toString());
        body.put("page", String.valueOf(page));

        HttpEntity<MultiValueMap<String, String>> requestMessage = new HttpEntity(body, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);
        return response.getBody();
    }

}
