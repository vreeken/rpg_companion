package com.mpvreeken.rpgcompanion.EncMaps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mpvreeken.rpgcompanion.Classes.PostObjectBase;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven on 7/4/2017.
 */

public class EncMapArrayAdapter extends ArrayAdapter<EncMap> {

    private final Context context;
    private final List<EncMap> maps;
    private final RPGCActivity activity;


    public EncMapArrayAdapter(Context context, ArrayList<EncMap> values) {
        super(context, R.layout.maps_list_item_layout, values);
        this.context = context;
        this.maps = values;
        this.activity=(RPGCActivity) context;
    }

    @Override
    public int getCount() {
        return maps != null ? maps.size() : 0;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final AdapterViewHolder holder;

        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.maps_list_item_layout, parent, false);

            holder = new AdapterViewHolder();
            holder.title_tv = convertView.findViewById(R.id.map_list_item_title_tv);
            holder.votes_tv = convertView.findViewById(R.id.map_list_item_votes_tv);
            holder.desc_tv = convertView.findViewById(R.id.map_list_item_desc_tv);
            holder.upvote_btn = convertView.findViewById(R.id.map_list_item_upvote_btn);
            holder.downvote_btn = convertView.findViewById(R.id.map_list_item_downvote_btn);
            holder.img = convertView.findViewById(R.id.map_list_item_img);
            holder.voted=0;
            holder.tempVoted=0;

            convertView.setTag(holder);
        }
        else {
            holder = (AdapterViewHolder) convertView.getTag();
        }

        final EncMap map = maps.get(position);

        if (map==null) {
            Toast.makeText(context, "An error has occurred. Can you please send me an email with some details of how you got here?", Toast.LENGTH_LONG).show();
        }
        else {
            View.OnClickListener onClickMe = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (map == null) {
                        Toast.makeText(context, "An error occurred trying to retrieve the data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, DisplayEncMapActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SERIALIZED_OBJ", map.getSerialized());
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, 1);
                }
            };


            Glide.with(context).load(context.getResources().getString(R.string.url_map_thumbs) + map.getId() + ".jpg").into(holder.img);

            holder.voted = holder.tempVoted = map.getVoted();
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

            holder.title_tv.setText(map.getTitle());
            holder.votes_tv.setText(map.getListItemVotes());
            String desc = map.getDescription();
            if (desc.length() > 2048) {
                desc = desc.substring(0, 2047) + "...";
            }
            holder.desc_tv.setText(desc);

            holder.desc_tv.setOnClickListener(onClickMe);
            holder.title_tv.setOnClickListener(onClickMe);
            holder.img.setOnClickListener(onClickMe);

            holder.upvote_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.voted == 1) {
                        return;
                    }
                    holder.upvote_btn.setImageResource(R.mipmap.arrow_upvote);
                    holder.downvote_btn.setImageResource(R.mipmap.arrow_neutral);
                    holder.tempVoted = 1;
                    map.upvote();
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
                    map.downvote();
                }
            });

            map.setVoteEventListener(new PostObjectBase.VoteEventListener() {
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
        ImageView img;
        int voted;
        int tempVoted;
    }
}