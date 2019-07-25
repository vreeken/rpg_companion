package com.mpvreeken.rpgcompanion.Riddles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mpvreeken.rpgcompanion.Classes.PostObjectBase;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven on 7/4/2017.
 */

public class RiddleArrayAdapter extends ArrayAdapter<Riddle> {

    private final Context context;
    private final List<Riddle> riddles;
    private final RPGCActivity activity;


    public RiddleArrayAdapter(Context context, ArrayList<Riddle> values) {
        super(context, R.layout.riddles_list_item_layout, values);
        this.context = context;
        this.riddles = values;
        this.activity=(RPGCActivity) context;
    }

    @Override
    public int getCount() {
        return riddles != null ? riddles.size() : 0;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final AdapterViewHolder holder;

        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.riddles_list_item_layout, parent, false);

            holder = new AdapterViewHolder();
            holder.riddle_tv = convertView.findViewById(R.id.riddle_list_item_riddle_tv);
            holder.votes_tv = convertView.findViewById(R.id.riddle_list_item_votes_tv);
            holder.answer_tv = convertView.findViewById(R.id.riddle_list_item_answer_tv);
            holder.upvote_btn = convertView.findViewById(R.id.riddle_list_item_upvote_btn);
            holder.downvote_btn = convertView.findViewById(R.id.riddle_list_item_downvote_btn);
            holder.voted=0;
            holder.tempVoted=0;

            convertView.setTag(holder);
        }
        else {
            holder = (AdapterViewHolder) convertView.getTag();
        }

        final Riddle riddle = riddles.get(position);

        if (riddle==null) {
            Toast.makeText(context, "An error has occurred. Can you please send me an email with some details of how you got here?", Toast.LENGTH_LONG).show();
        }
        else {
            View.OnClickListener onClickMe = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (riddle == null) {
                        Toast.makeText(context, "An error occurred trying to retrieve the data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, DisplayRiddleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SERIALIZED_OBJ", riddle.getSerialized());
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, 1);
                }
            };


            holder.voted = holder.tempVoted = riddle.getVoted();
            if (holder.voted == 1) {
                holder.upvote_btn.setImageResource(R.mipmap.arrow_upvote);
                holder.downvote_btn.setImageResource(R.mipmap.arrow_neutral);
            } else if (holder.voted == 0) {
                holder.downvote_btn.setImageResource(R.mipmap.arrow_downvote);
                holder.upvote_btn.setImageResource(R.mipmap.arrow_neutral);
            }
            else {
                holder.downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                holder.upvote_btn.setImageResource(R.mipmap.arrow_neutral);
            }

            holder.riddle_tv.setText(riddle.getRiddle());
            holder.votes_tv.setText(riddle.getListItemVotes());

            holder.answer_tv.setText(riddle.getAnswer());

            holder.riddle_tv.setOnClickListener(onClickMe);
            holder.answer_tv.setOnClickListener(onClickMe);

            holder.upvote_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.voted == 1) {
                        return;
                    }
                    holder.upvote_btn.setImageResource(R.mipmap.arrow_upvote);
                    holder.downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                    holder.tempVoted = 1;
                    riddle.upvote();
                }
            });
            holder.downvote_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.voted == 0) {
                        return;
                    }
                    holder.downvote_btn.setImageResource(R.mipmap.arrow_downvote);
                    holder.upvote_btn.setImageResource(R.mipmap.arrow_neutral);
                    holder.tempVoted = 0;
                    riddle.downvote();
                }
            });

            riddle.setVoteEventListener(new PostObjectBase.VoteEventListener() {
                @Override
                public void onVoteFail(String msg) {
                    holder.tempVoted = holder.voted;
                    updateUI();
                    if (!msg.isEmpty()) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onVoteSuccess() {
                    holder.voted = holder.tempVoted;
                    updateUI();
                }

                @Override
                public void onBookmarkFail(String s) {

                }
            });
        }


        return convertView;
    }

    private void updateUI() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    static class AdapterViewHolder {
        TextView riddle_tv;
        TextView votes_tv;
        TextView answer_tv;
        ImageButton upvote_btn;
        ImageButton downvote_btn;
        int voted;
        int tempVoted;
    }
}