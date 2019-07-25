package com.mpvreeken.rpgcompanion.PCs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.mpvreeken.rpgcompanion.PCs.PC.PCSkill;
import com.mpvreeken.rpgcompanion.R;

import java.util.ArrayList;
import java.util.List;

public class NewPCSkillsFragment extends Fragment {

    private NewPCActivity newPCActivity;
    private PC pc;
    private SkillsArrayAdapter skillsArrayAdapter;
    private ListView skills_lv;

    private OnFragmentInteractionListener eventListener;
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String command);
    }

    public NewPCSkillsFragment() { /*Required empty public constructor*/ }


    public static NewPCSkillsFragment newInstance() {
        NewPCSkillsFragment fragment = new NewPCSkillsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_pc_skills, container, false);

        newPCActivity = (NewPCActivity) getActivity();
        pc = newPCActivity.pc;


        skillsArrayAdapter = new SkillsArrayAdapter(newPCActivity, pc.skills);
        skills_lv = view.findViewById(R.id.new_pc_frag_skills_lv);
        skills_lv.setAdapter(skillsArrayAdapter);


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









    public class SkillsArrayAdapter extends ArrayAdapter<PCSkill> {

        private final Context context;
        private List<PCSkill> skills;

        public SkillsArrayAdapter(Context context, List<PCSkill> values) {
            super(context, R.layout.new_pc_skill_row, values);
            this.context = context;
            this.skills = values;
        }

        @Override
        public int getCount() {
            return skills != null ? skills.size() : 0;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final AdapterViewHolder holder;

            if (convertView==null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.new_pc_skill_row, parent, false);

                holder = new AdapterViewHolder();
                holder.proficient_cb = convertView.findViewById(R.id.new_pc_skills_prof_cb);
                holder.expertise_cb = convertView.findViewById(R.id.new_pc_skills_expertise_cb);
                holder.name_tv = convertView.findViewById(R.id.new_pc_skills_skill_tv);
                holder.extra_et = convertView.findViewById(R.id.new_pc_skills_extra_et);
                holder.total_tv = convertView.findViewById(R.id.new_pc_skills_total_tv);

                convertView.setTag(holder);
            }
            else {
                holder = (AdapterViewHolder) convertView.getTag();
            }

            final PCSkill skill = skills.get(position);

            if (skill==null) {
                Toast.makeText(context, "An error has occurred. Can you please send me an email with some details of how you got here?", Toast.LENGTH_LONG).show();
            }
            else {
                holder.name_tv.setText(skill.getFullName());
                holder.proficient_cb.setChecked(skill.proficient);
                holder.expertise_cb.setChecked(skill.expertise);
                holder.extra_et.setText(skill.extra+"");
                holder.total_tv.setText(skill.total+"");
            }

            return convertView;
        }

        private void updateUI() {
            /*
            NewPCSkillsFragment.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
            */
        }

        class AdapterViewHolder {
            CheckBox proficient_cb, expertise_cb;
            TextView name_tv, total_tv;
            EditText extra_et;
        }
    }

}


