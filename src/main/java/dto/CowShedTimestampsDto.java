package dto;

import com.cow.backend.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

public class CowShedTimestampsDto {
    @JsonSerialize(using = DateSerializer.class)
    private DateTime timestampStart;
    @JsonSerialize(using = DateSerializer.class)
    private DateTime timestampEnd;

    public DateTime getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(DateTime timestampStart) {
        this.timestampStart = timestampStart;
    }

    public DateTime getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(DateTime timestampEnd) {
        this.timestampEnd = timestampEnd;
    }
}
