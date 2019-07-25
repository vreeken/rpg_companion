package com.mpvreeken.rpgcompanion.Puzzles;

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

public class PuzzlesActivity extends RPGCActivity {

    private ArrayList<Puzzle> puzzlesArray = new ArrayList<>();
    private PuzzleArrayAdapter puzzleArrayAdapter;
    private ListView puzzles_lv;
    private JSONArray jsonArray;

    private Spinner sort_spinner, filter_spinner;
    private static final String[] SORT_METHODS = {"r", "uv", "dv", "dd", "da"};

    private String seed; //random seed for server
    private int page; //pagination for server to get next set of maps

    private Boolean firstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        setupLoadingAnimTransparent();

        page = 0;
        Random rand = new Random();
        seed = String.valueOf(rand.nextInt(1000000));

        Button new_btn = findViewById(R.id.puzzles_new_btn);

        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!application.getLoggedIn()) {
                    Toast.makeText(application, "You must be logged in to post a new Puzzle", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(context, NewPuzzleActivity.class);
                startActivity(intent);
            }
        });

        //Fetch puzzles from db
        this.puzzlesArray = new ArrayList<>();
        this.puzzleArrayAdapter = new PuzzleArrayAdapter(this, puzzlesArray);
        this.puzzles_lv = findViewById(R.id.puzzles_lv);

        setupUI();
    }

    public void setupUI() {
        sort_spinner = findViewById(R.id.puzzles_sort_spinner);
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

        filter_spinner = findViewById(R.id.puzzles_filter_spinner);
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

        puzzles_lv.setAdapter(puzzleArrayAdapter);
        Button btn = new Button(this);
        btn.setText(R.string.lbl_load_more);

        puzzles_lv.addFooterView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMorePuzzles();
            }
        });

        loadMorePuzzles();
    }

    private void resetAndLoad() {
        page=0;
        firstLoad=true;
        puzzleArrayAdapter.clear();
        puzzleArrayAdapter.notifyDataSetChanged();
        loadMorePuzzles();
    }

    public void loadMorePuzzles() {
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
                .url(getResources().getString(R.string.url_get_puzzles))
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

                    PuzzlesActivity.this.runOnUiThread(new Runnable() {
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
                    displayError("No more saved puzzles");
                }
                else {
                    displayError("No more puzzles. Help everyone by submitting new puzzles.");
                }
                return;
            }

            for (int i=0; i<jsonArray.length(); i++) {
                puzzlesArray.add(new Puzzle(context, puzzlesArray.size(), jsonArray.getJSONObject(i)));
            }

            //Increment page value for server
            page+=1;


            if (firstLoad) {
                firstLoad=false;
                //Set the new data
                puzzles_lv.setAdapter(puzzleArrayAdapter);
            }
            else {
                //Get scroll position of listview, so we can retain their position after loading more
                int firstVisibleItem = puzzles_lv.getFirstVisiblePosition();
                int oldCount = puzzleArrayAdapter.getCount();
                View view = puzzles_lv.getChildAt(0);
                int pos = (view == null ? 0 : view.getBottom());

                //Set the new data
                puzzles_lv.setAdapter(puzzleArrayAdapter);

                //Set the listview position back to where they were
                puzzles_lv.setSelectionFromTop(firstVisibleItem + puzzleArrayAdapter.getCount() - oldCount + 1, pos);
            }
        }
        catch (Exception e) {
            displayError("An unknown error occurred. Please try again");
            e.printStackTrace();
        }
        notifyChange();
    }

    private void notifyChange() {
        PuzzlesActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (puzzleArrayAdapter != null) {
                    puzzleArrayAdapter.notifyDataSetChanged();
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
                            SerialPuzzle serialPuzzle = (SerialPuzzle) data.getSerializableExtra("SERIALIZED_OBJ");
                            if (puzzlesArray != null && serialPuzzle != null) {
                                puzzlesArray.remove(serialPuzzle.position);
                            }
                        }
                        else {
                            SerialPuzzle serialPuzzle = (SerialPuzzle) data.getSerializableExtra("SERIALIZED_OBJ");
                            if (puzzlesArray != null && serialPuzzle != null) {
                                puzzlesArray.get(serialPuzzle.position).updateLocal(serialPuzzle);
                            }
                        }
                    }
                    catch (Exception e) { Log.e("PuzzlesActivity", e.getMessage()); }

                    notifyChange();
                }
                break;
            }
        }
    }
}
