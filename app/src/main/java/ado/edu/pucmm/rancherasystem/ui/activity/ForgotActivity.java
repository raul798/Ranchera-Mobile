package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ado.edu.pucmm.rancherasystem.R;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    public void cancelMe(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
