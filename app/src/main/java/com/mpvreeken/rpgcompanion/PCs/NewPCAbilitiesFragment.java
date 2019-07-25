package com.mpvreeken.rpgcompanion.PCs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpvreeken.rpgcompanion.PCs.PC.PC;
import com.mpvreeken.rpgcompanion.PCs.PC.PCAbility;
import com.mpvreeken.rpgcompanion.R;

import java.util.List;

public class NewPCAbilitiesFragment extends Fragment {

    private NewPCActivity newPCActivity;
    private PC pc;
    private AbilitiesArrayAdapter abilitiesArrayAdapter;
    private ListView abilities_lv;

    private OnFragmentInteractionListener eventListener;
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String command);
    }

    public NewPCAbilitiesFragment() {
        // Required empty public constructor
    }


    public static NewPCAbilitiesFragment newInstance() {
        NewPCAbilitiesFragment fragment = new NewPCAbilitiesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_pc_abilities, container, false);

        newPCActivity = (NewPCActivity) getActivity();
        pc = newPCActivity.pc;



        abilitiesArrayAdapter = new AbilitiesArrayAdapter(newPCActivity, pc.abilities);
        abilities_lv = view.findViewById(R.id.new_pc_frag_abilities_lv);
        abilities_lv.setAdapter(abilitiesArrayAdapter);

        Button next_btn = view.findViewById(R.id.new_pc_next_btn);
        Button prev_btn = view.findViewById(R.id.new_pc_prev_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onFragmentInteraction("next");
            }
        });
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onFragmentInteraction("prev");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            eventListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        eventListener = null;
    }



    public class AbilitiesArrayAdapter extends ArrayAdapter<PCAbility> {

        private final Context context;
        private List<PCAbility> abilitys;

        public AbilitiesArrayAdapter(Context context, List<PCAbility> values) {
            super(context, R.layout.new_pc_ability_row, values);
            this.context = context;
            this.abilitys = values;
        }

        @Override
        public int getCount() {
            return abilitys != null ? abilitys.size() : 0;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final AdapterViewHolder holder;

            if (convertView==null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.new_pc_ability_row, parent, false);

                holder = new AdapterViewHolder();

                holder.name_tv = convertView.findViewById(R.id.new_pc_frag_ability_name_tv);
                holder.ability_score_et = convertView.findViewById(R.id.new_pc_frag_ability_score_et);
                holder.ability_extra_et = convertView.findViewById(R.id.new_pc_frag_ability_score_extra_et);
                holder.ability_total_tv = convertView.findViewById(R.id.new_pc_frag_ability_score_total_tv);
                holder.save_prof_cb = convertView.findViewById(R.id.new_pc_frag_ability_save_prof_cb);
                holder.save_extra_et = convertView.findViewById(R.id.new_pc_frag_ability_score_save_extra_et);
                holder.save_total_tv = convertView.findViewById(R.id.new_pc_frag_ability_save_total_tv);

                convertView.setTag(holder);
            }
            else {
                holder = (AdapterViewHolder) convertView.getTag();
            }

            final PCAbility ability = abilitys.get(position);

            if (ability==null) {
                Toast.makeText(context, "An error has occurred. Can you please send me an email with some details of how you got here?", Toast.LENGTH_LONG).show();
            }
            else {
                holder.name_tv.setText(ability.getName());
                holder.ability_score_et.setText(ability.getScore()==0 ? "" : ability.getScore()+"");
                holder.ability_extra_et.setText(ability.getScoreExtra()==0 ? "" : ability.getScoreExtra()+"");
                holder.ability_total_tv.setText(ability.getScore()==0 ? "" : ability.getScore()+"");
                holder.save_prof_cb.setChecked(ability.isProficient());
                holder.save_extra_et.setText(ability.getSaveExtra()==0 ? "" : ability.getSaveExtra()+"");
                holder.save_total_tv.setText(ability.getSaveModTotal()==0 ? "" : ability.getSaveModTotal()+"");
            }

            return convertView;
        }

        private void updateUI() {
            /*
            NewPCAbilitysFragment.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
            */
        }

        class AdapterViewHolder {
            CheckBox save_prof_cb;
            TextView name_tv, ability_total_tv, save_total_tv;
            EditText ability_score_et, ability_extra_et, save_extra_et;
        }
    }
}
