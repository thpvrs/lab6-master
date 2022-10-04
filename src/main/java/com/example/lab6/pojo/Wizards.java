package com.example.lab6.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Document("Wizard")
public class Wizards implements Serializable {
    private List<Wizard> wids = new ArrayList<>();

    public Wizards() {
    }

    public Wizards(List<Wizard> wids) {
        this.wids = wids;
    }

    public List<Wizard> getWids() {
        return wids;
    }

    public void setWids(List<Wizard> wids) {
        this.wids = wids;
    }
}
