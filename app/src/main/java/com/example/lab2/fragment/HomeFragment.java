//package com.example.lab2.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.lab2.R;
//
//public class HomeFragment extends Fragment {
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
//
//        // Apply EdgeToEdge behavior (for handling system UI like status bar)
//        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        return view;
//    }
//}



package com.example.lab2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lab2.R;

public class HomeFragment extends Fragment {

    private TextView summaryTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        summaryTextView = view.findViewById(R.id.summaryTextView);

        Bundle data = getArguments();
        if (data != null) {
            String amount = data.getString("amount");
            String currency = data.getString("currency");
            String category = data.getString("category");
            String remark = data.getString("remark");
            String date = data.getString("date");

            String summary = "Amount: " + amount + " " + currency +
                    "\nCategory: " + category +
                    "\nRemark: " + remark +
                    "\nDate: " + date;

            summaryTextView.setText(summary);
        }

        return view;
    }
}