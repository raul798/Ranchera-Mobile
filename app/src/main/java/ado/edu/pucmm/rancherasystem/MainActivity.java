package ado.edu.pucmm.rancherasystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
