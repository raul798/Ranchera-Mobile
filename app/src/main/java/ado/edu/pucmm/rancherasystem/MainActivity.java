package ado.edu.pucmm.rancherasystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String id_client;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void loginMe(View view) {
        Intent loginIntent = new Intent(this, MenuActivity.class);
        //login logic
        startActivity(loginIntent);
    }

    public void forgotMe(View view) {
        Intent forgotIntent = new Intent(this, ForgotActivity.class);
        //login logic
        startActivity(forgotIntent);
    }

    public void helpMe(View view) {
        Intent helpIntent = new Intent(this, SupportActivity.class);
        //login logic
        startActivity(helpIntent);
    }







}
