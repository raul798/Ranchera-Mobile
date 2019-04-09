package ado.edu.pucmm.rancherasystem.remote.interceptor;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthenticationInterceptor implements Interceptor {
    private String credentials;

    public BasicAuthenticationInterceptor(String user, String password) {
        this.credentials = user != null &&
                password != null ? Credentials.basic(user, password) : null;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        if(credentials != null) {
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

        return chain.proceed(request);
    }
}

