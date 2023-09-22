package ua.lviv.iot.algo.part1.coursova.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Department extends Entity {

    @NotNull(message = "Department's location cannot be null")
    private String departmentLocation;
    @NotNull(message = "Department's working time cannot be null")
    private String workingTime;

    private List<Integer> parcelIds;

    private Integer departmentId;


    @JsonIgnore
    @Override
    public String getHeaders() {
        return "departmentId, departmentLocation, workingTime, parcelIds";
    }

    @Override
    public String toCSV() {
        return departmentId + ", " + departmentLocation + ", "
                + workingTime + ", " + parcelIds;
    }
}
