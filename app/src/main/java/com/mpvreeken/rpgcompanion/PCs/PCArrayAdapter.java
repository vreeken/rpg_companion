package com.mpvreeken.rpgcompanion.PCs;

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
import com.mpvreeken.rpgcompanion.PCs.PC.PC;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;
import com.mpvreeken.rpgcompanion.RPGCApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven on 7/4/2017.
 */

public class PCArrayAdapter extends ArrayAdapter<PC> {

    private final Context context;
    private final List<PC> pcs;
    private final RPGCActivity activity;


    public PCArrayAdapter(Context context, List<PC> values) {
        super(context, R.layout.pcs_list_item_layout, values);
        this.context = context;
        this.pcs = values;
        this.activity=(RPGCActivity) context;
    }

    @Override
    public int getCount() {
        return pcs != null ? pcs.size() : 0;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final AdapterViewHolder holder;

        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pcs_list_item_layout, parent, false);

            holder = new AdapterViewHolder();
            holder.name_tv = convertView.findViewById(R.id.pc_list_item_name_tv);

            convertView.setTag(holder);
        }
        else {
            holder = (AdapterViewHolder) convertView.getTag();
        }

        final PC pc = pcs.get(position);

        if (pc==null) {
            Toast.makeText(context, "An error has occurred. Can you please send me an email with some details of how you got here?", Toast.LENGTH_LONG).show();
        }
        else {
            View.OnClickListener onClickMe = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pc == null) {
                        Toast.makeText(context, "An error occurred trying to retrieve the data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, DisplayPCActivity.class);
                    RPGCApplication app = (RPGCApplication) activity.getApplication();
                    app.setEventBusObject(pc);
                    activity.startActivity(intent);
                }
            };

            holder.name_tv.setText(pc.name);
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
        TextView name_tv;
    }
}