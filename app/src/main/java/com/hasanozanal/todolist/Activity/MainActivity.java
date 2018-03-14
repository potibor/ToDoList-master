package com.hasanozanal.todolist.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hasanozanal.todolist.Fragments.ListelemeFragment;
import com.hasanozanal.todolist.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        ListelemeFragment listeFragment =(ListelemeFragment)manager.findFragmentById(R.id.fragment_container);

        if (listeFragment == null){
            listeFragment = new ListelemeFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container,listeFragment);
            transaction.commit();
        }
    }
}
