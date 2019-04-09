package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.remote.DataSource;
import ado.edu.pucmm.rancherasystem.remote.SessionService;
import ado.edu.pucmm.rancherasystem.remote.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    SessionService sessionService;
    boolean isLoginInProcess = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionService = SessionService.getInstance(getBaseContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sessionService.getId() != null)
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
    }

    public void loginMe(View view) {
        if(isLoginInProcess){
            Toast.makeText(MainActivity.this, "Su solicitud ha sido tomada, espere un momento", Toast.LENGTH_SHORT).show();
            return;
        }
            
        
        EditText usernameTxt = findViewById(R.id.username);
        EditText passwordTxt = findViewById(R.id.password);
        
        String username = usernameTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        
        if(username.isEmpty() && password.isEmpty()){
            usernameTxt.setError("El campo es requerido");
            passwordTxt.setError("El campo es requerido");
            return;
        }
        
        sessionService = SessionService.getInstance(getBaseContext());
        DataSource dataSource = DataSource.getInstance(getBaseContext(), sessionService);

        User user = new User(username, password);
        
        isLoginInProcess = true;
        
        Call<User> userCall = dataSource.getService().verify(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();

                    sessionService.setPassword(username);
                    sessionService.setUsername(password);
                    sessionService.setId(user.getId());
                    sessionService.setEmail(user.getPrimaryEmailAddr().getAddress());
                    sessionService.setDisplay(user.getDisplayName());

                    startActivity(new Intent(MainActivity.this, MenuActivity.class));

                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                    Log.wtf(TAG, user.toString());
                }else{ 
                    Toast.makeText(MainActivity.this, "Usuario y/o contraseña invalido", Toast.LENGTH_SHORT).show();
                }
                isLoginInProcess = false;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Usuario y/o contraseña invalido", Toast.LENGTH_SHORT).show();
                Log.wtf(TAG,t);
                isLoginInProcess = false;
            }
        });
    }

    public void forgotMe(View view) {
        startActivity(new Intent(this, ForgotActivity.class));
    }

    public void helpMe(View view) {
        startActivity(new Intent(this, SupportActivity.class));
    }
}
