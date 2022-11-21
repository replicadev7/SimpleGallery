package com.rahul.gallerymvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rahul.gallerymvvm.allvideos.AllVideosActivity;
import com.rahul.gallerymvvm.folderwise.FolderAct;
import com.rahul.gallerymvvm.databinding.ActivityDashboardBinding;

public class DashboardAct extends AppCompatActivity {

    private ActivityDashboardBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardAct.this, FolderAct.class);
                startActivity(intent);

            }
        });

        binding.btnAllVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardAct.this, AllVideosActivity.class);
                startActivity(intent);
            }
        });
    }
}