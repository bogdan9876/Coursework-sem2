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
import ua.lviv.iot.algo.part1.coursova.model.Parcel;
import ua.lviv.iot.algo.part1.coursova.service.CourierService;
import ua.lviv.iot.algo.part1.coursova.service.DepartmentService;
import ua.lviv.iot.algo.part1.coursova.service.ParcelService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class ParcelController {

    @Autowired
    private ParcelService parcelService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/parcels")
    public List<Parcel> getAllParcels() {
        return parcelService.getAllParcels();
    }

    @GetMapping("/parcels/{parcelId}")
    public ResponseEntity<Parcel> getParcelById(@PathVariable
                                                final Integer parcelId) {
        Parcel parcel = parcelService.getParcelById(parcelId);
        if (parcel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parcel);
    }

    @GetMapping("/departments/{departmentId}/parcels")
    public List<Parcel> getAllParcelsDepartment(
            @PathVariable final Integer departmentId) {
        return departmentService.getAllParcels(departmentId);
    }

    @GetMapping("/departments/{departmentId}/parcels/{parcelId}")
    public Parcel getParcelByIdDepartment(@PathVariable
                                          final Integer departmentId,
                                          @PathVariable
                                          final Integer parcelId) {
        return departmentService.getParcelById(departmentId, parcelId);
    }

    @PostMapping("/departments/{departmentId}/parcels")
    public void addParcel(@PathVariable final Integer departmentId,
                          @RequestBody
                          @Valid final Parcel parcel) throws IOException {
        departmentService.addParcel(departmentId, parcel);
    }

    @PutMapping("/departments/{departmentId}/parcels/{parcelId}")
    public void updateParcel(@PathVariable final Integer parcelId,
                             @RequestBody @Valid final Parcel parcel)
            throws IOException {
        parcelService.updateParcel(parcel, parcelId);
    }

    @DeleteMapping("/departments/{departmentId}/parcels/{parcelId}")
    public void deleteParcel(@PathVariable final Integer departmentId,
                             @PathVariable final Integer parcelId)
                             throws IOException {
        departmentService.deleteParcel(departmentId, parcelId);
    }

    @RequestMapping("/departments/{departmentId}/couriers/{courierId}"
            + "/parcels/{parcelId}")
    public void giveParcelToCourier(@PathVariable final Integer departmentId,
                                    @PathVariable final Integer courierId,
                                    @PathVariable
                                    final Integer parcelId)
            throws IOException {
        departmentService.giveParcelToCourier(departmentId,
                courierId, parcelId);
    }

    @RequestMapping("/departments/{departmentIdFrom}/deliver/"
            + "{departmentIdTo}/parcels/{parcelId}")
    public void deliverParcelDepartment(@PathVariable
                                        final Integer departmentIdFrom,
                                        @PathVariable
                                        final Integer departmentIdTo,
                                        @PathVariable
                                        final Integer parcelId)
                                        throws IOException {
        departmentService.deliverParcel(departmentIdFrom,
                departmentIdTo, parcelId);
    }

    @RequestMapping("/couriers/{courierId}/deliver/{parcelId}")
    public void deliverParcelCourier(@PathVariable
                                     final Integer courierId,
                                     @PathVariable
                                     final Integer parcelId)
                                     throws IOException {
        courierService.deliverParcel(courierId, parcelId);
    }
}
