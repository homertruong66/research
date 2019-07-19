package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.TeacherCreateModel;
import com.hoang.uma.common.view_model.TeacherSearchCriteria;
import com.hoang.uma.common.view_model.TeacherUpdateModel;
import com.hoang.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * homertruong
 */

public class TeacherController {

    @Autowired
    protected UserService userService;

    public ResponseDto create (@Valid TeacherCreateModel createModel) throws BusinessException
    {
        return new ResponseDto(userService.createTeacher(createModel));
    }

    public ResponseDto assign (String id, List<String> departmentIds) throws BusinessException {
        return new ResponseDto(userService.assignTeacher(id, departmentIds));
    }

    public void delete (String id) throws BusinessException {
        userService.deleteTeacher(id);
    }

    public ResponseDto get (String id) throws BusinessException {
        return new ResponseDto(userService.getTeacher(id));
    }

    public ResponseDto search (TeacherSearchCriteria searchCriteria) throws BusinessException {
        return new ResponseDto(userService.searchTeachers(searchCriteria));
    }

    public ResponseDto update (String teacherId, @Valid TeacherUpdateModel updateModel) throws BusinessException {
        return new ResponseDto(userService.updateTeacher(teacherId, updateModel));
    }

    public String upload (@RequestParam("file") MultipartFile uploadedFile) {
        try {
            List<TeacherCreateModel> createModels = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uploadedFile.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                TeacherCreateModel createModel = new TeacherCreateModel();
                String[] fields = line.split(",");
                createModel.setEmail(fields[0]);
                createModel.setName(fields[1]);
                createModel.setPassword(fields[2]);
                createModel.setAge(Integer.parseInt(fields[3]));
                createModel.setDegree(fields[4]);
                createModels.add(createModel);
            }
            userService.createTeachers(createModels);
        }
        catch (Exception ex) {
            return "error processing file: " + ex.getMessage();
        }

        return "ok";
    }

}
