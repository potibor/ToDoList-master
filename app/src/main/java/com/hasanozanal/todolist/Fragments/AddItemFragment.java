package com.hasanozanal.todolist.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hasanozanal.todolist.R;
import java.util.LinkedHashSet;
import java.util.Set;

public class AddItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editText;
    private Button saveBtn;
    private static final String PREFS_NAME = "myPrefsFile";

    public AddItemFragment() {
        // Required empty public constructor
    }
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        editText = (EditText) view.findViewById(R.id.edittextID);
        saveBtn = (Button) view.findViewById(R.id.saveBtnID);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editTextDetails = editText.getText().toString();

               if (editTextDetails.equals("")){
                  Toast.makeText(getContext(),"Empty Task",Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    Set<String> set = preferences.getStringSet("list", new ArraySet<String>());
                    set.add(editTextDetails);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putStringSet("list",set);
                    editor.clear();
                    editor.commit();

                    backtoListeFragment();
                }
            }
        });

        return view;

    }

    public void backtoListeFragment() {
        ListelemeFragment listeFragment = new ListelemeFragment();
        //AppCompatActivity activity = (AppCompatActivity)getActivity();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,listeFragment);
        transaction.commit();
    }
}
