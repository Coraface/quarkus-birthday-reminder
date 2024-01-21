package com.cchorafas.entities;

import com.google.gson.Gson;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Person extends PanacheEntity {

    public String firstName;
    public String lastName;
    public String birthday;
    public int age;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
