package com.squadapp.squadvehicletimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.slider.Slider;
import com.squadapp.squadvehicletimer.utils.VehicleWeapon;
import com.squadapp.squadvehicletimer.utils.VehicleWithWeapons;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.squadapp.squadvehicletimer.R.string.load_vehicle_fail_message;

public class VehicleStatsActivity extends AppCompatActivity {
    private final String JSON_PATH = "raw/vehicles.json";

    private VehicleWithWeapons[] vehicleList;
    private VehicleWithWeapons currentVehicleOne;
    private VehicleWithWeapons currentVehicleTwo;
    private VehicleWeapon selectedWeaponOne;
    private VehicleWeapon selectedWeaponTwo;
    private TextView VehicleOneDataTextView;
    private TextView VehicleTwoDataTextView;
    private TextView WeaponOneDataTextView;
    private TextView WeaponTwoDataTextView;
    private TextView vehicleOneCompare;
    private TextView vehicleTwoCompare;
    private TextView rangeTextView;
    private float distance = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_stats);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Vehicle stats");
        setSupportActionBar(myToolbar);

        loadVehicleData();

        VehicleOneDataTextView = findViewById(R.id.vehicle_one_data);
        VehicleTwoDataTextView = findViewById(R.id.vehicle_two_data);
        WeaponOneDataTextView = findViewById(R.id.weapon_one_data);
        WeaponTwoDataTextView = findViewById(R.id.weapon_two_data);
        vehicleOneCompare = findViewById(R.id.vehicle_one_compare);
        vehicleTwoCompare = findViewById(R.id.vehicle_two_compare);
        rangeTextView = findViewById(R.id.range_text);

        Slider slider = findViewById(R.id.range_slider);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                distance = value;
                rangeTextView.setText("Range : " + distance);
                vehicleOneCompare.setText(CompareVehicles(selectedWeaponOne, currentVehicleTwo));
                vehicleTwoCompare.setText(CompareVehicles(selectedWeaponTwo, currentVehicleOne));
            }
        });

        List<String> vehicleNamesList = new ArrayList<>();
        for (VehicleWithWeapons vehicle : vehicleList){
            vehicleNamesList.add(vehicle.getName());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vehicleNamesList = vehicleNamesList.stream().sorted().collect(Collectors.toList());
            Arrays.sort(vehicleList, new Comparator<VehicleWithWeapons>() {
                public int compare(VehicleWithWeapons v1, VehicleWithWeapons v2) {
                    return v1.getName().compareTo(v2.getName());
                }});

        }

        Spinner dropdownVehicleOne = findViewById(R.id.vehicle_one_spinner);
        Spinner dropdownVehicleTwo = findViewById(R.id.vehicle_two_spinner);
        ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, vehicleNamesList);
        dropdownVehicleOne.setAdapter(adapterVehicle);
        dropdownVehicleTwo.setAdapter(adapterVehicle);
        if (vehicleNamesList.size() > 0) {
            currentVehicleOne = vehicleList[0];
            currentVehicleTwo = vehicleList[0];
        } else {
            Toast.makeText(this, getString(load_vehicle_fail_message), Toast.LENGTH_LONG).show();
            this.finish();
            System.exit(1);
        }

        if(currentVehicleOne.getWeaponList().size()>0){
            selectedWeaponOne = currentVehicleOne.getWeaponList().get(0);
        } else {
            selectedWeaponOne = new VehicleWeapon();
        }
        if(currentVehicleTwo.getWeaponList().size()>0){
            selectedWeaponTwo = currentVehicleTwo.getWeaponList().get(0);
        } else {
            selectedWeaponTwo = new VehicleWeapon();
        }

        Spinner dropdownWeaponOne = findViewById(R.id.weapon_one_spinner);
        Spinner dropdownWeaponTwo = findViewById(R.id.weapon_two_spinner);
        ArrayAdapter<String> adapterWeaponOne = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            adapterWeaponOne = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, currentVehicleOne.getWeaponList()
                    .stream().
                            map(VehicleWeapon::getName).
                            collect(Collectors.toList()));
        }
        dropdownWeaponOne.setAdapter(adapterWeaponOne);

        ArrayAdapter<String> adapterWeaponTwo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            adapterWeaponTwo = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, currentVehicleTwo.getWeaponList()
                    .stream().
                            map(VehicleWeapon::getName).
                            collect(Collectors.toList()));
        }
        dropdownWeaponTwo.setAdapter(adapterWeaponTwo);

        dropdownWeaponOne.setSelection(0);
        dropdownWeaponOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedWeaponOne = currentVehicleOne.getWeaponList().get(position);
                displayWeapon(WeaponOneDataTextView, selectedWeaponOne);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        dropdownVehicleOne.setSelection(0);
        dropdownVehicleOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                currentVehicleOne = vehicleList[position];
                ((ArrayAdapter<String>) dropdownWeaponOne.getAdapter()).clear();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ((ArrayAdapter<String>) dropdownWeaponOne.getAdapter()).addAll(currentVehicleOne.getWeaponList()
                            .stream().
                                    map(VehicleWeapon::getName).
                                    collect(Collectors.toList()));
                }
                else {
                    for (VehicleWeapon weapon : currentVehicleOne.getWeaponList()){
                        ((ArrayAdapter<String>) dropdownWeaponOne.getAdapter()).add(weapon.getName());
                    }
                }
                displayVehicle(VehicleOneDataTextView, currentVehicleOne);
                dropdownWeaponOne.setSelection(0);
                if(currentVehicleOne.getWeaponList().size()>0){
                    selectedWeaponOne = currentVehicleOne.getWeaponList().get(0);
                } else {
                    selectedWeaponOne = new VehicleWeapon();
                }
                displayWeapon(WeaponOneDataTextView, selectedWeaponOne);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        // to display the layer on launch
        displayWeapon(WeaponOneDataTextView, currentVehicleOne.getWeaponList().get(0));

        dropdownWeaponTwo.setSelection(0);
        dropdownWeaponTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedWeaponTwo = currentVehicleTwo.getWeaponList().get(position);
                displayWeapon(WeaponTwoDataTextView, selectedWeaponTwo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        dropdownVehicleTwo.setSelection(0);
        dropdownVehicleTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                currentVehicleTwo = vehicleList[position];
                ((ArrayAdapter<String>) dropdownWeaponTwo.getAdapter()).clear();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ((ArrayAdapter<String>) dropdownWeaponTwo.getAdapter()).addAll(currentVehicleTwo.getWeaponList()
                            .stream().
                                    map(VehicleWeapon::getName).
                                    collect(Collectors.toList()));
                }
                else {
                    for (VehicleWeapon weapon : currentVehicleTwo.getWeaponList()){
                        ((ArrayAdapter<String>) dropdownWeaponTwo.getAdapter()).add(weapon.getName());
                    }
                }
                displayVehicle(VehicleTwoDataTextView, currentVehicleTwo);
                dropdownWeaponTwo.setSelection(0);
                if(currentVehicleTwo.getWeaponList().size()>0){
                    selectedWeaponTwo = currentVehicleTwo.getWeaponList().get(0);
                } else {
                    selectedWeaponTwo = new VehicleWeapon();
                }
                displayWeapon(WeaponTwoDataTextView, selectedWeaponTwo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        displayWeapon(WeaponTwoDataTextView, currentVehicleTwo.getWeaponList().get(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        switch (item.getItemId()) {
            case R.id.action_timers:
                myIntent = new Intent(this, MainActivity.class);
                startActivityForResult(myIntent, 0);
                return true;

            case R.id.action_vehicles:
                myIntent = new Intent(this, VehicleStatsActivity.class);
                startActivityForResult(myIntent, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** loads vehicles data from the vehicles file */
    private void loadVehicleData(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            vehicleList = mapper.readValue(getAssets().open(JSON_PATH), VehicleWithWeapons[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayVehicle(TextView textView, VehicleWithWeapons vehicle){
        textView.setText(vehicle.toString());
    }

    private void displayWeapon(TextView textView, VehicleWeapon weapon){
        textView.setText(weapon.getName()==null ? "Weapon name : " : weapon.toString());
        vehicleOneCompare.setText(CompareVehicles(selectedWeaponOne, currentVehicleTwo));
        vehicleTwoCompare.setText(CompareVehicles(selectedWeaponTwo, currentVehicleOne));
    }

    private String CompareVehicles(VehicleWeapon shootingWeapon, VehicleWithWeapons targetVehicle){
        String comparisonString = "";
        if(shootingWeapon.getName() != null){
            float targetMultiplier = 0;
            switch (shootingWeapon.getDamageType()){
                case HAT:
                    targetMultiplier = targetVehicle.getHatMultiplier();
                    break;
                case Heat:
                    targetMultiplier = targetVehicle.getHeatMultiplier();
                    break;
                case Kinetic:
                    targetMultiplier = targetVehicle.getKineticMultiplier();
                    break;
                case Explosive:
                    targetMultiplier = targetVehicle.getExplosivesMultiplier();
                    break;
                case SmallArms:
                    targetMultiplier = targetVehicle.getSmallArmsMultiplier();
                    break;
                case Fragmentation:
                    targetMultiplier = targetVehicle.getFragMultiplier();
                    break;
            }
            Float damagePerShot;
            if (shootingWeapon.getDamageFalloffFactor() != 0) {
                if (distance <= shootingWeapon.getDamageFalloffStart()) {
                    damagePerShot = shootingWeapon.getMaxDamage() * targetMultiplier;
                } else if (distance >= shootingWeapon.getDamageFalloffEnd()) {
                    damagePerShot = shootingWeapon.getMinDamage() * targetMultiplier;
                } else {
                    damagePerShot = (shootingWeapon.getMaxDamage()
                            -shootingWeapon.getDamageFalloffFactor() * (distance-shootingWeapon.getDamageFalloffStart()))
                            * targetMultiplier;
                }
            } else {
                damagePerShot = shootingWeapon.getMaxDamage() * targetMultiplier;
            }
            comparisonString+=String.format("Damage per shot = %.2f\n", damagePerShot);

            if(damagePerShot>0) {
                int numberOfShots = (int) Math.ceil(targetVehicle.getHealth() / damagePerShot);
                int fullMags = (int) Math.floor(numberOfShots / shootingWeapon.getRoundsPerMag());
                int shotsOutsideOfMags = numberOfShots - fullMags * shootingWeapon.getRoundsPerMag();
                float timeToKill = Math.max((shotsOutsideOfMags > 0 ? fullMags : fullMags - 1), 0) * shootingWeapon.getDryReload()
                        + fullMags * Math.max((shootingWeapon.getRoundsPerMag() - 1), 0) * shootingWeapon.getTimeBetweenShots()
                        + Math.max((shotsOutsideOfMags - 1), 0) * shootingWeapon.getTimeBetweenShots();

                comparisonString += "Number of shots = " + numberOfShots + "\n";
                comparisonString += "Number of mags = " + fullMags + "\n";
                comparisonString += String.format("Time to kill = %.2f", timeToKill);
            }
        }

        return comparisonString;
    }
}