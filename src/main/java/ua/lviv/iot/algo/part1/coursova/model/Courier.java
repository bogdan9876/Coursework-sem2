package ua.lviv.iot.algo.part1.coursova.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressFBWarnings
public class Courier extends Entity {
    private Integer courierId;
    private Integer departmentId;
    @NotNull(message = "Courier's full name shouldn't be null!")
    private String fullName;
    private boolean isWorking;

    private List<Integer> parcelIds;

    @Override
    @JsonIgnore
    public String getHeaders() {
        return "courierId, departmentId, fullName, "
                + "isWorking, parcelIds";
    }

    @Override
    public String toCSV() {
        return courierId + ", " + departmentId + ", " + fullName + ", "
                + isWorking + ", " + parcelIds;
    }
}
