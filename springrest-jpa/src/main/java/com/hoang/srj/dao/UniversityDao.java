package com.hoang.srj.dao;

import com.hoang.srj.model.University;
import com.hoang.srj.view_model.UniversitySearchCriteria;

import java.util.List;

public interface UniversityDao {

    List<University> search(UniversitySearchCriteria searchCriteria);

}
