package com.mpvreeken.rpgcompanion.PCs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpvreeken.rpgcompanion.R;

/**
 * Created by Sven on 2/16/2018.
 */

public class DisplayPCAbilitiesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_pc_abilities, container, false);

        return rootView;
    }
}
