package com.squadapp.squadvehicletimer;

import android.os.Build;
import android.os.Bundle;

import com.squadapp.squadvehicletimer.utils.BattleReport;
import com.squadapp.squadvehicletimer.utils.Layer;
import com.squadapp.squadvehicletimer.utils.Map;
import com.squadapp.squadvehicletimer.utils.Side;
import com.squadapp.squadvehicletimer.utils.Vehicle;
import com.squadapp.squadvehicletimer.utils.VehicleType;
import com.fasterxml.jackson.databind.ObjectMapper;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.squadapp.squadvehicletimer.R.string.load_map_fail_message;

public class MainActivity extends AppCompatActivity {
    private final String JSON_PATH = "raw";

    private ViewGroup leftLayout;
    private ViewGroup rightLayout;
    private TextView leftFactionTextView;
    private TextView rightFactionTextView;
    private TextView battleReportTitleTextView;
    private ExpandableLayout battleReportExpandableLayout;
    private ArrayList<TimerBlock> blockArmorList;
    private ArrayList<TimerBlock> blockTransportList;
    private ToggleButton toggleArmor;
    private ToggleButton toggleTransport;
    private boolean showArmor=true;
    private boolean showTransport=true;
    private static Set<VehicleType> armorVehicleTypes = null;
    private ArrayList<Map> mapList;
    private Map currentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load map list from json files
        loadDataFromFiles();

        leftLayout = findViewById(R.id.left_vertical_layout);
        rightLayout = findViewById(R.id.right_vertical_layout);
        leftFactionTextView = findViewById(R.id.left_faction_name);
        rightFactionTextView = findViewById(R.id.right_faction_name);
        battleReportTitleTextView = findViewById(R.id.batte_report_title);
        battleReportExpandableLayout = findViewById(R.id.battle_details_expandable_layout);

        toggleArmor = findViewById(R.id.toggle_armor);
        toggleTransport = findViewById(R.id.toggle_transport);

        // Sets are more efficient
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            armorVehicleTypes = Set.of(VehicleType.MBT, VehicleType.ARMOR_HUNTER,
                    VehicleType.TRACKED_IFV, VehicleType.WHEELED_IFV,
                    VehicleType.TRACKED_APC, VehicleType.WHEELED_APC,
                    VehicleType.TRACKED_APC_OPEN_TOP);
        } else {
            armorVehicleTypes =new HashSet<>(Arrays.asList(
                    VehicleType.MBT, VehicleType.ARMOR_HUNTER,
                    VehicleType.TRACKED_IFV, VehicleType.WHEELED_IFV,
                    VehicleType.TRACKED_APC, VehicleType.WHEELED_APC,
                    VehicleType.TRACKED_APC_OPEN_TOP));
        }

        List<String> mapListNames;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mapListNames = mapList.stream()
                    .map(Map::getName).collect(Collectors.toList());
        }
        else {
            mapListNames = new ArrayList<>();
            for (Map map : mapList){
                mapListNames.add(map.getName());
            }
        }
        Spinner dropdownMap = findViewById(R.id.map_spinner);
        ArrayAdapter<String> adapterMap = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, mapListNames);
        dropdownMap.setAdapter(adapterMap);
        if (mapList.size() > 0) {
            currentMap = mapList.get(0);
        } else {
            Toast.makeText(this, getString(load_map_fail_message), Toast.LENGTH_LONG).show();
            this.finish();
            System.exit(1);
        }

        Spinner dropdownLayer = findViewById(R.id.layer_spinner);

        ArrayAdapter<String> adapterLayer = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            adapterLayer = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, currentMap.getLayerList()
                    .stream().
                            map(Layer::getName).
                            collect(Collectors.toList()));
        }
        else {
            for (Layer layer : currentMap.getLayerList()){
                mapListNames.add(layer.getName());
            }
        }
        dropdownLayer.setAdapter(adapterLayer);

        // to avoid trigerring onItemSelected on init
        dropdownLayer.setSelection(0,false);
        dropdownLayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Layer selectedLayer = currentMap.getLayerList().get(position);
                displayLayer(selectedLayer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // to avoid trigerring onItemSelected on init
        dropdownMap.setSelection(0,false);
        dropdownMap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                currentMap = mapList.get(position);
                ((ArrayAdapter<String>) dropdownLayer.getAdapter()).clear();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ((ArrayAdapter<String>) dropdownLayer.getAdapter()).addAll(currentMap.getLayerList()
                            .stream().
                                    map(Layer::getName).
                                    collect(Collectors.toList()));
                }
                else {
                    for (Layer layer : currentMap.getLayerList()){
                        ((ArrayAdapter<String>) dropdownLayer.getAdapter()).add(layer.getName());
                    }
                }
                dropdownLayer.setSelection(0);
                displayLayer(currentMap.getLayerList().get(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        // to display the layer on launch
        displayLayer(currentMap.getLayerList().get(0));

        battleReportTitleTextView.setOnClickListener(view -> battleReportExpandableLayout.toggle());

        // toggle buttons filters logic
        toggleArmor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showArmor=true;
                for (TimerBlock block : blockArmorList){
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .show(block)
                            .commit();
                }
            } else {
                showArmor=false;
                for (TimerBlock block : blockArmorList){
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .hide(block)
                            .commit();
                }
            }
        });
        toggleTransport.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showTransport=true;
                for (TimerBlock block : blockTransportList){
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .show(block)
                            .commit();
                }
            } else {
                showTransport=false;
                for (TimerBlock block : blockTransportList){
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .hide(block)
                            .commit();
                }
            }
        });

    }

    /** Create new TimerBlock fragment */
    private TimerBlock createTimer(Vehicle vehicle, ViewGroup layout){
        // create block and add to view
        Side side = Side.NONE;
        if (layout.getId() == leftLayout.getId()){side = Side.LEFT;}
        else if (layout.getId() == rightLayout.getId()){side = Side.RIGHT;}
        TimerBlock block = new TimerBlock();
        Bundle args = new Bundle();
        args.putString(TimerBlock.NAME_KEY, vehicle.getName());
        args.putString(TimerBlock.VEHICLE_TYPE_KEY, vehicle.getType().toString());
        args.putInt(TimerBlock.RESPAWN_TIME_KEY, vehicle.getRespawnTime());
        args.putInt(TimerBlock.TICKET_COST_KEY, vehicle.getTicketCost());
        args.putString(TimerBlock.SIDE_KEY, side.toString());
        args.putInt(TimerBlock.DELAY_TIME_KEY, vehicle.getDelayTime());
        args.putBoolean(TimerBlock.EARLY_NOTIFICATION_KEY, false);
        args.putBoolean(TimerBlock.NOTIFICATION_KEY, true);
        block.setArguments(args);
        getFragmentManager().beginTransaction().add(layout.getId(), block).commit();
        if ((armorVehicleTypes.contains(vehicle.getType()) && !showArmor) ||
                (!armorVehicleTypes.contains(vehicle.getType()) && !showTransport)) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(block)
                    .commit();
        }
        return block;
    }

    /** loads layers data (vehicles) from map files (in assets/raw) */
    private void loadDataFromFiles(){
        ObjectMapper mapper = new ObjectMapper();
        mapList = new ArrayList<>();
        // Iterate over the files in data folder to load the maps
        String[] mapFilesToLoad = {};
        try { mapFilesToLoad = getAssets().list(JSON_PATH);
        } catch (IOException e) { e.printStackTrace(); }

        for (String mapFile : mapFilesToLoad){
            Map map = new Map();
            try {
                map = mapper.readValue(getAssets().open(JSON_PATH + "/" + mapFile), Map.class);
            } catch (IOException e) { e.printStackTrace(); }
            mapList.add(map);
        }
    }

    /** refreshes the display of the timers based on the selected layer */
    private void displayLayer(Layer selectedLayer){
        leftFactionTextView.setText(selectedLayer.getFactionLeft().toString());
        rightFactionTextView.setText(selectedLayer.getFactionRight().toString());
        // reset the destroyed vehicles list
        SmallReportFragment fragment = (SmallReportFragment) getSupportFragmentManager().findFragmentById(R.id.small_report_fragment);
        fragment.emptyBattleReportList();

        // reset previous timer blocks lists (used for filters)
        blockArmorList = new ArrayList<>();
        blockTransportList = new ArrayList<>();

        refreshVehicleLayout(leftLayout, selectedLayer.getVehicleListLeft());
        refreshVehicleLayout(rightLayout, selectedLayer.getVehicleListRight());
    }

    private void refreshVehicleLayout(ViewGroup layout, ArrayList<Vehicle> vehicleList){
        TimerBlock currentBlock;
        layout.removeAllViews();
        createRadioTimer(layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vehicleList.sort(new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle vehicle, Vehicle t1) {
                    return vehicle.getType().display_priority - t1.getType().display_priority;
                }
            });
        }
        for (Vehicle vehicle : vehicleList){
            currentBlock = createTimer(vehicle, layout);
            if (armorVehicleTypes.contains(vehicle.getType())) {
                blockArmorList.add(currentBlock); }
            else { blockTransportList.add(currentBlock); }
        }
    }

    /** adds a vehicle to the report of vehicle destroyed */
    public void addToBattleReport(Vehicle vehicle, Side side){
        SmallReportFragment fragment = (SmallReportFragment) getSupportFragmentManager().findFragmentById(R.id.small_report_fragment);
        switch (side) {
            case LEFT:
                fragment.addBattleReportLeftFaction(new BattleReport(Calendar.getInstance().getTime(), vehicle));
                break;
            case RIGHT:
                fragment.addBattleReportRightFaction(new BattleReport(Calendar.getInstance().getTime(), vehicle));
                break;
        }
    }

    private TimerBlock createRadioTimer(ViewGroup layout){
        Vehicle radio = new Vehicle();
        radio.setName(getString(R.string.radio_name));
        radio.setDelayTime(0);
        radio.setRespawnTime(1);
        radio.setTicketCost(20);
        radio.setType(VehicleType.RADIO);
        return createTimer(radio, layout);
    }

}