package ua.lviv.iot.algo.part1.coursova.datastorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part1.coursova.model.Courier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CourierDataRepositoryTest {

    private CourierDataRepository courierDataRepository;

    @BeforeEach
    public void setUp() {
        courierDataRepository = new CourierDataRepository();
    }

    @Test
    public void testGetId() {
        Courier courier = new Courier(1, 1, "John Doe", true, Arrays.asList(2, 3, 4));
        Integer id = courierDataRepository.getId(courier);
        Assertions.assertEquals(1, id);
    }

    @Test
    public void testFill() {
        List<String> values = Arrays.asList("1", "1", "Roman", "true", "2, 3, 4");
        Courier courier = courierDataRepository.fill(values);
        Assertions.assertEquals(1, courier.getCourierId());
        Assertions.assertEquals(1, courier.getDepartmentId());
        Assertions.assertEquals("Roman", courier.getFullName());
        Assertions.assertTrue(courier.isWorking());
        Assertions.assertEquals(Arrays.asList(2, 3, 4), courier.getParcelIds());
    }

    @Test
    public void testGetPrefix() {
        String prefix = courierDataRepository.getPrefix();
        Assertions.assertEquals("courier", prefix);
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        HashMap<Integer, Courier> courierMap = new HashMap<>();
        Courier courier = new Courier(1, 1, "Roman", true, Arrays.asList(2, 3, 4));
        courierMap.put(courierDataRepository.getId(courier), courier);

        String directoryPath = "test_directory/";
        DataRepositoryAssistant.generateDirectory(directoryPath);
        courierDataRepository.save(courierMap, directoryPath);

        HashMap<Integer, Courier> loadedCourierMap = courierDataRepository.load(directoryPath);

        Assertions.assertEquals(courierMap, loadedCourierMap);
    }
}
