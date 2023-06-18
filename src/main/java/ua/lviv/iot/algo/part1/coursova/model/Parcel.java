package ua.lviv.iot.algo.part1.coursova.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressFBWarnings
public class Parcel extends Entity {

    private float weight;
    private float height;
    private float width;
    private float length;
    private String origin;
    @NotNull(message = "Parcel's shipping address cannot be null")
    private String shippingAddress;
    @NotNull(message = "Parcel's current location cannot be null")
    private String currentLocation;
    @NotNull(message = "Parcel's sending date sent cannot be null")
    private Date sendingDate;

    private Integer parcelId;

    @Override
    @JsonIgnore
    public String getHeaders() {
        return "parcelId, weight, height, "
                + "width, length, origin, "
                + "shippingAddress, currentLocation, sendingDate";
    }

    @Override
    public String toCSV() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(sendingDate);
        return parcelId + ", " + weight + ", " + height + ", "
                + width + ", " + length + ", " + origin + ", "
                + shippingAddress + ", " + currentLocation + ", " + date;
    }
}
