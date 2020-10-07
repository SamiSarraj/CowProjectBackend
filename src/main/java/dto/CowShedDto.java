package dto;

import com.cow.backend.entity.Cow;

import java.util.List;

public class CowShedDto {
    private Long idCowShed;
    private Integer width;
    private Integer height;
    private Long idFarm;
    private List<WallpointDto> wallpointDtos;
    private CowShedTimestampsDto cowShedTimestampsDto;

    public Long getIdCowShed() {
        return idCowShed;
    }

    public void setIdCowShed(Long idCowShed) {
        this.idCowShed = idCowShed;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(Long idFarm) {
        this.idFarm = idFarm;
    }

    public List<WallpointDto> getWallpointDtos() {
        return wallpointDtos;
    }

    public void setWallpointDtos(List<WallpointDto> wallpointDtos) {
        this.wallpointDtos = wallpointDtos;
    }

    public CowShedTimestampsDto getCowShedTimestampsDto() {
        return cowShedTimestampsDto;
    }

    public void setCowShedTimestampsDto(CowShedTimestampsDto cowShedTimestampsDto) {
        this.cowShedTimestampsDto = cowShedTimestampsDto;
    }
}
