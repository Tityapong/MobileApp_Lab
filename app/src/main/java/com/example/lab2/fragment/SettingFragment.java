package com.example.lab2.fragment;

import android.os.Bundle;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import com.example.lab2.R;
import com.example.lab2.utils.LocaleHelper;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        ListPreference languagePreference = findPreference("language");
        if (languagePreference != null) {
            // Set the current summary to reflect the selected language
            languagePreference.setSummary(languagePreference.getEntry());

            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String language = (String) newValue;

                // Update locale and persist the selection
                LocaleHelper.setLocale(requireActivity(), language);

                // Update the summary to reflect the new selection
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(language);
                listPreference.setSummary(listPreference.getEntries()[index]);

                // Recreate the activity to apply the language change
                requireActivity().recreate();

                return true; // Save the new value
            });
        }
    }
}