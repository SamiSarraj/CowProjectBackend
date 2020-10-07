package dto;

import java.util.List;
import java.util.Set;

public class SectionDto {
    private Integer posX;
    private Integer posY;
    private Integer cowsCount;
    private Set<Long> cowsId;

    public Integer getPosX() {
        return posX;
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

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }
}
