package com.squadapp.squadvehicletimer.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class VehicleWithWeapons {
    @JsonProperty("vehicle_name")
    private String name;
    @JsonProperty("health")
    private float health;
    @JsonProperty("turret_health")
    private float turretHealth;
    @JsonProperty("commander_turret_health")
    private float commanderHealth;
    @JsonProperty("heat_multiplier")
    private float heatMultiplier;
    @JsonProperty("hat_multiplier")
    private float hatMultiplier;
    @JsonProperty("kinetic_multiplier")
    private float kineticMultiplier;
    @JsonProperty("frag_multiplier")
    private float fragMultiplier;
    @JsonProperty("small_arms_multiplier")
    private float smallArmsMultiplier;
    @JsonProperty("explosives_multiplier")
    private float explosivesMultiplier;
    @JsonProperty("thermite_multiplier")
    private float thermiteMultiplier;
    @JsonProperty("total_seats")
    private int totalSeats;
    @JsonProperty("weapon_list")
    private ArrayList<VehicleWeapon> weaponList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getTurretHealth() {
        return turretHealth;
    }

    public void setTurretHealth(float turretHealth) {
        this.turretHealth = turretHealth;
    }

    public float getCommanderHealth() {
        return commanderHealth;
    }

    public void setCommanderHealth(float commanderHealth) {
        this.commanderHealth = commanderHealth;
    }

    public float getHeatMultiplier() {
        return heatMultiplier;
    }

    public void setHeatMultiplier(float heatMultiplier) {
        this.heatMultiplier = heatMultiplier;
    }

    public float getHatMultiplier() {
        return hatMultiplier;
    }

    public void setHatMultiplier(float hatMultiplier) {
        this.hatMultiplier = hatMultiplier;
    }

    public float getKineticMultiplier() {
        return kineticMultiplier;
    }

    public void setKineticMultiplier(float kineticMultiplier) {
        this.kineticMultiplier = kineticMultiplier;
    }

    public float getFragMultiplier() {
        return fragMultiplier;
    }

    public void setFragMultiplier(float fragMultiplier) {
        this.fragMultiplier = fragMultiplier;
    }

    public float getSmallArmsMultiplier() {
        return smallArmsMultiplier;
    }

    public void setSmallArmsMultiplier(float smallArmsMultiplier) {
        this.smallArmsMultiplier = smallArmsMultiplier;
    }

    public float getExplosivesMultiplier() {
        return explosivesMultiplier;
    }

    public void setExplosivesMultiplier(float explosivesMultiplier) {
        this.explosivesMultiplier = explosivesMultiplier;
    }

    public float getThermiteMultiplier() {
        return thermiteMultiplier;
    }

    public void setThermiteMultiplier(float thermiteMultiplier) {
        this.thermiteMultiplier = thermiteMultiplier;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public ArrayList<VehicleWeapon> getWeaponList() {
        return weaponList;
    }

    public void setWeaponList(ArrayList<VehicleWeapon> weaponList) {
        this.weaponList = weaponList;
    }

    @Override
    public String toString() {
        return  "name = " + name + '\n' +
                "health = " + health + '\n' +
                "turretHealth = " + turretHealth + '\n' +
                "commanderHealth = " + commanderHealth + '\n' +
                "heatMultiplier = " + heatMultiplier + '\n' +
                "hatMultiplier = " + hatMultiplier + '\n' +
                "kineticMultiplier = " + kineticMultiplier + '\n' +
                "fragMultiplier = " + fragMultiplier + '\n' +
                "smallArmsMultiplier = " + smallArmsMultiplier + '\n' +
                "explosivesMultiplier = " + explosivesMultiplier + '\n' +
                "thermiteMultiplier = " + thermiteMultiplier + '\n' +
                "totalSeats = " + totalSeats + '\n';
    }
}
