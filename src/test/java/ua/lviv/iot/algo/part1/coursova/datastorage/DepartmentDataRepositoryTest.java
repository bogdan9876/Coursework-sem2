package ua.lviv.iot.algo.part1.coursova.datastorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part1.coursova.model.Department;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DepartmentDataRepositoryTest {

    private DepartmentDataRepository departmentDataRepository;

    @BeforeEach
    public void setUp() {
        departmentDataRepository = new DepartmentDataRepository();
    }

    @Test
    public void testGetId() {
        Department department = new Department("Kyiv", "09:00-19:00", Arrays.asList(1, 2, 3), 1);
        Integer id = departmentDataRepository.getId(department);
        Assertions.assertEquals(1, id);
    }

    @Test
    public void testFill() throws ParseException {
        List<String> values = Arrays.asList("1", "Kyiv", "09:00-19:00", "1, 2, 3");
        Department department = departmentDataRepository.fill(values);
        Assertions.assertEquals(1, department.getDepartmentId());
        Assertions.assertEquals("Kyiv", department.getDepartmentLocation());
        Assertions.assertEquals("09:00-19:00", department.getWorkingTime());
        Assertions.assertEquals(Arrays.asList(1, 2, 3), department.getParcelIds());
    }

    @Test
    public void testGetPrefix() {
        String prefix = departmentDataRepository.getPrefix();
        Assertions.assertEquals("department", prefix);
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        HashMap<Integer, Department> departmentMap = new HashMap<>();
        Department department = new Department("Kyiv", "09:00-19:00", Arrays.asList(1, 2, 3), 1);
        departmentMap.put(departmentDataRepository.getId(department), department);

        String directoryPath = "test_directory/";
        DataRepositoryAssistant.generateDirectory(directoryPath);
        departmentDataRepository.save(departmentMap, directoryPath);

        HashMap<Integer, Department> loadedDepartmentMap = departmentDataRepository.load(directoryPath);

        Assertions.assertEquals(departmentMap, loadedDepartmentMap);
    }
}
