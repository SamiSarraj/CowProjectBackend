package com.cow.backend.entity;

import javax.persistence.*;

//Many to one with shed
@Entity
public class Wallpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer position_x;
    private Integer position_y;
    @ManyToOne
    private CowShed cowShed;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition_x() {
        return position_x;
    }

    public void setPosition_x(Integer position_x) {
        this.position_x = position_x;
    }

    public Integer getPosition_y() {
        return position_y;
    }

    public void setPosition_y(Integer position_y) {
        this.position_y = position_y;
    }

    public CowShed getCowShed() {
        return cowShed;
    }

    public void setCowShed(CowShed cowShed) {
        this.cowShed = cowShed;
    }
}
