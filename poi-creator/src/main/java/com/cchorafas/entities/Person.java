package com.cchorafas.entities;

import com.google.gson.Gson;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Person extends PanacheEntity {

    @NotEmpty(message = "First name cannot be null or empty")
    @NotNull
    public String firstName;

    @NotEmpty(message = "Last name cannot be null or empty")
    @NotNull
    public String lastName;

    @NotEmpty(message = "Birthday cannot be null or empty")
    @NotNull
    public String birthday;

    @Min(message="Age shouldn't be negative", value=1)
    @Max(message="Age shouldn't be so high", value=100)
    public int age;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
