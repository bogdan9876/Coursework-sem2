package ua.lviv.iot.algo.part1.coursova.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.coursova.datastorage.ParcelDataRepository;
import ua.lviv.iot.algo.part1.coursova.model.Parcel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Service
public class ParcelService {

    @Autowired
    private ParcelDataRepository parcelDataRepository;

    private final AtomicInteger parcelIdCounter = new AtomicInteger(1);
    private final List<Parcel> parcels = new ArrayList<>();

    public List<Parcel> getAllParcels() {
        return new ArrayList<>(this.parcels);
    }

    public Parcel getParcelById(final Integer parcelId) {
        for (Parcel parcel : parcels) {
            if (parcel.getParcelId().equals(parcelId)) {
                return parcel;
            }
        }
        return null;
    }

    public Parcel addParcel(final Parcel parcel) throws IOException {
        Integer parcelId = parcel.getParcelId();
        if (parcelId == null || parcelId <= 0) {
            parcelId = parcelIdCounter.getAndIncrement();
            parcel.setParcelId(parcelId);
        }
        parcels.add(parcel);
        saveParcels();
        return parcel;
    }

    public void updateParcel(final Parcel updatedParcel,
                             final Integer parcelId) throws IOException {
        Parcel existingParcel = getParcelById(parcelId);

        if (existingParcel != null) {
            existingParcel.setWeight(updatedParcel.getWeight());
            existingParcel.setHeight(updatedParcel.getHeight());
            existingParcel.setWidth(updatedParcel.getWidth());
            existingParcel.setLength(updatedParcel.getLength());
            existingParcel.setOrigin(updatedParcel.getOrigin());
            existingParcel.setShippingAddress(updatedParcel.
                    getShippingAddress());
            existingParcel.setCurrentLocation(updatedParcel.
                    getCurrentLocation());
            existingParcel.setSendingDate(updatedParcel.getSendingDate());
            saveParcels();
        }
    }

    public void deleteParcel(final Integer parcelId) {
        parcels.removeIf(parcel -> parcel.getParcelId().equals(parcelId));
    }

    @PreDestroy
    public void saveParcels() throws IOException {
        HashMap<Integer, Parcel> parcelMap = new HashMap<>();
        for (Parcel parcel : parcels) {
            parcelMap.put(parcel.getParcelId(), parcel);
        }
        parcelDataRepository.save(parcelMap, "res\\");
    }

    @PostConstruct
    public void loadParcels() throws IOException, ParseException {
        HashMap<Integer, Parcel> loadedParcels = parcelDataRepository.
                load("res\\");
        if (loadedParcels != null) {
            parcels.clear();
            parcels.addAll(loadedParcels.values());
            Integer maxId = parcels.stream()
                    .mapToInt(Parcel::getParcelId)
                    .max()
                    .orElse(0);
            parcelIdCounter.set(maxId + 1);
        }
    }
}
