package com.cow.backend.entity;

import javax.persistence.*;

//One to many to location
// Many to one to shed
@Entity
public class Cow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CowShed cowShed;
    private Long breederId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CowShed getCowShed() {
        return cowShed;
    }

    public void setCowShed(CowShed cowShed) {
        this.cowShed = cowShed;
    }

    public Long getBreederId() {
        return breederId;
    }

    public void setBreederId(Long breederId) {
        this.breederId = breederId;
    }
}
