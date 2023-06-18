package ua.lviv.iot.algo.part1.coursova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ua.lviv.iot.algo.part1.coursova.model.Courier;
import ua.lviv.iot.algo.part1.coursova.service.CourierService;
import ua.lviv.iot.algo.part1.coursova.service.DepartmentService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class CourierController {

    @Autowired
    private CourierService courierService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/couriers")
    public List<Courier> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    @GetMapping("/couriers/{courierId}")
    public Courier getCourierById(@PathVariable final Integer courierId) {
        return courierService.getCourierById(courierId);
    }

    @PostMapping("/departments/{departmentId}/couriers")
    public void addCourier(@PathVariable final Integer departmentId,
                           @RequestBody @Valid final Courier courier)
                            throws IOException {
        departmentService.addCourier(departmentId, courier);
    }

    @PutMapping("/departments/{departmentId}/couriers/{courierId}")
    public void updateCourier(@PathVariable final Integer departmentId,
                              @RequestBody @Valid final Courier courier,
                              @PathVariable final Integer courierId)
            throws IOException {
        departmentService.updateCourier(departmentId, courier, courierId);
    }

    @DeleteMapping("/departments/{departmentId}/couriers/{courierId}")
    public void deleteCourier(@PathVariable final Integer departmentId,
                              @PathVariable final Integer courierId)
            throws IOException {
        departmentService.deleteCourier(departmentId, courierId);
    }

    @GetMapping("/departments/{departmentId}/couriers")
    public List<Courier> getAllCouriersDepartment(
            @PathVariable final Integer departmentId) {
        return departmentService.getAllCouriers(departmentId);
    }

    @GetMapping("/departments/{departmentId}/couriers/{courierId}")
    public Courier getCourierByIdDepartment(
            @PathVariable final Integer departmentId,
            @PathVariable final Integer courierId) {
        return departmentService.getCourierById(departmentId, courierId);
    }
}
