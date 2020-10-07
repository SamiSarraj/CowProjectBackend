package dto;

import com.cow.backend.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

import java.util.List;

public class CowLocationsDto {
    private Long cowShedId;
    @JsonSerialize(using = DateSerializer.class)
    private DateTime timeStamp;
    private List<SectionDto> sectionDtos;

    public Long getCowShedId() {
        return cowShedId;
    }

    public void setCowShedId(Long cowShedId) {
        this.cowShedId = cowShedId;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<SectionDto> getSectionDtos() {
        return sectionDtos;
    }

    public void setSectionDtos(List<SectionDto> sectionDtos) {
        this.sectionDtos = sectionDtos;
    }
}
