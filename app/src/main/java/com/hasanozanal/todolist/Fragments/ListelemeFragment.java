package com.hasanozanal.todolist.Fragments;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.hasanozanal.todolist.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class ListelemeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button addBtn ;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList = new ArrayList<String>();

    public ListelemeFragment() {
        // Required empty public constructor
    }
    public static ListelemeFragment newInstance(String param1, String param2) {
        ListelemeFragment fragment = new ListelemeFragment();
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
        View view = inflater.inflate(R.layout.fragment_liste, container, false);

        addBtn = (Button) view.findViewById(R.id.addBtnID);
        listView = (ListView) view.findViewById(R.id.listviewItemsID);


        final SharedPreferences preferences = getActivity().getSharedPreferences("myPrefsFile", Context.MODE_PRIVATE);
        final Set<String> fetch = preferences.getStringSet("list",null);
        if(fetch == null){
            arrayList = new ArrayList<String>();
        }
        else{
            arrayList = new ArrayList<String>(fetch);
        }
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        listView.setLongClickable(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Delete");
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Are you sure?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(position);

                                Set<String> lv = new LinkedHashSet<>(arrayList);
                                //arrayList = new ArrayList<>(fetch);
                                SharedPreferences.Editor editor= preferences.edit();
                                editor.clear();
                                editor.putStringSet("list",lv);
                                editor.commit();

                                adapter.notifyDataSetChanged();
                            }
                        });

                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alert1 = builder1.create();
                        alert1.show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
        adapter.notifyDataSetChanged();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddItemFragment();
            }
        });



        return view;
    }

    public void gotoAddItemFragment() {
        AddItemFragment addItemFragment = new AddItemFragment();
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,addItemFragment);
        transaction.commit();
    }

}
