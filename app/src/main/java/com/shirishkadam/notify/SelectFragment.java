package com.shirishkadam.notify;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelectFragment extends Fragment {

    public static final String Topic_FE = "/topics/FE";
    public static final String Topic_SE = "/topics/SE";
    public static final String Topic_TE = "/topics/TE";
    public static final String Topic_BE = "/topics/BE";

    public SelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select, container, false);
        // Inflate the layout for this fragment
        Button fe = (Button) view.findViewById(R.id.fe);
        Button se = (Button) view.findViewById(R.id.se);
        Button te = (Button) view.findViewById(R.id.te);
        Button be = (Button) view.findViewById(R.id.be);

        final Intent in = new Intent(getActivity(),MessageActivity.class);
        final SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


        fe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in.putExtra("Topic", Topic_FE);
                startActivity(in);
                sf.edit().putString("Last_Visited", Topic_FE).apply();
            }
        });

        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in.putExtra("Topic", Topic_SE);
                startActivity(in);
                sf.edit().putString("Last_Visited",Topic_SE).apply();

            }
        });

        te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in.putExtra("Topic", Topic_TE);
                startActivity(in);
                sf.edit().putString("Last_Visited",Topic_TE).apply();

            }
        });

        be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in.putExtra("Topic",Topic_BE);
                startActivity(in);
                sf.edit().putString("Last_Visited",Topic_BE).apply();

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
