package com.hoang.restconsume;

import com.hoang.restconsume.api.UMA;
import com.hoang.restconsume.model.Clazz;
import com.hoang.restconsume.dto.ClazzDto;
import com.hoang.restconsume.model.Course;
import com.hoang.restconsume.model.Department;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * homertruong
 */

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ApplicationContext context;

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> runUMA(restTemplate);
    }

    private void runUMA(RestTemplate restTemplate) throws JSONException {
        UMA uma = (UMA) context.getBean("uma");
        uma.run(restTemplate);
        //testNestedMappingWithDozer();
    }

    private void testNestedMappingWithDozer() {
        Department department = new Department();
        department.setId("1");
        department.setName("d1");
        department.setVersion(0);
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());

        Course course = new Course();
        course.setId("1");
        course.setName("c1");
        course.setNumberOfCredits(1);
        course.setVersion(0);
        course.setCreatedAt(new Date());
        course.setUpdatedAt(new Date());
        course.setDepartment(department);

        Clazz clazz = new Clazz();
        clazz.setId("1");
        clazz.setDuration(2);
        clazz.setRoom("A6");
        clazz.setVersion(0);
        clazz.setCreatedAt(new Date());
        clazz.setUpdatedAt(new Date());
        clazz.setStatus("NEW");
        clazz.setCourse(course);
        clazz.setCourseId(course.getId());

        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder beanMappingBuilder = new BeanMappingBuilder() {
            @Override
            protected void configure() {
//                mapping(Clazz.class, ClazzDto.class)
//                        .fields("clazzStudents", "studentDtos");        // need custom converter
            }
        };
        mapper.addMapping(beanMappingBuilder);

        ClazzDto clazzDto = new ClazzDto();
        mapper.map(clazz, clazzDto);
        logger.info(clazzDto.toString());
    }

}