package com.mpvreeken.rpgcompanion.Riddles;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.mpvreeken.rpgcompanion.Classes.PostObjectBase;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;

import org.json.JSONArray;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisplayRiddleActivity extends RPGCActivity {

    private ArrayList<RiddleComment> commentsArray = new ArrayList<>();
    private RiddleCommentsArrayAdapter commentArrayAdapter;
    private LinearLayout comments_layout;
    private Riddle riddle;
    private ImageButton upvote_btn, downvote_btn;
    private ImageView bookmark_iv;
    private TextView votes_tv;
    private SerialRiddle serialized;
    private TextView riddle_tv, answer_tv, user_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_riddle);
        setupLoadingAnim();

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        assert bundle != null;

        if (bundle.containsKey("SERIALIZED_OBJ")) {
            serialized = (SerialRiddle) bundle.getSerializable("SERIALIZED_OBJ");
        }
        else if (bundle.containsKey("POST_ID")) {
            serialized = new SerialRiddle(bundle.getInt("POST_ID"));
        }
        else {
            Toast.makeText(application, "An unknown error has occurred. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        upvote_btn = findViewById(R.id.riddle_details_vote_up_btn);
        upvote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upvote_btn.setImageResource(R.mipmap.arrow_upvote);
                downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                riddle.upvote();
            }
        });
        downvote_btn = findViewById(R.id.riddle_details_vote_down_btn);
        downvote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downvote_btn.setImageResource(R.mipmap.arrow_downvote);
                upvote_btn.setImageResource(R.mipmap.arrow_neutral);
                riddle.downvote();
            }
        });

        //Fetch riddles from db
        this.commentsArray = new ArrayList<>();
        this.commentArrayAdapter = new RiddleCommentsArrayAdapter(this, commentsArray);
        //this.comments_lv = findViewById(R.id.riddle_comments_lv);

        showLoadingAnim();

        this.comments_layout = findViewById(R.id.riddle_comments_linear_layout);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;
        requestBody = new FormBody.Builder()
                .add("id", String.valueOf(serialized.id))
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_get_riddle))
                .header("Authorization", "Bearer" + application.getToken())
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                hideLoadingAnim();
                displayError("Could not connect to server. Please try again");
                finish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                hideLoadingAnim();
                if (!response.isSuccessful()) {
                    onUnsuccessfulResponse(response);
                    finish();
                }
                else {
                    try {
                        JSONObject all = new JSONObject(response.body().string());

                        JSONObject h = all.getJSONObject("post");
                        riddle = new Riddle(context, serialized.position, h);


                        //We can't update the UI on a background thread, so run on the UI thread
                        DisplayRiddleActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setupUI();
                            }
                        });
                    }
                    catch (Exception e) {
                        displayError("An unknown error occurred. Please try again");
                        Log.e("DisplayRiddleActivity", e.getMessage());
                        e.printStackTrace();
                        finish();
                    }
                }
            }
        });
    }

    public void setupUI() {
        votes_tv = findViewById(R.id.riddle_details_votes_tv);
        votes_tv.setText(String.valueOf(riddle.getCalculatedVotes()));
        riddle_tv = findViewById(R.id.riddle_details_riddle_tv);
        riddle_tv.setText(String.valueOf(riddle.getRiddle()));
        answer_tv = findViewById(R.id.riddle_details_answer_tv);
        answer_tv.setText(String.valueOf(riddle.getAnswer()));
        user_tv = findViewById(R.id.riddle_details_user_tv);
        user_tv.setText(riddle.getDetailSubtitle());

        if (riddle.getVoted() == 1) {
            upvote_btn.setImageResource(R.mipmap.arrow_upvote);
        }
        else if (riddle.getVoted() == 0) {
            downvote_btn.setImageResource(R.mipmap.arrow_downvote);
        }

        if (riddle.isMine()) {
            ImageView edit_iv = findViewById(R.id.riddle_details_edit_iv);
            edit_iv.setVisibility(View.VISIBLE);
            edit_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditRiddleActivity.riddle=riddle;
                    Intent intent = new Intent(context, EditRiddleActivity.class);
                    startActivityForResult(intent, 1);
                }
            });
        }
        else {
            bookmark_iv = findViewById(R.id.riddle_details_bookmark_iv);
            resetBookmark();
            bookmark_iv.setVisibility(View.VISIBLE);
            bookmark_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (riddle.isBookmarked()) {
                        bookmark_iv.setImageResource(R.mipmap.ic_bookmark);
                        riddle.bookmark(false);
                    }
                    else {
                        bookmark_iv.setImageResource(R.mipmap.ic_bookmarked);
                        riddle.bookmark(true);
                    }
                }
            });
        }

        //comments_lv.setAdapter(commentArrayAdapter);

        for (int i=0; i<commentsArray.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.comment_layout, null);
            TextView comment_tv = view.findViewById(R.id.comment_layout_comment_tv);


            comment_tv.setText(commentsArray.get(i).getComment());
            comments_layout.addView(view);
        }

        riddle.setVoteEventListener(new PostObjectBase.VoteEventListener() {
            @Override
            public void onVoteFail(final String msg) {
                DisplayRiddleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!msg.isEmpty()) {
                            Toast.makeText(application, msg, Toast.LENGTH_SHORT).show();
                        }
                        resetButtons();
                    }
                });
            }

            @Override
            public void onVoteSuccess() {
                DisplayRiddleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        votes_tv.setText(String.valueOf(riddle.getCalculatedVotes()));
                    }
                });
                setOnResult();
            }

            @Override
            public void onBookmarkFail(final String msg) {
                DisplayRiddleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bookmark_iv.setImageResource(R.mipmap.ic_bookmarked);
                        resetBookmark();
                        if (!msg.isEmpty()) {
                            Toast.makeText(application, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void resetBookmark() {
        if (riddle.isBookmarked()) {
            bookmark_iv.setImageResource(R.mipmap.ic_bookmarked);
        }
        else {
            bookmark_iv.setImageResource(R.mipmap.ic_bookmark);
        }
    }

    private void setOnResult() {
        if (serialized.position!=-1) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("SERIALIZED_OBJ", riddle.getSerialized());
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    private void resetButtons() {
        DisplayRiddleActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (riddle.getVoted() == 0) {
                    upvote_btn.setImageResource(R.mipmap.arrow_neutral);
                    downvote_btn.setImageResource(R.mipmap.arrow_downvote);
                } else if (riddle.getVoted() == 1) {
                    upvote_btn.setImageResource(R.mipmap.arrow_upvote);
                    downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                } else {
                    upvote_btn.setImageResource(R.mipmap.arrow_neutral);
                    downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                }
            }
        });
    }

    private void updateAfterEdit() {
        riddle_tv.setText(riddle.getRiddle());
        answer_tv.setText(riddle.getAnswer());
        user_tv.setText(riddle.getDetailSubtitle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.hasExtra("DELETED")) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("DELETED", true);
                        resultIntent.putExtra("SERIALIZED_OBJ", riddle.getSerialized());
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                    else {
                        updateAfterEdit();
                        setOnResult();
                    }
                }
                break;
            }
        }
    }

}
