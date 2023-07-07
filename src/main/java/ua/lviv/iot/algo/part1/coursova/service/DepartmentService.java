package ua.lviv.iot.algo.part1.coursova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.coursova.datastorage.DepartmentDataRepository;
import ua.lviv.iot.algo.part1.coursova.model.Courier;
import ua.lviv.iot.algo.part1.coursova.model.Department;
import ua.lviv.iot.algo.part1.coursova.model.Parcel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private DepartmentDataRepository departmentDataRepository;

    private HashMap<Integer, Department> departments = new HashMap<>();

    public List<Department> getAllDepartments() {
        return new LinkedList<>(departments.values());
    }

    public Department getDepartmentById(final Integer departmentId) {
        return departments.get(departmentId);
    }

    public Department createDepartment(final Department department)
            throws IOException {
        Integer departmentId = getNextDepartmentId();
        department.setDepartmentId(departmentId);
        department.setParcelIds(new ArrayList<>());
        departments.put(departmentId, department);
        saveDepartments();
        return department;
    }

    private Integer getNextDepartmentId() {
        Integer maxId = departments.keySet().stream().
                max(Integer::compareTo).orElse(0);
        return maxId + 1;
    }

    public void updateDepartment(final Department department,
                                 final Integer departmentId)
            throws IOException {
        department.setDepartmentId(departmentId);
        departments.replace(departmentId, department);
        saveDepartments();
    }

    public void deleteDepartment(final Integer departmentId)
            throws IOException {
        for (Integer parcelId : departments.get(departmentId).getParcelIds()) {
            parcelService.deleteParcel(parcelId);
        }
        for (Courier courier : courierService.getAllCouriers()) {
            if (Objects.equals(courier.getDepartmentId(), departmentId)) {
                courierService.deleteCourier(courier.getCourierId());
            }
        }
        departments.remove(departmentId);
        saveDepartments();
    }

    public void giveParcelToCourier(final Integer departmentId,
                                    final Integer courierId,
                                    final Integer parcelId) throws IOException {
        if (departments.get(departmentId).getParcelIds().contains(parcelId)
                && Objects.equals(courierService.
                getCourierById(courierId).getDepartmentId(), departmentId)) {
            List<Integer> newIdsDpt = departments.get(departmentId).
                    getParcelIds();
            newIdsDpt.remove(parcelId);
            departments.get(departmentId).setParcelIds(newIdsDpt);
            List<Integer> newIdsCourier = courierService.
                    getCourierById(courierId).getParcelIds();
            newIdsCourier.add(parcelId);
            Courier courier = courierService.getCourierById(courierId);
            courier.setParcelIds(newIdsCourier);
            courierService.updateCourier(courierId, courier);
            Parcel newParcel = parcelService.getParcelById(parcelId);
            newParcel.setCurrentLocation("Courier is delivering the parcel");
            parcelService.deleteParcel(parcelId);
            parcelService.addParcel(newParcel);
            parcelService.saveParcels();
            courierService.saveCouriers();
            saveDepartments();
        }
    }

    public void addParcel(final Integer departmentId,
                          final Parcel parcel) throws IOException {
        Department department = departments.get(departmentId);
        if (department != null) {
            List<Integer> parcelIds = department.getParcelIds();
            parcelService.addParcel(parcel);
            parcelIds.add(parcel.getParcelId());
            department.setParcelIds(parcelIds);
            parcel.setCurrentLocation(department.getDepartmentLocation());
            parcelService.saveParcels();
        }
    }


    public void deleteParcel(final Integer departmentId,
                             final Integer parcelId) throws IOException {
        if (departments.get(departmentId).getParcelIds().
                contains(parcelId)) {
            parcelService.deleteParcel(parcelId);
            parcelService.saveParcels();
        }
    }

    public List<Parcel> getAllParcels(final Integer departmentId) {
        List<Parcel> result = new LinkedList<>();
        for (Parcel parcel : parcelService.getAllParcels()) {
            if (departments.get(departmentId).getParcelIds().
                    contains(parcel.getParcelId())) {
                result.add(parcel);
            }
        }
        return result;
    }

    public Parcel getParcelById(final Integer departmentId,
                                final Integer parcelId) {
        Parcel result = new Parcel();
        for (Parcel parcel : parcelService.getAllParcels()) {
            if (departments.get(departmentId).getParcelIds().
                    contains(parcel.getParcelId())) {
                if (Objects.equals(parcel.getParcelId(), parcelId)) {
                    result = parcel;
                }
            }
        }
        return result;
    }

    public void deliverParcel(final Integer departmentIdFrom,
                              final Integer departmentIdTo,
                              final Integer parcelId)
            throws IOException {
        if (departments.get(departmentIdFrom).getParcelIds().
                contains(parcelId)) {
            List<Integer> newIdsFrom = departments.get(departmentIdFrom).
                    getParcelIds();
            newIdsFrom.remove(parcelId);
            departments.get(departmentIdFrom).setParcelIds(newIdsFrom);
            List<Integer> newIdsTo = departments.get(departmentIdTo).
                    getParcelIds();
            newIdsTo.add(parcelId);
            departments.get(departmentIdTo).setParcelIds(newIdsTo);
            Parcel newParcel = parcelService.getParcelById(parcelId);
            newParcel.setCurrentLocation(departments.
                    get(departmentIdTo).getDepartmentLocation());
            parcelService.deleteParcel(parcelId);
            parcelService.addParcel(newParcel);
            parcelService.saveParcels();
            saveDepartments();
        }
    }

    public void addCourier(final Integer departmentId,
                           final Courier courier) throws IOException {
        if (departments.get(departmentId) != null) {
            courier.setDepartmentId(departmentId);
            courierService.addCourier(courier);
            courierService.saveCouriers();
        }
    }

    public void deleteCourier(final Integer departmentId,
                              final Integer courierId) throws IOException {
        if (departments.get(departmentId) != null) {
            courierService.deleteCourier(courierId);
            courierService.saveCouriers();
        }
    }

    public List<Courier> getAllCouriers(final Integer departmentId) {
        List<Courier> result = new LinkedList<>();
        for (Courier courier : courierService.getAllCouriers()) {
            if (Objects.equals(courier.getDepartmentId(), departmentId)) {
                result.add(courier);
            }
        }
        return result;
    }

    public Courier getCourierById(final Integer departmentId,
                                  final Integer courierId) {
        Courier result = new Courier();
        for (Courier courier : courierService.getAllCouriers()) {
            if (Objects.equals(courier.getDepartmentId(), departmentId)) {
                if (Objects.equals(courier.getCourierId(), courierId)) {
                    result = courier;
                }
            }
        }
        return result;
    }

    @PreDestroy
    public void saveDepartments() throws IOException {
        departmentDataRepository.save(this.departments, "res\\");
    }

    @PostConstruct
    public void loadDepartments() throws IOException, ParseException {
        if (departmentDataRepository.load("res\\") != null) {
            this.departments = departmentDataRepository.load("res\\");
        }
    }
}
