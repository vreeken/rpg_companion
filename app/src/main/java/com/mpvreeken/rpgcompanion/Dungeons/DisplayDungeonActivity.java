package com.mpvreeken.rpgcompanion.Dungeons;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mpvreeken.rpgcompanion.Classes.PostObjectBase;
import com.mpvreeken.rpgcompanion.ExternalLinkAlertActivity;
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

public class DisplayDungeonActivity extends RPGCActivity {

    private ArrayList<DungeonComment> commentsArray = new ArrayList<>();
    private DungeonCommentsArrayAdapter commentArrayAdapter;
    private LinearLayout comments_layout;
    private Dungeon dungeon;
    private ImageButton upvote_btn, downvote_btn;
    private ImageView bookmark_iv;
    private TextView votes_tv;
    private SerialDungeon serialized;
    private Button external_btn;
    private ImageView img;
    private TextView title_tv, description_tv, user_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_dungeon);
        setupLoadingAnim();

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        assert bundle != null;

        if (bundle.containsKey("SERIALIZED_OBJ")) {
            serialized = (SerialDungeon) bundle.getSerializable("SERIALIZED_OBJ");
        }
        else if (bundle.containsKey("POST_ID")) {
            serialized = new SerialDungeon(bundle.getInt("POST_ID"));
        }
        else {
            Toast.makeText(application, "An unknown error has occurred. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        upvote_btn = findViewById(R.id.dungeon_details_vote_up_btn);
        upvote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upvote_btn.setImageResource(R.mipmap.arrow_upvote);
                downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                dungeon.upvote();
            }
        });
        downvote_btn = findViewById(R.id.dungeon_details_vote_down_btn);
        downvote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downvote_btn.setImageResource(R.mipmap.arrow_downvote);
                upvote_btn.setImageResource(R.mipmap.arrow_neutral);
                dungeon.downvote();
            }
        });

        //Fetch dungeons from db
        this.commentsArray = new ArrayList<>();
        this.commentArrayAdapter = new DungeonCommentsArrayAdapter(this, commentsArray);
        //this.comments_lv = findViewById(R.id.dungeon_comments_lv);

        showLoadingAnim();

        this.comments_layout = findViewById(R.id.dungeon_comments_linear_layout);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;
        requestBody = new FormBody.Builder()
                .add("id", String.valueOf(serialized.id))
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_get_dungeon))
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
                        JSONObject all = new JSONObject(response.body().string());

                        JSONObject h = all.getJSONObject("post");
                        dungeon = new Dungeon(context, serialized.position, h);

                        //We can't update the UI on a background thread, so run on the UI thread
                        DisplayDungeonActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setupUI();
                            }
                        });
                    }
                    catch (Exception e) {
                        displayError("An unknown error occurred. Please try again");
                        Log.e("DisplayFRDActivity", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setupUI() {
        votes_tv = findViewById(R.id.dungeon_details_votes_tv);
        votes_tv.setText(String.valueOf(dungeon.getCalculatedVotes()));
        title_tv = findViewById(R.id.dungeon_details_title_tv);
        title_tv.setText(dungeon.getTitle());
        description_tv = findViewById(R.id.dungeon_details_description_tv);
        description_tv.setText(dungeon.getDescription());
        user_tv = findViewById(R.id.dungeon_details_user_tv);
        user_tv.setText(dungeon.getDetailSubtitle());

        if (dungeon.getVoted() == 1) {
            upvote_btn.setImageResource(R.mipmap.arrow_upvote);
        }
        else if (dungeon.getVoted() == 0) {
            downvote_btn.setImageResource(R.mipmap.arrow_downvote);
        }

        if (dungeon.isMine()) {
            ImageView edit_iv = findViewById(R.id.dungeon_details_edit_iv);
            edit_iv.setVisibility(View.VISIBLE);
            edit_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditDungeonActivity.dungeon=dungeon;
                    Intent intent = new Intent(context, EditDungeonActivity.class);
                    startActivityForResult(intent, 1);
                }
            });
        }
        else {
            bookmark_iv = findViewById(R.id.dungeon_details_bookmark_iv);
            resetBookmark();
            bookmark_iv.setVisibility(View.VISIBLE);
            bookmark_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dungeon.isBookmarked()) {
                        bookmark_iv.setImageResource(R.mipmap.ic_bookmark);
                        dungeon.bookmark(false);
                    }
                    else {
                        bookmark_iv.setImageResource(R.mipmap.ic_bookmarked);
                        dungeon.bookmark(true);
                    }
                }
            });
        }

        external_btn = findViewById(R.id.dungeon_details_external_btn);
        if (dungeon.getExternalLink().length()>0) {
            external_btn.setVisibility(View.VISIBLE);
            external_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showExternalLink();
                }
            });
        }

        img = findViewById(R.id.dungeon_details_img);
        String img_url = dungeon.getImageLink();
        if (img_url.length()>0) {
            img.setVisibility(View.VISIBLE);
            Glide.with(context).load(dungeon.getImageLink()).into(img);
        }
        //comments_lv.setAdapter(commentArrayAdapter);

        for (int i=0; i<commentsArray.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.comment_layout, null);
            TextView comment_tv = view.findViewById(R.id.comment_layout_comment_tv);


            comment_tv.setText(commentsArray.get(i).getComment());
            comments_layout.addView(view);
        }

        dungeon.setVoteEventListener(new PostObjectBase.VoteEventListener() {
            @Override
            public void onVoteFail(final String msg) {
                DisplayDungeonActivity.this.runOnUiThread(new Runnable() {
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
                DisplayDungeonActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        votes_tv.setText(String.valueOf(dungeon.getCalculatedVotes()));
                    }
                });
                setOnResult();
            }

            @Override
            public void onBookmarkFail(final String msg) {
                DisplayDungeonActivity.this.runOnUiThread(new Runnable() {
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
        if (dungeon.isBookmarked()) {
            bookmark_iv.setImageResource(R.mipmap.ic_bookmarked);
        }
        else {
            bookmark_iv.setImageResource(R.mipmap.ic_bookmark);
        }
    }

    private void showExternalLink() {
        String link = dungeon.getExternalLink();
        if (link.length() == 0) {
            Toast.makeText(application, "No External Link was provided for this Unique Dungeon", Toast.LENGTH_LONG).show();
            return;
        }
        if (application.getExternalLinkAlert()) {
            Intent intent = new Intent(getApplicationContext(), ExternalLinkAlertActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("LINK", link);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(link));
            startActivity(i);
        }
    }

    private void setOnResult() {
        if (serialized.position!=-1) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("SERIALIZED_OBJ", dungeon.getSerialized());
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    private void resetButtons() {
        DisplayDungeonActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dungeon.getVoted() == 0) {
                    upvote_btn.setImageResource(R.mipmap.arrow_neutral);
                    downvote_btn.setImageResource(R.mipmap.arrow_downvote);
                } else if (dungeon.getVoted() == 1) {
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
        title_tv.setText(dungeon.getTitle());
        description_tv.setText(dungeon.getDescription());
        user_tv.setText(dungeon.getDetailSubtitle());

        if (dungeon.getExternalLink().length()>0) {
            external_btn.setVisibility(View.VISIBLE);
            external_btn.setOnClickListener(null);
            external_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showExternalLink();
                }
            });
        }

        String img_url = dungeon.getImageLink();
        if (img_url.length()>0) {
            img.setVisibility(View.VISIBLE);
            Glide.with(context).load(dungeon.getImageLink()).into(img);
        }
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
                        resultIntent.putExtra("SERIALIZED_OBJ", dungeon.getSerialized());
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
