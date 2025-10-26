package com.hrms.hrms.department.service;

import com.hrms.hrms.department.model.Department;
import com.hrms.hrms.department.repositary.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;
    public List<Department> getAllDepartment() {
        List<Department> list=departmentRepo.findAll();
        return list;
    }
}
