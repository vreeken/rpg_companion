package com.mpvreeken.rpgcompanion.Dungeons;

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

public class DungeonArrayAdapter extends ArrayAdapter<Dungeon> {

    private final Context context;
    private final List<Dungeon> dungeons;
    private final RPGCActivity activity;


    public DungeonArrayAdapter(Context context, ArrayList<Dungeon> values) {
        super(context, R.layout.dungeons_list_item_layout, values);
        this.context = context;
        this.dungeons = values;
        this.activity=(RPGCActivity) context;
    }

    @Override
    public int getCount() {
        return dungeons != null ? dungeons.size() : 0;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final AdapterViewHolder holder;

        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dungeons_list_item_layout, parent, false);

            holder = new AdapterViewHolder();
            holder.title_tv = convertView.findViewById(R.id.dungeon_list_dungeon_title_tv);
            holder.votes_tv = convertView.findViewById(R.id.dungeon_list_dungeon_votes_tv);
            holder.desc_tv = convertView.findViewById(R.id.dungeon_list_dungeon_desc_tv);
            holder.upvote_btn = convertView.findViewById(R.id.dungeon_list_dungeon_upvote_btn);
            holder.downvote_btn = convertView.findViewById(R.id.dungeon_list_dungeon_downvote_btn);
            holder.voted=0;
            holder.tempVoted=0;

            convertView.setTag(holder);
        }
        else {
            holder = (AdapterViewHolder) convertView.getTag();
        }

        final Dungeon dungeon = dungeons.get(position);

        if (dungeon==null) {
            Toast.makeText(context, "An error has occurred. Can you please send me an email with some details of how you got here?", Toast.LENGTH_LONG).show();
        }
        else {
            View.OnClickListener onClickMe = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dungeon == null) {
                        Toast.makeText(context, "An error occurred trying to retrieve the data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, DisplayDungeonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SERIALIZED_OBJ", dungeon.getSerialized());
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, 1);
                }
            };


            holder.voted = holder.tempVoted = dungeon.getVoted();
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

            holder.title_tv.setText(dungeon.getTitle());
            holder.votes_tv.setText(dungeon.getListItemVotes());
            String desc = dungeon.getDescription();
            if (desc.length() > 2048) {
                desc = desc.substring(0, 2047) + "...";
            }
            holder.desc_tv.setText(desc);

            holder.desc_tv.setOnClickListener(onClickMe);
            holder.title_tv.setOnClickListener(onClickMe);

            holder.upvote_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.voted == 1) {
                        return;
                    }
                    holder.upvote_btn.setImageResource(R.mipmap.arrow_upvote);
                    holder.downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                    holder.tempVoted = 1;
                    dungeon.upvote();
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
                    dungeon.downvote();
                }
            });

            dungeon.setVoteEventListener(new PostObjectBase.VoteEventListener() {
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
        TextView title_tv;
        TextView votes_tv;
        TextView desc_tv;
        ImageButton upvote_btn;
        ImageButton downvote_btn;
        int voted;
        int tempVoted;
    }
}