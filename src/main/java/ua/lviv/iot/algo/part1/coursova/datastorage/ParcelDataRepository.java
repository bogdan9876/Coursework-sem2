package ua.lviv.iot.algo.part1.coursova.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.part1.coursova.model.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ParcelDataRepository extends DataRepository<Parcel> {

    private final AtomicInteger idCounter = new AtomicInteger();

    @Override
    protected Integer getId(final Parcel parcel) {
        return parcel.getParcelId();
    }

    @Override
    protected Parcel fill(final List<String> values) throws ParseException {
        float weight = Float.parseFloat(values.get(0));
        float height = Float.parseFloat(values.get(1));
        float width = Float.parseFloat(values.get(2));
        float length = Float.parseFloat(values.get(3));
        String origin = values.get(4);
        String shippingAddress = values.get(5);
        String currentLocation = values.get(6);
        Date sendingDate = new SimpleDateFormat("yyyy-MM-dd").
                parse(values.get(7));

        Integer parcelId = idCounter.incrementAndGet();

        return new Parcel(weight, height, width, length, origin,
                shippingAddress, currentLocation, sendingDate, parcelId);
    }

    @Override
    protected String getPrefix() {
        return "parcel";
    }
}
