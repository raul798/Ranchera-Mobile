package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ado.edu.pucmm.rancherasystem.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginMe(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }

    public void forgotMe(View view) {
        startActivity(new Intent(this, ForgotActivity.class));
    }

    public void helpMe(View view) {
        startActivity(new Intent(this, SupportActivity.class));
    }
}
