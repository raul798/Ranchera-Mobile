package ado.edu.pucmm.rancherasystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    public void cancelMe(View view) {
        Intent cancelIntent = new Intent(this, MainActivity.class);
        //login logic
        startActivity(cancelIntent);

    }
}
