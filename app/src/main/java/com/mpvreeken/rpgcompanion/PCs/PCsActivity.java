package com.mpvreeken.rpgcompanion.PCs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mpvreeken.rpgcompanion.Campaigns.Campaign;
import com.mpvreeken.rpgcompanion.PCs.PC.PC;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;

import java.util.ArrayList;
import java.util.List;

public class PCsActivity extends RPGCActivity {

    private Spinner campaign_spinner;
    private ArrayAdapter<String> campaignAdapter;

    private ListView pcs_lv;
    private List<PC> pcs;
    private PCArrayAdapter pcArrayAdapter;

    private Button new_camp_popup_create_btn, new_camp_popup_cancel_btn;
    private EditText new_camp_popup_name_et;
    private List<Campaign> campaigns;
    private ConstraintLayout new_camp_popup;
    private TextView new_camp_popup_title_tv;

    private static final String CREATE_NEW_CAMPAIGN_TEXT = "Create New Campaign";
    private static final String CREATE_NEW_PC_TEXT = "Create New PC";

    private int currentCampaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcs);

        campaign_spinner = findViewById(R.id.pcs_campaigns_spinner);
        new_camp_popup_title_tv = findViewById(R.id.pcs_new_campaign_popup_title_et);

        new_camp_popup_name_et = findViewById(R.id.pcs_new_campaign_popup_name_et);
        new_camp_popup_cancel_btn = findViewById(R.id.pcs_new_campaign_popup_cancel_btn);
        new_camp_popup_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNewCampaignPopup();
            }
        });
        new_camp_popup_create_btn = findViewById(R.id.pcs_new_campaign_popup_create_btn);
        new_camp_popup_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewCampaign();
            }
        });
        new_camp_popup = findViewById(R.id.pcs_new_campaign_popup);

        pcs_lv = findViewById(R.id.pcs_listview);

        Button btn = new Button(this);
        btn.setText("NEW PC");
        pcs_lv.addFooterView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPC();
            }
        });

        pcArrayAdapter = new PCArrayAdapter(this, pcs);

        currentCampaign=0;

        getCampaigns();
    }

    private void getCampaigns() {
        campaigns = Campaign.getCampaigns(this);

        final List<String> campNames = new ArrayList<>();
        for (int i=0; i<campaigns.size(); i++) {
            campNames.add(campaigns.get(i).name);
        }
        campNames.add(CREATE_NEW_CAMPAIGN_TEXT);

        campaignAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, campNames);
        campaignAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campaign_spinner.setAdapter(campaignAdapter);

        campaign_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("onItemSelected", i+"");
                if (i==campaigns.size()) {
                    showNewCampaignPopup();
                }
                else {
                    changeCampaign(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void showNewCampaignPopup() {
        if (campaigns.size()==0) {
            new_camp_popup_title_tv.setText("Create a campaign to organize your PCs");
        }
        else {
            new_camp_popup_title_tv.setText("Create A New Campaign");
        }
        new_camp_popup_name_et.setText("");
        new_camp_popup.setVisibility(View.VISIBLE);
    }

    private void cancelNewCampaignPopup() {
        if (campaigns.size()==0) {
            Toast.makeText(application, "You must have at least one campaign before adding a PC", Toast.LENGTH_LONG).show();
            return;
        }
        hideNewCampaignPopup();

        campaign_spinner.setSelection(currentCampaign);
    }

    private void hideNewCampaignPopup() {
        new_camp_popup.setVisibility(View.GONE);
        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(new_camp_popup_name_et.getWindowToken(), 0);
    }

    private void changeCampaign(int i) {
        currentCampaign=i;

        loadPCs();
    }

    private void saveNewCampaign() {
        String name = new_camp_popup_name_et.getText().toString();

        campaigns = Campaign.saveNewCampaign(this, name);

        campaignAdapter.remove(CREATE_NEW_CAMPAIGN_TEXT);
        campaignAdapter.add(name);
        campaignAdapter.add(CREATE_NEW_CAMPAIGN_TEXT);

        campaignAdapter.notifyDataSetChanged();
        campaign_spinner.setSelection(campaigns.size()-1);
        currentCampaign=campaigns.size()-1;

        hideNewCampaignPopup();
    }


    private void loadPCs() {
        pcs = PC.getPCs(this, currentCampaign);

        List<String> pcNames = new ArrayList<>();

        for (int i=0; i<pcs.size(); i++) {
            pcNames.add(pcs.get(i).name);
        }

        pcNames.add(CREATE_NEW_PC_TEXT);

        pcArrayAdapter = new PCArrayAdapter(this, pcs);
        pcArrayAdapter.notifyDataSetChanged();

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pcNames);

        pcs_lv.setAdapter(pcArrayAdapter);

        pcs_lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("onPCSelected", i+"");
                if (i==pcs.size()) {
                    showNewPC();
                }
                else {
                    //Display PC
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void createNewPC() {
        showNewPC();
    }

    private void showNewPC() {
        Log.e("PCsActivity", "ShowNewPC)");
        Intent intent = new Intent(getBaseContext(), NewPCActivity.class);
        startActivity(intent);
    }
}
