package ado.edu.pucmm.rancherasystem.remote;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import ado.edu.pucmm.rancherasystem.remote.interceptor.BasicAuthenticationInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSource {

    private static DataSource instance;

    private SessionService sessionService;
    private Service service;
    private Context context;

    private DataSource(Context context, SessionService sessionService){
        this.sessionService = sessionService;
        this.context = context;

        String username = sessionService.getUsername();
        String password = sessionService.getPassword();
        String endpoint = "http:///ranchera.dfb.com.do";

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthenticationInterceptor(username, password))
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(Service.class);
    }

    public static DataSource getInstance(Context context, SessionService sessionService){
        if(instance == null){
            instance = new DataSource(context, sessionService);
        }
        return instance;
    }

    public Service getService() {
        return service;
    }
}
