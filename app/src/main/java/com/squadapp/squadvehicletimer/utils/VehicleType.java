package com.squadapp.squadvehicletimer.utils;


import com.squadapp.squadvehicletimer.R;

public enum VehicleType {
    MBT(R.drawable.mbt_icon, R.drawable.mbt_destroyed_icon, R.drawable.mbt_ok_icon, 1),
    TRACKED_IFV(R.drawable.tracked_ifv_icon, R.drawable.tracked_ifv_destroyed_icon, R.drawable.tracked_ifv_ok_icon, 2),
    WHEELED_IFV(R.drawable.wheeled_ifv_icon, R.drawable.wheeled_ifv_destroyed_icon, R.drawable.wheeled_ifv_ok_icon, 3),
    TRACKED_APC(R.drawable.tracked_apc_icon, R.drawable.tracked_apc_destroyed_icon, R.drawable.tracked_apc_ok_icon,5),
    TRACKED_APC_OPEN_TOP(R.drawable.tracked_apc_open_icon, R.drawable.tracked_apc_open_destroyed_icon, R.drawable.tracked_apc_open_ok_icon,6),
    WHEELED_APC(R.drawable.wheeled_apc_icon, R.drawable.wheeled_apc_destroyed_icon, R.drawable.wheeled_apc_ok_icon,5),
    LOGI_TRUCK(R.drawable.logi_truck_icon, R.drawable.logi_truck_destroyed_icon, R.drawable.logi_truck_ok_icon,8),
    LOGI_CAR(R.drawable.logi_car_icon, R.drawable.logi_car_destroyed_icon, R.drawable.logi_car_ok_icon,8),
    HELI(R.drawable.heli_icon, R.drawable.heli_destroyed_icon, R.drawable.heli_ok_icon,9),
    TRANSPORT_TRUCK(R.drawable.transport_truck_icon, R.drawable.transport_truck_destroyed_icon, R.drawable.transport_truck_ok_icon,10),
    TRANSPORT_CAR(R.drawable.transport_car_icon, R.drawable.transport_car_destroyed_icon, R.drawable.transport_car_ok_icon,10),
    BIKE(R.drawable.bike_icon, R.drawable.bike_destroyed_icon, R.drawable.bike_ok_icon,11),
    CAR(R.drawable.car_icon, R.drawable.car_destroyed_icon, R.drawable.car_ok_icon,6),
    CAR_OPEN_TOP(R.drawable.car_open_icon, R.drawable.car_open_destroyed_icon, R.drawable.car_open_ok_icon,7),
    CAR_MORTAR(R.drawable.car_morter_icon, R.drawable.car_morter_destroyed_icon, R.drawable.car_morter_ok_icon,7),
    ARMOR_HUNTER(R.drawable.armor_hunter_icon, R.drawable.armor_hunter_destroyed_icon, R.drawable.armor_hunter_ok_icon,4),
    AA(R.drawable.aa_icon, R.drawable.aa_destroyed_icon, R.drawable.aa_ok_icon,7),
    AA_TRUCK(R.drawable.aa_truck_icon, R.drawable.aa_truck_destroyed_icon, R.drawable.aa_truck_ok_icon,7),
    RADIO(R.drawable.radio_icon, R.drawable.radio_bleeding_icon, R.drawable.radio_destroyed_icon,0),
    WHEELED_RECO(R.drawable.wheeled_ifv_icon, R.drawable.wheeled_ifv_destroyed_icon, R.drawable.wheeled_ifv_ok_icon, 3),
    BOAT_TURRET(R.drawable.boat_icon, R.drawable.boat_detroyed_icon, R.drawable.boat_ok_icon, 10),
    BOAT_LOGI(R.drawable.boat_icon, R.drawable.boat_detroyed_icon, R.drawable.boat_ok_icon, 10),
    BOAT_TRANSPORT(R.drawable.boat_icon, R.drawable.boat_detroyed_icon, R.drawable.boat_ok_icon, 10),
    TRACKED_APC_LOGI(R.drawable.tracked_apc_open_icon, R.drawable.tracked_apc_open_destroyed_icon, R.drawable.tracked_apc_open_ok_icon, 6);

    public final int icon;
    public final int destroyed_icon;
    public final int ok_icon;
    public final int display_priority;

    VehicleType(int icon, int destroyed_icon, int ok_icon, int display_priority) {
        this.icon = icon;
        this.destroyed_icon = destroyed_icon;
        this.ok_icon = ok_icon;
        this.display_priority = display_priority;
    }
}
