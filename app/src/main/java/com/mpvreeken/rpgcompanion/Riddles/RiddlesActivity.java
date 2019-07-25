package com.mpvreeken.rpgcompanion.Riddles;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RiddlesActivity extends RPGCActivity {

    private ArrayList<Riddle> riddlesArray = new ArrayList<>();
    private RiddleArrayAdapter riddleArrayAdapter;
    private ListView riddles_lv;
    private JSONArray jsonArray;

    private Spinner sort_spinner, filter_spinner;
    private static final String[] SORT_METHODS = {"r", "uv", "dv", "dd", "da"};

    private String seed; //random seed for server
    private int page; //pagination for server to get next set of maps

    private Boolean firstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddles);

        setupLoadingAnimTransparent();

        page = 0;
        Random rand = new Random();
        seed = String.valueOf(rand.nextInt(1000000));

        Button new_btn = findViewById(R.id.riddles_new_btn);

        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!application.getLoggedIn()) {
                    Toast.makeText(application, "You must be logged in to post a new Riddle", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(context, NewRiddleActivity.class);
                startActivity(intent);
            }
        });

        //Fetch riddles from db
        this.riddlesArray = new ArrayList<>();
        this.riddleArrayAdapter = new RiddleArrayAdapter(this, riddlesArray);
        this.riddles_lv = findViewById(R.id.riddles_lv);

        setupUI();
    }

    public void setupUI() {
        sort_spinner = findViewById(R.id.riddles_sort_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort_spinner.setAdapter(adapter);
        sort_spinner.setSelected(false);
        sort_spinner.setSelection(0,true);
        sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    Random rand = new Random();
                    seed = String.valueOf(rand.nextInt(1000000));
                }
                resetAndLoad();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        filter_spinner = findViewById(R.id.riddles_filter_spinner);
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(this,
                R.array.filter_array, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_spinner.setAdapter(filterAdapter);
        filter_spinner.setSelected(false);
        filter_spinner.setSelection(0,true);
        filter_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                resetAndLoad();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (application.getLoggedIn()) {
            filter_spinner.setVisibility(View.VISIBLE);
        }

        riddles_lv.setAdapter(riddleArrayAdapter);
        Button btn = new Button(this);
        btn.setText(R.string.lbl_load_more);

        riddles_lv.addFooterView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMoreRiddles();
            }
        });

        loadMoreRiddles();
    }

    private void resetAndLoad() {
        page=0;
        firstLoad=true;
        riddleArrayAdapter.clear();
        riddleArrayAdapter.notifyDataSetChanged();
        loadMoreRiddles();
    }

    public void loadMoreRiddles() {
        showLoadingAnim();

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;

        String filter = application.getLoggedIn() ? String.valueOf(filter_spinner.getSelectedItemPosition()) : "0";

        int i = sort_spinner.getSelectedItemPosition();
        if (i==0) {
            //Random
            requestBody = new FormBody.Builder()
                    .add("method", SORT_METHODS[0])
                    .add("seed", seed)
                    .add("page", String.valueOf(page))
                    .add("filter", filter)
                    .build();
        }
        else {
            requestBody = new FormBody.Builder()
                    .add("method", SORT_METHODS[i])
                    .add("page", String.valueOf(page))
                    .add("filter", filter)
                    .build();
        }

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_get_riddles))
                .header("Authorization", "Bearer" + application.getToken())
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                hideLoadingAnim();
                displayError("Could not connect to server. Please try again");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                hideLoadingAnim();
                if (!response.isSuccessful()) {
                    onUnsuccessfulResponse(response);
                }
                else {
                    try {
                        jsonArray = new JSONObject(response.body().string()).getJSONArray("posts");
                    }
                    catch (Exception e) {
                        displayError("An unknown error occurred. Please try again");
                        e.printStackTrace();
                        return;
                    }

                    RiddlesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateArray();
                        }
                    });
                }
            }
        });
    }

    private void updateArray() {
        try {
            if (jsonArray == null) {
                displayError("An unknown error occurred. Please try again");
                return;
            }
            if (jsonArray.length()==0) {
                if (filter_spinner.getSelectedItemPosition()==3) {
                    displayError("No more saved riddles");
                }
                else {
                    displayError("No more riddles. Help everyone by submitting new riddles.");
                }
                return;
            }

            for (int i=0; i<jsonArray.length(); i++) {
                riddlesArray.add(new Riddle(context, riddlesArray.size(), jsonArray.getJSONObject(i)));
            }

            //Increment page value for server
            page+=1;


            if (firstLoad) {
                firstLoad=false;
                //Set the new data
                riddles_lv.setAdapter(riddleArrayAdapter);
            }
            else {
                //Get scroll position of listview, so we can retain their position after loading more
                int firstVisibleItem = riddles_lv.getFirstVisiblePosition();
                int oldCount = riddleArrayAdapter.getCount();
                View view = riddles_lv.getChildAt(0);
                int pos = (view == null ? 0 : view.getBottom());

                //Set the new data
                riddles_lv.setAdapter(riddleArrayAdapter);

                //Set the listview position back to where they were
                riddles_lv.setSelectionFromTop(firstVisibleItem + riddleArrayAdapter.getCount() - oldCount + 1, pos);
            }
        }
        catch (Exception e) {
            displayError("An unknown error occurred. Please try again");
            e.printStackTrace();
        }
        notifyChange();
    }

    private void notifyChange() {
        RiddlesActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (riddleArrayAdapter != null) {
                    riddleArrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyChange();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data.hasExtra("DELETED")) {
                            SerialRiddle serialRiddle = (SerialRiddle) data.getSerializableExtra("SERIALIZED_OBJ");
                            if (riddlesArray != null && serialRiddle != null) {
                                riddlesArray.remove(serialRiddle.position);
                            }
                        }
                        else {
                            SerialRiddle serialRiddle = (SerialRiddle) data.getSerializableExtra("SERIALIZED_OBJ");
                            if (riddlesArray != null && serialRiddle != null) {
                                riddlesArray.get(serialRiddle.position).updateLocal(serialRiddle);
                            }
                        }
                    }
                    catch (Exception e) { Log.e("RiddlesActivity", e.getMessage()); }

                    notifyChange();
                }
                break;
            }
        }
    }
}
