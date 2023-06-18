package ua.lviv.iot.algo.part1.coursova.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.part1.coursova.model.Department;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Component
public class DepartmentDataRepository extends DataRepository<Department> {

    @Override
    protected Integer getId(final Department department) {
        return department.getDepartmentId();
    }

    @Override
    protected Department fill(final List<String> values) {
        List<Integer> ids = new LinkedList<>();
        if (!Objects.equals(values.get(3), "")) {
            ids = Arrays.stream(values.get(3).split(", "))
                    .map(s -> Integer.parseInt(s.trim())).
                    collect(Collectors.toList());
        }
        return new Department(values.get(1), values.get(2), ids,
                Integer.parseInt(values.get(0)));
    }

    @Override
    protected String getPrefix() {
        return "department";
    }


}
