package com.squadapp.squadvehicletimer;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squadapp.squadvehicletimer.utils.BattleBuddy;
import com.squadapp.squadvehicletimer.utils.BattleReport;
import com.squadapp.squadvehicletimer.utils.Side;
import com.squadapp.squadvehicletimer.utils.VehicleType;
import com.google.android.flexbox.FlexboxLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class SmallReportFragment extends Fragment {
    private BattleBuddy battleBuddy;

    private HashMap<VehicleType, Integer> leftVehicleCounterMap;
    private HashMap<VehicleType, Integer> rightVehicleCounterMap;

    private TextView leftTicketTextview;
    private TextView rightTicketTextview;
    private FlexboxLayout reportLeftLayout;
    private FlexboxLayout reportRightLayout;
    private float iconSizeInPx;
    private TableLayout battleReportLayout;

    DateFormat formatter;

    public SmallReportFragment() {
        // Required empty public constructor
    }

    public static SmallReportFragment newInstance() {
        SmallReportFragment fragment = new SmallReportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        battleBuddy = new BattleBuddy();
        formatter = new SimpleDateFormat("HH:mm:ss");
        float dip = 40f;
        Resources r = getResources();
        iconSizeInPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout fl = (FrameLayout) inflater.inflate(R.layout.fragment_small_report, container, false);

        leftTicketTextview = fl.findViewById(R.id.left_ticket_text);
        rightTicketTextview = fl.findViewById(R.id.right_ticket_text);
        reportLeftLayout = fl.findViewById(R.id.report_left_layout);
        reportRightLayout = fl.findViewById(R.id.report_right_layout);

        return fl;
    }

    public void addBattleReportLeftFaction(BattleReport br){
        battleBuddy.addReportToLeftFaction(br);
        updateFragmentDisplayLeftSide();
    }

    public void addBattleReportRightFaction(BattleReport br){
        battleBuddy.addReportToRightFaction(br);
        updateFragmentDisplayRightSide();
    }

    public void updateFragmentDisplay(){
        updateFragmentDisplayLeftSide();
        updateFragmentDisplayRightSide();
    }

    private void updateFragmentDisplayLeftSide(){
        reportLeftLayout.removeAllViews();
        leftVehicleCounterMap = new HashMap<>();
        countVehicleTypesAndPutInMap(battleBuddy.getLeftFactionBattleReportList(), leftVehicleCounterMap);
        refreshSideDisplay(Side.LEFT);
    }

    private void updateFragmentDisplayRightSide(){
        reportRightLayout.removeAllViews();
        rightVehicleCounterMap = new HashMap<>();
        countVehicleTypesAndPutInMap(battleBuddy.getRightFactionBattleReportList(), rightVehicleCounterMap);
        refreshSideDisplay(Side.RIGHT);
    }

    private void countVehicleTypesAndPutInMap(ArrayList<BattleReport> battleReportList,
                                              HashMap<VehicleType, Integer> vehicleCounterMap){
        for (BattleReport br : battleReportList) {
            if (vehicleCounterMap.containsKey(br.getVehicleDestroyed().getType())) {
                vehicleCounterMap.put(br.getVehicleDestroyed().getType(),
                        vehicleCounterMap.get(br.getVehicleDestroyed().getType()) + 1);
            } else {
                vehicleCounterMap.put(br.getVehicleDestroyed().getType(), 1);
            }
        }
    }

    // TODO find a better way to do it
    public void refreshSideDisplay(Side side){
        FlexboxLayout mainLayout;
        int ticketCounter;
        TextView ticketTextView;
        HashMap<VehicleType, Integer> vehicleCounterMap;
        // set parameters based on the side
        switch (side) {
            case LEFT:
                mainLayout = reportLeftLayout;
                ticketCounter = battleBuddy.getLeftTicketCounter();
                ticketTextView = leftTicketTextview;
                vehicleCounterMap = leftVehicleCounterMap;
                break;
            case RIGHT:
                mainLayout = reportRightLayout;
                ticketCounter = battleBuddy.getRightTicketCounter();
                ticketTextView = rightTicketTextview;
                vehicleCounterMap = rightVehicleCounterMap;
                break;
            default:
                // invalid side parameter
                return;
        }

        // update side display
        ticketTextView.setText(String.format("ticket cost : %d ", ticketCounter));
        for (VehicleType vt :vehicleCounterMap.keySet()){
            LinearLayout ll = new LinearLayout(getView().getContext());
            TextView tv = new TextView(getView().getContext());
            ImageView iv = new ImageView(getView().getContext());

            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setText(String.format("x%d", vehicleCounterMap.get(vt)));
            tv.setLayoutParams(new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            iv.setImageResource(vt.icon);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    Math.round(iconSizeInPx), Math.round(iconSizeInPx));
            iv.setLayoutParams(lp);

            ll.addView(iv);
            ll.addView(tv);
            mainLayout.addView(ll);
        }
    }

    public void emptyBattleReportList(){
        battleBuddy.reset();
        updateFragmentDisplay();
    }
}