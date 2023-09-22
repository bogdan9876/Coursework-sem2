package ua.lviv.iot.algo.part1.coursova.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.part1.coursova.model.Courier;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Component
public class CourierDataRepository extends DataRepository<Courier> {

    @Override
    protected Integer getId(final Courier courier) {
        return courier.getCourierId();
    }

    @Override
    protected Courier fill(final List<String> values) {
        List<Integer> ids = new LinkedList<>();
        if (!Objects.equals(values.get(4), "")) {
            ids = Arrays.stream(values.get(4).split(", "))
                    .map(s -> Integer.parseInt(s.trim())).
                    collect(Collectors.toList());
        }
        return new Courier(Integer.parseInt(values.get(0)),
                Integer.parseInt(values.get(1)),
                values.get(2), Boolean.parseBoolean(values.get(3)), ids);
    }

    @Override
    protected String getPrefix() {
        return "courier";
    }

}
