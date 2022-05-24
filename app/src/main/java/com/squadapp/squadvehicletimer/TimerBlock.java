package com.squadapp.squadvehicletimer;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squadapp.squadvehicletimer.utils.Side;
import com.squadapp.squadvehicletimer.utils.Vehicle;
import com.squadapp.squadvehicletimer.utils.VehicleType;

import java.util.Locale;

public class TimerBlock extends Fragment {
    public static final String NAME_KEY = "name";
    public static final String VEHICLE_TYPE_KEY = "vehicleType";
    public static final String RESPAWN_TIME_KEY = "respawnTime";
    public static final String TICKET_COST_KEY = "ticketCost";
    public static final String DELAY_TIME_KEY = "delayTime";
    public static final String SIDE_KEY = "side";
    public static final String EARLY_NOTIFICATION_KEY = "earlyNotification";
    public static final String NOTIFICATION_KEY = "notification";
    public static final long ONE_MINUTE_IN_MS = 60000;
    public static final String START_TIMER_TEXT = "Start";
    public static final String RESET_TIMER_TEXT = "Reset";
    public static final String PAUSE_TIMER_TEXT = "Pause";
    public static final String REMOVE_1MN_TIMER_TEXT = "-1Mn";

    private Vehicle vehicle;
    private Side side;
    private long timeRemainingInMs; // time left on the counter in milliseconds
    private boolean timerRunning;
    private boolean earlyNotification;
    private boolean notification;
    private boolean timerStarted = false;
    private boolean timerFinished = false;
    private CountDownTimer countDownTimer;

    private LinearLayout layout;
    private RelativeLayout textAndImageLayout;
    private LinearLayout imagesLayout;
    private LinearLayout textAndCounterLayout;
    private LinearLayout buttonLayout;
    private TextView textViewName;
    private TextView textViewCounter;
    private ImageView imageViewIcon;
    private ImageView delayImageViewIcon;
    private Button buttonStartPause;
    private Button buttonReset;
    private Button buttonRemoveOneMn;

    float vehicleIconSizeInPx;
    float delayIconSizeInPx;

    private Context context;
    private Vibrator v;

    public static TimerBlock newInstance() {
        Bundle args = new Bundle();
        args.putString(NAME_KEY, "default_name");
        args.putString(VEHICLE_TYPE_KEY, "CAR");
        args.putInt(RESPAWN_TIME_KEY, 1);
        args.putInt(DELAY_TIME_KEY, 0);
        args.putBoolean(EARLY_NOTIFICATION_KEY, false);
        args.putBoolean(NOTIFICATION_KEY, true);

        TimerBlock fragment = new TimerBlock();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vehicle=new Vehicle();
        this.vehicle.setName(getArguments().getString(NAME_KEY));
        this.vehicle.setType(VehicleType.valueOf(getArguments().getString(VEHICLE_TYPE_KEY)));
        this.vehicle.setRespawnTime(getArguments().getInt(RESPAWN_TIME_KEY));
        this.vehicle.setTicketCost(getArguments().getInt(TICKET_COST_KEY));
        this.side = Side.valueOf(getArguments().getString(SIDE_KEY));
        this.vehicle.setDelayTime(getArguments().getInt(DELAY_TIME_KEY));
        this.earlyNotification=getArguments().getBoolean(EARLY_NOTIFICATION_KEY);
        this.notification=getArguments().getBoolean(NOTIFICATION_KEY);
        this.timeRemainingInMs=vehicle.getRespawnTime()*60*1000;

        // Converts image size into px from dp for vehicle icons
        this.vehicleIconSizeInPx = convertDpInPx(40f);
        // Converts image size into px from dp for delay icon
        this.delayIconSizeInPx = convertDpInPx(30f);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.context = container.getContext();
        this.v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        return createTimerBlockLayout();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        layout = null;
    }

    // TODO make the layout in a layout.xml instead of code
    /** used to create the main layout */
    private View createTimerBlockLayout(){
        // layout that will contain the rest of the elements
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
               LinearLayout.LayoutParams.WRAP_CONTENT,
               LinearLayout.LayoutParams.WRAP_CONTENT));

        // layout with vehicle name, icon and timer
        layout.addView(createVehicleNameIconAndTimerSublayout());

        // horizontal layout for the buttons
        layout.addView(createButtonSublayout());

        return layout;
    }

    private RelativeLayout createVehicleNameIconAndTimerSublayout(){
        textAndImageLayout = new RelativeLayout(context);
        textAndImageLayout.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        textAndImageLayout.addView(createVehicleNameAndCounterSublayout());
        textAndImageLayout.addView(createVehicleImagesSublayout());

        return textAndImageLayout;
    }

    private LinearLayout createVehicleNameAndCounterSublayout(){
        textAndCounterLayout = new LinearLayout(context);
        textAndCounterLayout.setOrientation(LinearLayout.VERTICAL);
        textAndCounterLayout.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Textview to display name of the counter
        textViewName = new TextView(context);
        textViewName.setText(this.vehicle.getName());
        textViewName.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        //layout.addView(textViewName);
        textAndCounterLayout.addView(textViewName);

        // TextView to display counter
        textViewCounter = new TextView(context);
        if(vehicle.getDelayTime()>0){
            textViewCounter.setText(
                    String.format("%s (%s)",
                            String.format(Locale.getDefault(), "%02d:00", vehicle.getRespawnTime()),
                            String.format(Locale.getDefault(), "%02d:00", vehicle.getDelayTime())));
        } else {
            textViewCounter.setText(String.format(Locale.getDefault(), "%02d:00", vehicle.getRespawnTime()));
        }
        textViewCounter.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textAndCounterLayout.addView(textViewCounter);

        return textAndCounterLayout;
    }

    private LinearLayout createVehicleImagesSublayout(){
        imagesLayout = new LinearLayout(context);
        imagesLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        imagesLayout.setLayoutParams(lp);
        imagesLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);

        // ImageView to display the vehicle icon
        imageViewIcon = new ImageView(context);
        imageViewIcon.setImageResource(vehicle.getType().icon);
        imageViewIcon.setOnClickListener(v -> {
            if(!timerStarted){
                startTimer();
                ((MainActivity)getActivity()).addToBattleReport(vehicle, side);
            } if (timerFinished){resetTimer();}
        });
        LinearLayout.LayoutParams viewParam = new LinearLayout.LayoutParams(
                Math.round(vehicleIconSizeInPx), Math.round(vehicleIconSizeInPx),1.0f);
        imageViewIcon.setLayoutParams(viewParam);

        // ImageView to display the vehicle icon
        if(vehicle.getDelayTime()>0) {
            delayImageViewIcon = new ImageView(context);
            delayImageViewIcon.setImageResource(R.drawable.ic_timer_icon);
            delayImageViewIcon.setColorFilter(0xff585858);
            delayImageViewIcon.setOnClickListener(v -> {
                if (!timerStarted) {
                    startDelayTimer();
                }
                if (timerFinished) {
                    resetTimer();
                }
            });
            LinearLayout.LayoutParams delayViewParam = new LinearLayout.LayoutParams(
                    Math.round(delayIconSizeInPx), Math.round(delayIconSizeInPx),1.0f);
            delayImageViewIcon.setLayoutParams(delayViewParam);
            imagesLayout.addView(delayImageViewIcon);
        }

        imagesLayout.addView(imageViewIcon);

        return imagesLayout;
    }

    private LinearLayout createButtonSublayout(){
        buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // create buttons to start/stop/reset timer
        buttonStartPause = new Button(context);
        buttonStartPause.setText(START_TIMER_TEXT);
        buttonStartPause.setSingleLine(true);
        buttonStartPause.setOnClickListener(v -> {
            if (timerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        buttonStartPause.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f));
        buttonLayout.addView(buttonStartPause);

        buttonReset = new Button(context);
        buttonReset.setText(RESET_TIMER_TEXT);
        buttonReset.setSingleLine(true);
        buttonReset.setOnClickListener(v -> resetTimer());

        buttonReset.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f));
        buttonReset.setVisibility(View.INVISIBLE);
        buttonLayout.addView(buttonReset);

        buttonRemoveOneMn = new Button(context);
        buttonRemoveOneMn.setText(REMOVE_1MN_TIMER_TEXT);
        buttonRemoveOneMn.setSingleLine(true);
        buttonRemoveOneMn.setOnClickListener(v -> removeOneMnFromCounter());

        buttonRemoveOneMn.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f));
        buttonLayout.addView(buttonRemoveOneMn);

        return buttonLayout;
    }

    private void startDelayTimer() {
        // TODO change the vehicle icon to one representing the delay (instead of destruction)
        timeRemainingInMs = vehicle.getDelayTime()*60*1000;
        startTimer();
    }

    public void startTimer(){
        countDownTimer = createNewTimer(timeRemainingInMs).start();
        timerRunning = true;
        timerStarted = true;
        buttonStartPause.setText(PAUSE_TIMER_TEXT);
        buttonReset.setVisibility(View.INVISIBLE);
        imageViewIcon.setImageResource(vehicle.getType().destroyed_icon);
    }

    private CountDownTimer createNewTimer(long time){
        return new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainingInMs = millisUntilFinished;
                update();
            }
            @Override
            public void onFinish() {
                timerRunning = false;
                timerFinished = true;
                buttonRemoveOneMn.setVisibility(View.INVISIBLE);
                buttonStartPause.setText(START_TIMER_TEXT);
                buttonStartPause.setVisibility(View.INVISIBLE);
                buttonReset.setVisibility(View.VISIBLE);
                if(notification) {
                    // Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(500);
                    }
                }
                imageViewIcon.setImageResource(vehicle.getType().ok_icon);
            }
        };
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartPause.setText(START_TIMER_TEXT);
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        timerStarted = false;
        timerFinished = false;
        timeRemainingInMs = vehicle.getRespawnTime()*60*1000;
        buttonRemoveOneMn.setVisibility(View.VISIBLE);
        update();
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStartPause.setVisibility(View.VISIBLE);
        imageViewIcon.setImageResource(vehicle.getType().icon);
        if(vehicle.getDelayTime()>0){
            textViewCounter.setText(
                    String.format("%s (%s)",
                            String.format(Locale.getDefault(), "%02d:00",
                                    vehicle.getRespawnTime()),
                            String.format(Locale.getDefault(), "%02d:00", vehicle.getDelayTime())));
        } else {
            textViewCounter.setText(String.format(Locale.getDefault(), "%02d:00", vehicle.getRespawnTime()));
        }
    }

    public void update(){
        int minutes = (int) (timeRemainingInMs / 1000) / 60;
        int seconds = (int) (timeRemainingInMs / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCounter.setText(timeLeftFormatted);
        if(timeRemainingInMs<ONE_MINUTE_IN_MS){buttonRemoveOneMn.setVisibility(View.INVISIBLE);}
    }

    public void removeOneMnFromCounter(){
        if (timerRunning) {countDownTimer.cancel();}
        timeRemainingInMs -= ONE_MINUTE_IN_MS;
        countDownTimer = createNewTimer(timeRemainingInMs);
        if (timerRunning){countDownTimer.start();}
        else {buttonReset.setVisibility(View.VISIBLE);}
        update();
    }

    private float convertDpInPx(float dp){
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public ViewGroup getLayout() {
        return layout;
    }
}