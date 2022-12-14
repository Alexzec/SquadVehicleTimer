package com.squadapp.squadvehicletimer.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleWeapon {
    @JsonProperty("weapon_name")
    private String name;
    @JsonProperty("max_damage")
    private float maxDamage;
    @JsonProperty("min_damage")
    private float minDamage;
    @JsonProperty("max_penetration")
    private float maxPenetration;
    @JsonProperty("min_penetration")
    private float minPenetration;
    @JsonProperty("moa")
    private float moa;
    @JsonProperty("max_mags")
    private int maxMags;
    @JsonProperty("rounds_per_mag")
    private int roundsPerMag;
    @JsonProperty("time_between_shots")
    private float timeBetweenShots;
    @JsonProperty("muzzle_velocity")
    private float muzzleVelocity;
    @JsonProperty("tac_reload")
    private float tacReload;
    @JsonProperty("dry_reload")
    private float dryReload;
    @JsonProperty("time_between_rearm")
    private float timeBetweenRearm;
    @JsonProperty("rearm_one_magazine")
    private boolean rearmOneMagazine;
    @JsonProperty("rearm_x_rounds_per_rearm")
    private boolean rearmXRoundsPerRearm;
    @JsonProperty("rounds_per_rearm")
    private int roundsPerRearm;
    @JsonProperty("damage_type")
    private DamageType damageType;
    @JsonProperty("ammo_per_rearm")
    private float ammoPerRearm;
    @JsonProperty("damage_falloff_start")
    private float damageFalloffStart;
    @JsonProperty("damage_falloff_end")
    private float damageFalloffEnd;
    @JsonProperty("damage_falloff_factor")
    private float damageFalloffFactor;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
    }

    public float getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(float minDamage) {
        this.minDamage = minDamage;
    }

    public float getMaxPenetration() {
        return maxPenetration;
    }

    public void setMaxPenetration(float maxPenetration) {
        this.maxPenetration = maxPenetration;
    }

    public float getMinPenetration() {
        return minPenetration;
    }

    public void setMinPenetration(float minPenetration) {
        this.minPenetration = minPenetration;
    }

    public float getMoa() {
        return moa;
    }

    public void setMoa(float moa) {
        this.moa = moa;
    }

    public int getMaxMags() {
        return maxMags;
    }

    public void setMaxMags(int maxMags) {
        this.maxMags = maxMags;
    }

    public int getRoundsPerMag() {
        return roundsPerMag;
    }

    public void setRoundsPerMag(int roundsPerMag) {
        this.roundsPerMag = roundsPerMag;
    }

    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }

    public void setTimeBetweenShots(float timeBetweenShots) {
        this.timeBetweenShots = timeBetweenShots;
    }

    public float getMuzzleVelocity() {
        return muzzleVelocity;
    }

    public void setMuzzleVelocity(float muzzleVelocity) {
        this.muzzleVelocity = muzzleVelocity;
    }

    public float getTacReload() {
        return tacReload;
    }

    public void setTacReload(float tacReload) {
        this.tacReload = tacReload;
    }

    public float getDryReload() {
        return dryReload;
    }

    public void setDryReload(float dryReload) {
        this.dryReload = dryReload;
    }

    public float getTimeBetweenRearm() {
        return timeBetweenRearm;
    }

    public void setTimeBetweenRearm(float timeBetweenRearm) {
        this.timeBetweenRearm = timeBetweenRearm;
    }

    public boolean isRearmOneMagazine() {
        return rearmOneMagazine;
    }

    public void setRearmOneMagazine(boolean rearmOneMagazine) {
        this.rearmOneMagazine = rearmOneMagazine;
    }

    public boolean isRearmXRoundsPerRearm() {
        return rearmXRoundsPerRearm;
    }

    public void setRearmXRoundsPerRearm(boolean rearmXRoundsPerRearm) {
        this.rearmXRoundsPerRearm = rearmXRoundsPerRearm;
    }

    public int getRoundsPerRearm() {
        return roundsPerRearm;
    }

    public void setRoundsPerRearm(int roundsPerRearm) {
        this.roundsPerRearm = roundsPerRearm;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public float getAmmoPerRearm() {
        return ammoPerRearm;
    }

    public void setAmmoPerRearm(float ammoPerRearm) {
        this.ammoPerRearm = ammoPerRearm;
    }

    public float getDamageFalloffStart() {
        return damageFalloffStart;
    }

    public void setDamageFalloffStart(float damageFalloffStart) {
        this.damageFalloffStart = damageFalloffStart;
    }

    public float getDamageFalloffEnd() {
        return damageFalloffEnd;
    }

    public void setDamageFalloffEnd(float damageFalloffEnd) {
        this.damageFalloffEnd = damageFalloffEnd;
    }

    public float getDamageFalloffFactor() {
        return damageFalloffFactor;
    }

    public void setDamageFalloffFactor(float damageFalloffFactor) {
        this.damageFalloffFactor = damageFalloffFactor;
    }

    @Override
    public String toString() {
        String s = new String();
        s += "name = " + name;
        if (damageType!=DamageType.None){s += '\n' + "damageType = " + damageType;}
        if(maxDamage!=minDamage){
            s += '\n' + "maxDamage = " + maxDamage +
                '\n' + "minDamage = " + minDamage;
        } else {
            s += '\n' + "damage = " + maxDamage;
        }
        if(maxPenetration!=minPenetration){
            s += '\n' + "maxPenetration = " + maxPenetration +
                    '\n' + "minPenetration = " + minPenetration;
        } else {
            s += '\n' + "penetration = " + maxPenetration;
        }
        s += '\n' + "moa = " + moa +
                '\n' + "maxMags = " + maxMags +
                '\n' + "roundsPerMag = " + roundsPerMag +
                '\n' + "muzzleVelocity = " + muzzleVelocity/100 + "m/s";
        if(roundsPerMag>1){
            s += '\n' + "ROF = " + (int)getRateOfFire() + "RPM";
        }
        if(tacReload!=dryReload){
            s += '\n' + "tacReload = " + tacReload +
                    '\n' + "dryReload = " + dryReload;
        } else {
            s += '\n' + "reload = " + dryReload;
        }
        s += '\n' + "timeBetweenRearm = " + timeBetweenRearm;
        s += '\n' + "ammoCostPerRearm = " + ammoPerRearm;
        if (rearmXRoundsPerRearm){
            s += '\n' + "roundsPerRearm = " + roundsPerRearm;
        }
        else if(rearmOneMagazine){
            s += '\n' + "roundsPerRearm = " + roundsPerMag;
        }
        s += '\n' + "Full rearm time = " + fullRearmTime() +
                '\n' + "Full rearm ammo cost = " + fullRearmAmmo();

        return s;
    }

    private float fullRearmTime(){
        float rearmTime=0;

        if (rearmOneMagazine){
            rearmTime = timeBetweenRearm * maxMags;
        } else if (rearmXRoundsPerRearm){
            rearmTime = timeBetweenRearm * (roundsPerMag * maxMags / roundsPerRearm);
        }

        return rearmTime;
    }

    private float fullRearmAmmo(){
        float rearmAmmo=0;

        if (rearmOneMagazine){
            rearmAmmo = ammoPerRearm * maxMags;
        } else if (rearmXRoundsPerRearm){
            rearmAmmo = ammoPerRearm * (roundsPerMag * maxMags / roundsPerRearm);
        }

        return rearmAmmo;
    }

    private float getRateOfFire(){
        return 60/(timeBetweenShots);
    }
}