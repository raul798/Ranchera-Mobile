package ado.edu.pucmm.rancherasystem.remote.interceptor;

import android.content.Context;
import android.se.omapi.Session;

import java.io.IOException;

import ado.edu.pucmm.rancherasystem.remote.SessionService;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthenticationInterceptor implements Interceptor {
    private String credentials;
    private Context context;

    public BasicAuthenticationInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        SessionService sessionService = SessionService.getInstance(context);

        String user = sessionService.getUsername();
        String password = sessionService.getPassword();

        this.credentials = user != null &&
                password != null ? Credentials.basic(user, password) : null;

        if(credentials != null) {
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

        return chain.proceed(request);
    }
}

