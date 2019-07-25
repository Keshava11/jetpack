package com.ravi.jetpack;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.ravi.jetpack.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.secondary_btn)
    Button mButton;
    @BindView(R.id.expected_closer)
    TextView mTestView;
    @BindView(R.id.expected_closer1)
    TextView mTestView1;
    @BindView(R.id.expected_closer2)
    TextView mTestView2;
    @BindView(R.id.expected_closer3)
    TextView mTestView3;

    @BindView(R.id.sim_txv)
    TextView mSimTxv;

    @Override
    String getActivityName() {
        return "Main";
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mSimTxv.setText(LogUtils.getModelDetails());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String str = sharedPreferences.getString("firstInstall", "");

        if (TextUtils.isEmpty(str)) {
            sharedPreferences.edit().putString("firstInstall", "yes").apply();
        }
    }

    public static String capitalizeNew(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        String[] splitArr = str.split(" ");

        StringBuilder builder = new StringBuilder();
        if (splitArr.length > 0) {

            // For each item split
            for (String item : splitArr) {
                char[] arr = item.toCharArray();
                boolean capitalizeNext = true;

                for (char c : arr) {
                    if (capitalizeNext && Character.isLetter(c)) {
                        builder.append(Character.toUpperCase(c));
                        capitalizeNext = false;
                        continue;
                    } else if (Character.isLetter(c)) {
                        builder.append(Character.toLowerCase(c));
                        capitalizeNext = true;
                    }
                    builder.append(c);
                }
            }
        }

        return builder.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 9001) {
            if (permissions.length > 0) {
                if (permissions[0].equals(Manifest.permission.READ_PHONE_STATE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO Check IMEI numbers
                }
            }
        }
    }

    @OnClick(R.id.secondary_btn)
    public void onBtnCLick() {
        Intent intent = new Intent(this, SecondaryActivity.class);
        startActivity(intent);
    }

    public static boolean allZero(String iInputStr) {
        return iInputStr.replaceAll("0", "").trim().length() <= 0;
    }
}
