package com.mpvreeken.rpgcompanion.Dungeons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mpvreeken.rpgcompanion.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven on 7/4/2017.
 */

public class DungeonCommentsArrayAdapter extends ArrayAdapter<DungeonComment> {

    private final Context context;
    private final List<DungeonComment> comments;
    //TODO
    //Consider using a RecyclerView instead of ListView

    public DungeonCommentsArrayAdapter(Context context, ArrayList<DungeonComment> values) {
        super(context, R.layout.comments_list_item_layout, values);
        this.context = context;
        this.comments = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DungeonComment comment = comments.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.comments_list_item_layout, parent, false);
        TextView comment_tv = (TextView) rowView.findViewById(R.id.comment_list_item_comment_tv);
        TextView vote_tv = (TextView) rowView.findViewById(R.id.comment_list_item_vote_tv);

        comment_tv.setText(comment.getComment());
        vote_tv.setText(String.valueOf(comment.getVotes()));

        return rowView;
    }
}