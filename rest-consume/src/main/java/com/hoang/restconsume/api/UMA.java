package com.hoang.restconsume.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * homertruong
 */

@Component("uma")
public class UMA {

    private static final Logger logger = LoggerFactory.getLogger(UMA.class);

    @Value("${uma.loginUrl}")
    private String loginUrl;

    @Value("${uma.logoutUrl}")
    private String logoutUrl;

    @Value("${uma.baseUrl}")
    private String baseUrl;

    private RestTemplate restTemplate;
    private String securityTokenHeaderKey = "X-Security-Token";
    private String securityTokenHeader;

    public void run(RestTemplate restTemplate) throws JSONException {
        this.restTemplate = restTemplate;

        login();

        consumeUser();
        consumeUniversity();
        consumeDepartment();
        consumeCourse();
        consumeStudent();
        consumeTeacher();
        consumeClass();

        logout();
    }

    private void login() {
        logger.info("Login to UMA...");
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "hoang@3s");
        headers.set("password", "12345678");
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, String.class);
        securityTokenHeader = response.getHeaders().get(securityTokenHeaderKey).get(0);
        logger.info("Token: " + securityTokenHeader);
    }

    private void logout() {
        logger.info("Logout of CJA...");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(securityTokenHeaderKey, securityTokenHeader);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        restTemplate.exchange(logoutUrl, HttpMethod.POST, entity, String.class);
    }

    private void consumeUser() throws JSONException {
        logger.info("......Run User......");

        // create
        logger.info("Create a User...");
        JSONObject createModel = createViewModel("User", "create");
        String id = sendRequest(restTemplate, baseUrl + "users/", HttpMethod.POST, createModel);
        assert id != null;

        // update
        String entityUrl = baseUrl + "users/" + id;
        logger.info("Update the User...");
        JSONObject updateModel = createViewModel("User", "update");
        id = sendRequest(restTemplate, entityUrl, HttpMethod.PUT, updateModel);
        assert id != null;

        // get
        logger.info("Get the User...");
        String body = sendRequest(restTemplate, entityUrl, HttpMethod.GET, new JSONObject());
        JSONObject dto = new JSONObject(body);
        assert dto.get("name").equals(updateModel.get("name"));
        assert dto.get("age").equals(updateModel.get("age"));

        // delete
        logger.info("Delete the User...");
        sendRequest(restTemplate, entityUrl, HttpMethod.DELETE, new JSONObject());
    }

    private void consumeUniversity() throws JSONException {
        logger.info("......Run University......");

        // create
        logger.info("Create a University...");
        JSONObject createModel = createViewModel("University", "create");
        String id = sendRequest(restTemplate, baseUrl + "universities/", HttpMethod.POST, createModel);
        assert id != null;

        // update
        String entityUrl = baseUrl + "universities/" + id;
        logger.info("Update the University...");
        JSONObject updateModel = createViewModel("University", "update");
        id = sendRequest(restTemplate, entityUrl, HttpMethod.PUT, updateModel);
        assert id != null;

        // get
        logger.info("Get the University...");
        String body = sendRequest(restTemplate, entityUrl, HttpMethod.GET, new JSONObject());
        JSONObject dto = new JSONObject(body);
        assert dto.get("name").equals(updateModel.get("name"));
        assert ((JSONObject) dto.get("country")).get("id").equals(updateModel.get("countryId"));

        // delete
        logger.info("Delete the University...");
        sendRequest(restTemplate, entityUrl, HttpMethod.DELETE, new JSONObject());
    }

    private void consumeDepartment() throws JSONException {
        logger.info("......Run Department......");

        // create Uni
        logger.info("Create a University...");
        JSONObject createModel = createViewModel("University", "create");
        String universityId = sendRequest(restTemplate, baseUrl + "universities/", HttpMethod.POST, createModel);
        assert universityId != null;

        // create
        logger.info("Create a Department for the University...");
        createModel = createViewModel("Department", "create");
        String id = sendRequest(restTemplate, baseUrl + "universities/" + universityId + "/departments/", HttpMethod.POST, createModel);
        assert id != null;

        // update
        String entityUrl = baseUrl + "departments/" + id;
        logger.info("Update the Department...");
        JSONObject updateModel = createViewModel("Department", "update");
        id = sendRequest(restTemplate, entityUrl, HttpMethod.PUT, updateModel);
        assert id != null;

        // get
        logger.info("Get the Department...");
        String body = sendRequest(restTemplate, entityUrl, HttpMethod.GET, new JSONObject());
        JSONObject dto = new JSONObject(body);
        assert dto.get("name").equals(updateModel.get("name"));

        // delete
        logger.info("Delete the Department...");
        sendRequest(restTemplate, entityUrl, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the University...");
        sendRequest(restTemplate, baseUrl + "universities/" + universityId, HttpMethod.DELETE, new JSONObject());
    }

    private void consumeCourse() throws JSONException {
        logger.info("......Run Course......");

        // create Uni
        logger.info("Create a University...");
        JSONObject createModel = createViewModel("University", "create");
        String universityId = sendRequest(restTemplate, baseUrl + "universities/", HttpMethod.POST, createModel);
        assert universityId != null;

        // create Dept
        logger.info("Create a Department for the University...");
        createModel = createViewModel("Department", "create");
        String departmentId = sendRequest(restTemplate, baseUrl + "universities/" + universityId + "/departments/", HttpMethod.POST, createModel);
        assert departmentId != null;

        // create
        logger.info("Create a Course for the Department...");
        createModel = createViewModel("Course", "create");
        String id = sendRequest(restTemplate, baseUrl + "departments/" + departmentId + "/courses/", HttpMethod.POST, createModel);
        assert id != null;

        // update
        String entityUrl = baseUrl + "courses/" + id;
        logger.info("Update the Course...");
        JSONObject updateModel = createViewModel("Course", "update");
        id = sendRequest(restTemplate, entityUrl, HttpMethod.PUT, updateModel);
        assert id != null;

        // get
        logger.info("Get the Course...");
        String body = sendRequest(restTemplate, entityUrl, HttpMethod.GET, new JSONObject());
        JSONObject dto = new JSONObject(body);
        assert dto.get("name").equals(updateModel.get("name"));
        assert dto.get("num_credits").equals(updateModel.get("numberOfCredits"));

        // delete
        logger.info("Delete the Course...");
        sendRequest(restTemplate, entityUrl, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the Department...");
        sendRequest(restTemplate, baseUrl + "departments/" + departmentId, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the University...");
        sendRequest(restTemplate, baseUrl + "universities/" + universityId, HttpMethod.DELETE, new JSONObject());
    }

    private void consumeStudent() throws JSONException {
        logger.info("......Run Student......");

        // create Uni
        logger.info("Create a University...");
        JSONObject createModel = createViewModel("University", "create");
        String universityId = sendRequest(restTemplate, baseUrl + "universities/", HttpMethod.POST, createModel);
        assert universityId != null;

        // create Dept
        logger.info("Create a Department for the University...");
        createModel = createViewModel("Department", "create");
        String departmentId = sendRequest(restTemplate, baseUrl + "universities/" + universityId + "/departments/", HttpMethod.POST, createModel);
        assert departmentId != null;

        // create
        logger.info("Create a Student for the Department...");
        createModel = createViewModel("Student", "create");
        String id = sendRequest(restTemplate, baseUrl + "departments/" + departmentId + "/students/", HttpMethod.POST, createModel);
        assert id != null;

        // update
        String entityUrl = baseUrl + "students/" + id;
        logger.info("Update the Student...");
        JSONObject updateModel = createViewModel("Student", "update");
        id = sendRequest(restTemplate, entityUrl, HttpMethod.PUT, updateModel);
        assert id != null;

        // get
        logger.info("Get the Student...");
        String body = sendRequest(restTemplate, entityUrl, HttpMethod.GET, new JSONObject());
        JSONObject dto = new JSONObject(body);
        assert dto.get("name").equals(updateModel.get("name"));
        assert dto.get("age").equals(updateModel.get("age"));
        assert dto.get("year").equals(updateModel.get("year"));

        // delete
        logger.info("Delete the Student...");
        sendRequest(restTemplate, entityUrl, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the Department...");
        sendRequest(restTemplate, baseUrl + "departments/" + departmentId, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the University...");
        sendRequest(restTemplate, baseUrl + "universities/" + universityId, HttpMethod.DELETE, new JSONObject());
    }

    private void consumeTeacher() throws JSONException {
        logger.info("......Run Teacher......");

        // create
        logger.info("Create a Teacher...");
        JSONObject createModel = createViewModel("Teacher", "create");
        String id = sendRequest(restTemplate, baseUrl + "teachers/", HttpMethod.POST, createModel);
        assert id != null;

        // update
        String entityUrl = baseUrl + "teachers/" + id;
        logger.info("Update the Teacher...");
        JSONObject updateModel = createViewModel("Teacher", "update");
        id = sendRequest(restTemplate, entityUrl, HttpMethod.PUT, updateModel);
        assert id != null;

        // get
        logger.info("Get the Teacher...");
        String body = sendRequest(restTemplate, entityUrl, HttpMethod.GET, new JSONObject());
        JSONObject dto = new JSONObject(body);
        assert dto.get("name").equals(updateModel.get("name"));
        assert dto.get("age").equals(updateModel.get("age"));
        assert dto.get("degree").equals(updateModel.get("degree"));

        // delete
        logger.info("Delete the Teacher...");
        sendRequest(restTemplate, entityUrl, HttpMethod.DELETE, new JSONObject());
    }

    private void consumeClass() throws JSONException {
        logger.info("......Run Class......");

        // create Uni
        logger.info("Create a University...");
        JSONObject createModel = createViewModel("University", "create");
        String universityId = sendRequest(restTemplate, baseUrl + "universities/", HttpMethod.POST, createModel);
        assert universityId != null;

        // create Dept
        logger.info("Create a Department for the University...");
        createModel = createViewModel("Department", "create");
        String departmentId = sendRequest(restTemplate, baseUrl + "universities/" + universityId + "/departments/", HttpMethod.POST, createModel);
        assert departmentId != null;

        // create Course
        logger.info("Create a Course for the Department...");
        createModel = createViewModel("Course", "create");
        String courseId = sendRequest(restTemplate, baseUrl + "departments/" + departmentId + "/courses/", HttpMethod.POST, createModel);
        assert courseId != null;

        // create Teacher
        logger.info("Create a Teacher...");
        createModel = createViewModel("Teacher", "create");
        String teacherId = sendRequest(restTemplate, baseUrl + "teachers/", HttpMethod.POST, createModel);
        assert teacherId != null;

        // create Clazz
        logger.info("Create a Clazz for the Course...");
        createModel = createViewModel("Clazz", "create");
        createModel.put("teacherId", teacherId);
        String id = sendRequest(restTemplate, baseUrl + "courses/" + courseId + "/clazzes/", HttpMethod.POST, createModel);
        assert id != null;

        // update
        String entityUrl = baseUrl + "clazzes/" + id;
        logger.info("Update the Clazz...");
        JSONObject updateModel = createViewModel("Clazz", "update");
        updateModel.put("teacherId", teacherId);
        id = sendRequest(restTemplate, entityUrl, HttpMethod.PUT, updateModel);
        assert id != null;

        // get
        logger.info("Get the Clazz...");
        String body = sendRequest(restTemplate, entityUrl, HttpMethod.GET, new JSONObject());
        JSONObject dto = new JSONObject(body);
        assert dto.get("duration").equals(updateModel.get("duration"));
        assert dto.get("room").equals(updateModel.get("room"));
//        assert dto.get("started_at").equals(updateModel.get("startedAt"));
//        assert dto.get("ended_at").equals(updateModel.get("endedAt"));
        assert ((JSONObject) dto.get("teacher")).get("id").equals(updateModel.get("teacherId"));

        // start
        logger.info("Start the Clazz...");
        id = sendRequest(restTemplate, entityUrl + "/start", HttpMethod.POST, new JSONObject());
        assert id != null;

        // end
        logger.info("End the Clazz...");
        id = sendRequest(restTemplate, entityUrl + "/end", HttpMethod.POST, new JSONObject());
        assert id != null;

        // delete
        logger.info("Delete the Clazz...");
        sendRequest(restTemplate, entityUrl, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the Teacher...");
        sendRequest(restTemplate, baseUrl + "teachers/" + teacherId, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the Course...");
        sendRequest(restTemplate, baseUrl + "courses/" + courseId, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the Department...");
        sendRequest(restTemplate, baseUrl + "departments/" + departmentId, HttpMethod.DELETE, new JSONObject());

        logger.info("Delete the University...");
        sendRequest(restTemplate, baseUrl + "universities/" + universityId, HttpMethod.DELETE, new JSONObject());
    }

    private JSONObject createViewModel(String entityName, String modelType) throws JSONException {
        JSONObject viewModel = new JSONObject();

        switch (entityName) {
            case "User":
                if (modelType.equals("create")) {
                    viewModel.put("email", "consumeUser@3s");
                    viewModel.put("confirmedEmail", "consumeUser@3s");
                    viewModel.put("name", "User");
                    viewModel.put("password", "12345678");
                    viewModel.put("confirmedPassword", "12345678");
                    viewModel.put("age", 66);
                    List<String> roles = new ArrayList<>();
                    roles.add("ROLE_ADMIN");
                    roles.add("ROLE_USER");
                    viewModel.put("roles", roles);
                } else if (modelType.equals("update")) {
                    viewModel.put("name", "User Updated");
                    viewModel.put("age", 99);
                    List<String> updateRoles = new ArrayList<>();
                    updateRoles.add("ROLE_ADMIN");
                    viewModel.put("roles", updateRoles);
                }
                break;
            case "University":
                if (modelType.equals("create")) {
                    viewModel.put("name", "University");
                    viewModel.put("countryId", "1");
                } else if (modelType.equals("update")) {
                    viewModel.put("name", "University Updated");
                    viewModel.put("countryId", "2");
                }
                break;
            case "Department":
                if (modelType.equals("create")) {
                    viewModel.put("name", "Department");
                } else if (modelType.equals("update")) {
                    viewModel.put("name", "Department Updated");
                }
                break;
            case "Course":
                if (modelType.equals("create")) {
                    viewModel.put("name", "Course");
                    viewModel.put("numberOfCredits", 2);
                } else if (modelType.equals("update")) {
                    viewModel.put("name", "Course Updated");
                    viewModel.put("numberOfCredits", 3);
                }
                break;
            case "Student":
                if (modelType.equals("create")) {
                    viewModel.put("email", "consumeStudent@test.com");
                    viewModel.put("confirmedEmail", "consumeStudent@test.com");
                    viewModel.put("name", "Student");
                    viewModel.put("password", "12345678");
                    viewModel.put("confirmedPassword", "12345678");
                    viewModel.put("age", 66);
                    viewModel.put("year", 1);
                    List<String> roles = new ArrayList<>();
                    roles.add("ROLE_STUDENT");
                    viewModel.put("roles", roles);
                } else if (modelType.equals("update")) {
                    viewModel.put("name", "Student Updated");
                    viewModel.put("age", 99);
                    viewModel.put("year", 2);
                }
                break;
            case "Teacher":
                if (modelType.equals("create")) {
                    viewModel.put("email", "consumeTeacher@test.com");
                    viewModel.put("confirmedEmail", "consumeTeacher@test.com");
                    viewModel.put("name", "Teacher");
                    viewModel.put("password", "12345678");
                    viewModel.put("confirmedPassword", "12345678");
                    viewModel.put("age", 66);
                    viewModel.put("degree", "Bachelor");
                    List<String> roles = new ArrayList<>();
                    roles.add("ROLE_TEACHER");
                    viewModel.put("roles", roles);
                } else if (modelType.equals("update")) {
                    viewModel.put("name", "Teacher Updated");
                    viewModel.put("age", 99);
                    viewModel.put("degree", "Master");
                }
                break;
            case "Clazz":
                if (modelType.equals("create")) {
                    viewModel.put("duration", 2);
                    viewModel.put("room", "A66");
                    viewModel.put("startedAt", "2017-01-01");
                    viewModel.put("endedAt", "2017-06-06");
                } else if (modelType.equals("update")) {
                    viewModel.put("duration", 3);
                    viewModel.put("room", "A69");
                    viewModel.put("startedAt", "2017-02-02");
                    viewModel.put("endedAt", "2017-05-05");
                }
                break;
        }

        return viewModel;
    }

    private String sendRequest(RestTemplate restTemplate, String url, HttpMethod method, JSONObject viewModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(securityTokenHeaderKey, securityTokenHeader);
        HttpEntity<String> entity = new HttpEntity<>(viewModel.toString(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, entity, String.class);
        return responseEntity.getBody();
    }
}
