package com.squadapp.squadvehicletimer.utils;

import java.util.Date;

public class BattleReport {
    private Date timeOfDestruction;
    private Vehicle vehicleDestroyed;

    public BattleReport(Date timeOfDestruction, Vehicle vehicleDestroyed) {
        this.timeOfDestruction = timeOfDestruction;
        this.vehicleDestroyed = vehicleDestroyed;
    }

    public Date getTimeOfDestruction() {
        return timeOfDestruction;
    }

    public void setTimeOfDestruction(Date timeOfDestruction) {
        this.timeOfDestruction = timeOfDestruction;
    }

    public Vehicle getVehicleDestroyed() {
        return vehicleDestroyed;
    }

    public void setVehicleDestroyed(Vehicle vehicleDestroyed) {
        this.vehicleDestroyed = vehicleDestroyed;
    }
}
