package com.mier.Mondeo.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mier.Mondeo.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}