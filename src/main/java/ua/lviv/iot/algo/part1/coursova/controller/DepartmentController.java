package ua.lviv.iot.algo.part1.coursova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ua.lviv.iot.algo.part1.coursova.model.Department;
import ua.lviv.iot.algo.part1.coursova.service.DepartmentService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable
                                        final Integer departmentId) {
        Department department = departmentService.getDepartmentById(
                departmentId);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public void createDepartment(@RequestBody
                                 @Valid final Department department)
                                 throws IOException {
        Department createdDepartment = departmentService.
                createDepartment(department);
    }

    @PutMapping("/{departmentId}")
    public void updateDepartment(@RequestBody
                                 @Valid final Department department,
                                 @PathVariable
                                 final Integer departmentId)
                                 throws IOException {
        departmentService.updateDepartment(
                department, departmentId);
    }

    @DeleteMapping("/{departmentId}")
    public void deleteDepartment(@PathVariable
                                 final Integer departmentId)
                                 throws IOException {
        departmentService.deleteDepartment(departmentId);
    }
}
