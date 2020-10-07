package dto;

import java.util.Set;

public class WallpointLocationDto {
    private Long wallpointId;
    private Integer posX;
    private Integer posY;
    private Integer wallpointWidth;
    private Integer wallpointHeight;
    private Integer cowsCount;
    private Set<Long> cowsId;

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getWallpointWidth() {
        return wallpointWidth;
    }

    public void setWallpointWidth(Integer wallpointWidth) {
        this.wallpointWidth = wallpointWidth;
    }

    public Integer getWallpointHeight() {
        return wallpointHeight;
    }

    public void setWallpointHeight(Integer wallpointHeight) {
        this.wallpointHeight = wallpointHeight;
    }

    public Integer getCowsCount() {
        return cowsCount;
    }

    public void setCowsCount(Integer cowsCount) {
        this.cowsCount = cowsCount;
    }

    public Set<Long> getCowsId() {
        return cowsId;
    }

    public void setCowsId(Set<Long> cowsId) {
        this.cowsId = cowsId;
    }

    public Long getWallpointId() {
        return wallpointId;
    }

    public void setWallpointId(Long wallpointId) {
        this.wallpointId = wallpointId;
    }
}
