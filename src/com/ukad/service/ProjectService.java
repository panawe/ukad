package com.ukad.service;

import java.util.List;

import com.ukad.model.Event;
import com.ukad.model.Project;

public interface ProjectService  extends BaseService {

	List<Project> loadAllProjects(Class<Project> class1);


}
