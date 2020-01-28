package com.vnat.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.vnat.appbanhang.R;

public class LienHeActivity extends AppCompatActivity {
    Toolbar toolbarLienHe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        toolbarLienHe = findViewById(R.id.toolbarLienHe);
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(toolbarLienHe);
        getSupportActionBar().setTitle("Liên hệ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
