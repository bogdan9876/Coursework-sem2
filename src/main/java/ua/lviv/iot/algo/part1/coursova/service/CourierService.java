package ua.lviv.iot.algo.part1.coursova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.coursova.datastorage.CourierDataRepository;
import ua.lviv.iot.algo.part1.coursova.model.Courier;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class CourierService {

    @Autowired
    private ParcelService parcelService;
    @Autowired
    private CourierDataRepository courierDataRepository;

    private HashMap<Integer, Courier> couriers = new HashMap<>();

    private Integer nextCourierId = 1;

    public List<Courier> getAllCouriers() {
        return new LinkedList<>(this.couriers.values());
    }

    public Courier getCourierById(final Integer courierId) {
        return couriers.get(courierId);
    }

    public void deliverParcel(final Integer courierId,
                              final Integer parcelId) throws IOException {
        if (this.couriers.get(courierId).getParcelIds().contains(parcelId)) {
            parcelService.deleteParcel(parcelId);
            List<Integer> newIds = this.couriers.get(courierId).getParcelIds();
            newIds.remove(parcelId);
            this.couriers.get(courierId).setParcelIds(newIds);
            parcelService.saveParcels();
            saveCouriers();
        }
    }

    public void addCourier(final Courier courier) {
        Integer courierId = nextCourierId;
        courier.setCourierId(courierId);
        nextCourierId++;

        List<Integer> parcelIds = new LinkedList<>();
        courier.setParcelIds(parcelIds);
        couriers.put(courierId, courier);
    }


    public void updateCourier(final Integer courierId,
                              final Courier updatedCourier) {
        Courier existingCourier = getCourierById(courierId);
        if (existingCourier != null) {
            existingCourier.setFullName(updatedCourier.getFullName());
            existingCourier.setWorking(updatedCourier.isWorking());
            existingCourier.setParcelIds(updatedCourier.getParcelIds());
        }
    }


    public void deleteCourier(final Integer courierId) {
        for (Integer parcelId: this.couriers.get(courierId).getParcelIds()) {
            parcelService.deleteParcel(parcelId);
        }
        this.couriers.remove(courierId);
    }


    @PreDestroy
    public void saveCouriers() throws IOException {
        courierDataRepository.save(this.couriers, "res\\");
    }

    @PostConstruct
    public void loadCouriers() throws IOException, ParseException {
        if (courierDataRepository.load("res\\") != null) {
            this.couriers = courierDataRepository.load("res\\");
        }
    }

}
