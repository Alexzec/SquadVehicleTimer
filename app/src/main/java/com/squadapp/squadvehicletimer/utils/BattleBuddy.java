package com.squadapp.squadvehicletimer.utils;

import java.util.ArrayList;

public class BattleBuddy {
    private int leftTicketCounter;
    private int rightTicketCounter;
    private ArrayList<BattleReport> leftFactionBattleReportList;
    private ArrayList<BattleReport> rightFactionBattleReportList;

    public void reset(){
        leftFactionBattleReportList = new ArrayList<>();
        rightFactionBattleReportList = new ArrayList<>();
        leftTicketCounter = 0;
        rightTicketCounter = 0;
    }

    public void addReportToLeftFaction(BattleReport br){
        leftFactionBattleReportList.add(br);
        leftTicketCounter += br.getVehicleDestroyed().getTicketCost();
    }

    public void addReportToRightFaction(BattleReport br){
        rightFactionBattleReportList.add(br);
        rightTicketCounter += br.getVehicleDestroyed().getTicketCost();
    }

    public ArrayList<BattleReport> getLeftFactionBattleReportList() {
        return leftFactionBattleReportList;
    }

    public ArrayList<BattleReport> getRightFactionBattleReportList() {
        return rightFactionBattleReportList;
    }

    public int getLeftTicketCounter() {
        return leftTicketCounter;
    }

    public void setLeftTicketCounter(int leftTicketCounter) {
        this.leftTicketCounter = leftTicketCounter;
    }

    public int getRightTicketCounter() {
        return rightTicketCounter;
    }

    public void setRightTicketCounter(int rightTicketCounter) {
        this.rightTicketCounter = rightTicketCounter;
    }
}
