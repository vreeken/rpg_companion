package com.mpvreeken.rpgcompanion.PCs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mpvreeken.rpgcompanion.PCs.PC.PC;
import com.mpvreeken.rpgcompanion.R;

public class NewPCRaceClassFragment extends Fragment {

    private NewPCActivity newPCActivity;
    private PC pc;

    private Spinner raceSpinner, subraceSpinner, classSpinner, backgroundSpinner;

    private OnFragmentInteractionListener eventListener;
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String command);
    }

    public NewPCRaceClassFragment() {
        // Required empty public constructor
    }

    
    public static NewPCRaceClassFragment newInstance() {
        NewPCRaceClassFragment fragment = new NewPCRaceClassFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_pc_race_class, container, false);

        newPCActivity = (NewPCActivity) getActivity();
        pc = newPCActivity.pc;

        Button next_btn = view.findViewById(R.id.new_pc_next_btn);
        Button prev_btn = view.findViewById(R.id.new_pc_prev_btn);

        raceSpinner = view.findViewById(R.id.new_pc_race_spinner);
        subraceSpinner = view.findViewById(R.id.new_pc_subrace_spinner);
        classSpinner = view.findViewById(R.id.new_pc_class_spinner);
        backgroundSpinner = view.findViewById(R.id.new_pc_background_spinner);

        ArrayAdapter<String> raceAdapter = new ArrayAdapter<>(newPCActivity, android.R.layout.simple_spinner_item, PC.RACES);
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(raceAdapter);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(newPCActivity, android.R.layout.simple_spinner_item, PC.CLASSES);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(classAdapter);

        ArrayAdapter<String> backgroundAdapter = new ArrayAdapter<>(newPCActivity, android.R.layout.simple_spinner_item, PC.BACKGROUNDS);
        backgroundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(backgroundAdapter);



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
}
