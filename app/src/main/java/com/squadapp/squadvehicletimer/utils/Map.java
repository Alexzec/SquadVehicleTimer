package com.squadapp.squadvehicletimer.utils;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Map {
    @JsonProperty("map_name")
    private String name;
    @JsonProperty("layer_list")
    private ArrayList<Layer> layerList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Layer> getLayerList() {
        return layerList;
    }

    public void setLayerList(ArrayList<Layer> layerList) {
        this.layerList = layerList;
    }
}
