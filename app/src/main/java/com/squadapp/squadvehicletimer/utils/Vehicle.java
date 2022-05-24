package com.squadapp.squadvehicletimer.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehicle {
    @JsonProperty("vehicle_name")
    private String name;
    @JsonProperty("vehicle_class")
    private VehicleType type;
    @JsonProperty("respawn_time")
    private int respawnTime;
    @JsonProperty("ticket_cost")
    private int ticketCost;
    @JsonProperty("delay_time")
    private int delayTime;

    /*
    public Vehicle(String name, VehicleType type, int respawnTime, int delayTime) {
        this.name = name;
        this.type = type;
        this.respawnTime = respawnTime;
        this.delayTime = delayTime;
    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTime(int respawnTime) {
        this.respawnTime = respawnTime;
    }

    public int getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(int ticketCost) {
        this.ticketCost = ticketCost;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }
}

