package com.cow.backend.entity;

import javax.persistence.*;

//One to many with location
// Many to one with Cow_shed
//Many to one with algorithm
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer upper_left_x;
    private Integer upper_left_y;
    private Integer lower_right_x;
    private Integer lower_right_y;
    @ManyToOne
    private Algorithm algorithm;
    @ManyToOne
    private CowShed cowShed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUpper_left_x() {
        return upper_left_x;
    }

    public void setUpper_left_x(Integer upper_left_x) {
        this.upper_left_x = upper_left_x;
    }

    public Integer getUpper_left_y() {
        return upper_left_y;
    }

    public void setUpper_left_y(Integer upper_left_y) {
        this.upper_left_y = upper_left_y;
    }

    public Integer getLower_right_x() {
        return lower_right_x;
    }

    public void setLower_right_x(Integer lower_right_x) {
        this.lower_right_x = lower_right_x;
    }

    public Integer getLower_right_y() {
        return lower_right_y;
    }

    public void setLower_right_y(Integer lower_right_y) {
        this.lower_right_y = lower_right_y;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public CowShed getCowShed() {
        return cowShed;
    }

    public void setCowShed(CowShed cowShed) {
        this.cowShed = cowShed;
    }
}
