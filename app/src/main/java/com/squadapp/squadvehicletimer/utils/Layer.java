package com.squadapp.squadvehicletimer.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Layer {
    @JsonProperty("layer_name")
    private String name;
    @JsonProperty("faction_left")
    private Faction factionLeft;
    @JsonProperty("faction_right")
    private Faction factionRight;
    @JsonProperty("vehicle_list_left")
    private ArrayList<Vehicle> vehicleListLeft;
    @JsonProperty("vehicle_list_right")
    private ArrayList<Vehicle> vehicleListRight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faction getFactionLeft() {
        return factionLeft;
    }

    public void setFactionLeft(Faction factionLeft) {
        this.factionLeft = factionLeft;
    }

    public Faction getFactionRight() {
        return factionRight;
    }

    public void setFactionRight(Faction factionRight) {
        this.factionRight = factionRight;
    }

    public ArrayList<Vehicle> getVehicleListLeft() {
        return vehicleListLeft;
    }

    public void setVehicleListLeft(ArrayList<Vehicle> vehicleListLeft) {
        this.vehicleListLeft = vehicleListLeft;
    }

    public ArrayList<Vehicle> getVehicleListRight() {
        return vehicleListRight;
    }

    public void setVehicleListRight(ArrayList<Vehicle> vehicleListRight) {
        this.vehicleListRight = vehicleListRight;
    }
}
