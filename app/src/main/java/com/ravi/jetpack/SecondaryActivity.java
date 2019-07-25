package com.ravi.jetpack;

import android.os.Bundle;

public class SecondaryActivity extends BaseActivity {

    @Override
    String getActivityName() {
        return "Secondary";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
    }
}
