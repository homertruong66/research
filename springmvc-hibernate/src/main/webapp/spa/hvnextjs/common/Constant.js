PAGE_SIZE = 10;
DEFAULT_ID = '-1';
SERVER_URL = "http://localhost:8080/hvnspring";

ACCESS_TOKEN = '';

LOGIN_AUTHENICATION = "/oauth/token?grant_type=password&client_id=restapp&client_secret=$2a$11$gxpnezmYfNJRYnw/EpIK5Oe08TlwZDmcmUeKkrGcSGGHXvWaxUwQ2&&username={0}&password={1}";

DOMAIN_SEARCH = SERVER_URL+"/api/domain/search";
COUNTRY_SEARCH = SERVER_URL+"/api/domain/search?access_token={0}&criteria={1}";
DOMAIN_DELETE = "/api/domain/delete?access_token={0}&id={1}";

TEACHER_DELETE = "/api/teacher/delete";
TEACHER_GET_TO_EDIT = "/api/teacher/getToEdit?access_token={0}&id={1}";
TEACHER_GET_TO_TRANSFER = "/api/teacher/getToTransfer?access_token={0}&entity={1}";
TEACHER_SAVE = "/api/teacher/save";
TEACHER_SEARCH = "/api/teacher/search";
TEACHER_TRANSFER = "/api/teacher/transfer";

UNIVERSITY_DELETE = "/api/university/delete?access_token={0}&id={1}";
UNIVERSITY_GET_TO_EDIT = SERVER_URL+"/api/university/getToEdit?access_token={0}&id={1}";
UNIVERSITY_SAVE = SERVER_URL+"/api/university/save?access_token={0}&entity={1}";
UNIVERSITY_SEARCH = SERVER_URL+"/api/university/search";

STUDENT_DELETE = "/api/student/delete?access_token={0}&id={1}";
STUDENT_GET_TO_EDIT = "/api/student/getToEdit?access_token={0}&id={1}";
STUDENT_SAVE = "/api/student/save?access_token={0}&entity={1}";
STUDENT_SEARCH = "/api/student/search?access_token={0}&criteria={1}";

RESPONSE_STATUS_SUCCESS = "success";
RESPONSE_STATUS_ERROR = "error";

PROVINE_NAME = '';
UNIVERSITY_ID = '';