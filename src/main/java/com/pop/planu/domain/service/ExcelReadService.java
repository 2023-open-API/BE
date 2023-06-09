package com.pop.planu.domain.service;

import com.pop.planu.domain.controller.response.CourseProcess.CourseResponse;
import com.pop.planu.domain.service.dto.CourseProcess.ExcelDto;
import com.pop.planu.domain.service.dto.applicationCourse.CourseTimeDto;
import nonapi.io.github.classgraph.utils.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelReadService {

    private String basic_path = "syllabus/";
    private String extension = ".xlsx";

    private String getFilePath(Long year, Long semester) {
        return basic_path+year+"-"+semester+extension;
    }

    private String getFileName(Long year, Long semester) {
        return year+"-"+semester;
    }

    private String getExtension() {
        return this.extension;
    }

    public Map<String, ExcelDto> read(Long year, Long semester) throws IOException, URISyntaxException {
        InputStream inputStream = new ClassPathResource(getFilePath(year, semester)).getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        Map<String, ExcelDto> data = new HashMap<>();
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        for(int rowidx=0; rowidx<rows; rowidx++) {
            XSSFRow row = sheet.getRow(rowidx);
            if(row != null) {
                String code = row.getCell(0).toString();
                String room = row.getCell(1).toString();
                String time = row.getCell(2).toString();
                ExcelDto excelDto = ExcelDto.builder()
                        .courseTimeDtos(parsingCourseTime(time))
                        .professor(row.getCell(3).toString())
                        .build();
                data.put(code+"-"+room, excelDto);
            }
        }
        return data;
    }

    public List<CourseTimeDto> parsingCourseTime(String str) {
        // ex) str : "월10:00~11:00(공5410), 수15:00~14:30"
        List<CourseTimeDto> courseTimeDtos = new ArrayList<>();
        if(str.equals("")) {
            return courseTimeDtos;
        }
        String[] times = str.split(", ");
        for(int i=0; i< times.length; i++) {
            String day = times[i].substring(0,1);
            String[] date;
            String location = "";
            // 강의실 데이터 분리
            if(times[i].indexOf('(') == -1) {
                date= times[i].substring(1).split("~");
            }
            else {
                date= times[i].substring(1, times[i].indexOf('(')).split("~");
                location = times[i].substring(times[i].indexOf('(')+1, times[i].indexOf(')'));
            }
            CourseTimeDto courseTimeDto = CourseTimeDto.builder()
                    .day(day)
                    .startTime(LocalTime.parse(date[0], DateTimeFormatter.ofPattern("HH:mm")))
                    .endTime(LocalTime.parse(date[1], DateTimeFormatter.ofPattern("HH:mm")))
                    .location(location)
                    .build();
            courseTimeDtos.add(courseTimeDto);
        }
        return courseTimeDtos;
    }
}
