package com.mpvreeken.rpgcompanion.InitiativeTracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;

public class InitiativeTrackerActivity extends RPGCActivity {

    private Spinner campaign_spinner;
    private EditText new_name_et, new_init_et;
    private Button quick_add_btn, add_to_campaign_btn;
    private LinearLayout init_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiative_tracker);

        campaign_spinner = findViewById(R.id.init_campaign_spinner);
        new_name_et = findViewById(R.id.init_new_name_et);
        new_init_et = findViewById(R.id.init_new_init_et);
        quick_add_btn = findViewById(R.id.init_quick_add_btn);
        add_to_campaign_btn = findViewById(R.id.init_add_to_campaign_btn);
        init_ll = findViewById(R.id.init_ll);

    }
}
