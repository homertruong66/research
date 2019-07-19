package com.hoang.uma.common.config;

import com.hoang.uma.common.dto.*;
import com.hoang.uma.service.model.*;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * homertruong
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper config () {
        ModelMapper modelMapper = new ModelMapper();

        // User
        PropertyMap<UserUpdate, UserDto> userMapping = new PropertyMap<UserUpdate, UserDto>() {
            protected void configure() {
                map(source.getUserRoleUpdates(), destination.getRoles());
            }
        };
        modelMapper.addMappings(userMapping);

        // University
        PropertyMap<University, UniversityDto> universityMapping = new PropertyMap<University, UniversityDto>() {
            protected void configure() {
                map(source.getCountry().getId(), destination.getCountry().getId());
            }
        };
        modelMapper.addMappings(universityMapping);

        // Course
        PropertyMap<Course, CourseDto> courseMapping = new PropertyMap<Course, CourseDto>() {
            protected void configure() {
                map(source.getDepartment().getUniversity().getName(), destination.getUniversityName());
            }
        };
        modelMapper.addMappings(courseMapping);

        // Teacher
        PropertyMap<Teacher, TeacherDto> teacherMapping = new PropertyMap<Teacher, TeacherDto>() {
            protected void configure() {
                map(source.getDepartmentTeachers(), destination.getDepartmentDtos());
            }
        };
        modelMapper.addMappings(teacherMapping);

        // Clazz
        PropertyMap<Clazz, ClazzDto> clazzMapping = new PropertyMap<Clazz, ClazzDto>() {
            protected void configure() {
                map(source.getClazzStudents(), destination.getStudentDtos());
            }
        };
        modelMapper.addMappings(clazzMapping);

        // Converters
        Converter<DepartmentTeacher, DepartmentDto> dt2ddto = new AbstractConverter<DepartmentTeacher, DepartmentDto>() {
            protected DepartmentDto convert(DepartmentTeacher source) {
                return modelMapper.map(source.getDepartment(), DepartmentDto.class);
            }
        };
        modelMapper.addConverter(dt2ddto);

        Converter<ClazzStudent, StudentDto> cs2sdto = new AbstractConverter<ClazzStudent, StudentDto>() {
            protected StudentDto convert(ClazzStudent source) {
                return modelMapper.map(source.getStudent(), StudentDto.class);
            }
        };
        modelMapper.addConverter(cs2sdto);

        return modelMapper;
    }
}
