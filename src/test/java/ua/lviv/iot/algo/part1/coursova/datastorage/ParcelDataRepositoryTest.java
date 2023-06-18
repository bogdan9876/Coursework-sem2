package ua.lviv.iot.algo.part1.coursova.datastorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part1.coursova.model.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParcelDataRepositoryTest {

    private ParcelDataRepository parcelDataRepository;

    @BeforeEach
    public void setUp() {
        parcelDataRepository = new ParcelDataRepository();
    }

    @Test
    public void testGetId() throws ParseException {
        Parcel parcel = new Parcel(10.5f, 20.5f, 30.5f, 40.5f, "Origin", "Shipping Address",
                "Current", new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-19"), 123);
        Integer id = parcelDataRepository.getId(parcel);
        Assertions.assertEquals(123, id);
    }

    @Test
    public void testFill() throws ParseException {
        List<String> values = Arrays.asList("10.5", "20.5", "30.5", "40.5",
                "Origin", "Shipping Address", "Current", "2023-05-19");
        Parcel parcel = parcelDataRepository.fill(values);
        Assertions.assertEquals(10.5f, parcel.getWeight());
        Assertions.assertEquals(20.5f, parcel.getHeight());
        Assertions.assertEquals(30.5f, parcel.getWidth());
        Assertions.assertEquals(40.5f, parcel.getLength());
        Assertions.assertEquals("Origin", parcel.getOrigin());
        Assertions.assertEquals("Shipping Address", parcel.getShippingAddress());
        Assertions.assertEquals("Current", parcel.getCurrentLocation());
        Assertions.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-19"), parcel.getSendingDate());
        Assertions.assertNotNull(parcel.getParcelId());
    }

    @Test
    public void testGetPrefix() {
        String prefix = parcelDataRepository.getPrefix();
        Assertions.assertEquals("parcel", prefix);
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        HashMap<Integer, Parcel> parcelMap = new HashMap<>();
        Parcel parcel = new Parcel(10.5f, 20.5f, 30.5f, 40.5f, "Origin", "Shipping Address",
                "Current Location", new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-19"), 1);
        parcelMap.put(parcelDataRepository.getId(parcel), parcel);

        String directoryPath = "test_directory/";
        DataRepositoryAssistant.generateDirectory(directoryPath);
        parcelDataRepository.save(parcelMap, directoryPath);

        HashMap<Integer, Parcel> loadedParcelMap = parcelDataRepository.load(directoryPath);

        Assertions.assertEquals(parcelMap.size(), loadedParcelMap.size());
        Assertions.assertEquals(parcelMap.get(1), loadedParcelMap.get(1));
    }

}
