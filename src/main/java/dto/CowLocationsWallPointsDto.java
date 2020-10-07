package dto;

import com.cow.backend.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

import java.util.List;

public class CowLocationsWallPointsDto {
    private Long cowShedId;
    @JsonSerialize(using = DateSerializer.class)
    private DateTime timeStamp;
    private List<WallpointLocationDto> wallpointLocationDtoList;

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

    public List<WallpointLocationDto> getWallpointLocationDtoList() {
        return wallpointLocationDtoList;
    }

    public void setWallpointLocationDtoList(List<WallpointLocationDto> wallpointLocationDtoList) {
        this.wallpointLocationDtoList = wallpointLocationDtoList;
    }
}
