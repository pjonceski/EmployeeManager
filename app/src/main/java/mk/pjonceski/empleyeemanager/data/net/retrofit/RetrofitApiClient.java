package mk.pjonceski.empleyeemanager.data.net.retrofit;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The retrofit client is build.
 * Basic authorisation is configured.
 * All requests are intercepted and authorisation  headers are added.
 */

public final class RetrofitApiClient {
    public static RetrofitApi createClient(String baseUrl, String username, String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(createOkHttpInterceptor(Credentials.basic(username, password)))
                .build();
        return retrofit.create(RetrofitApi.class);
    }

    private static OkHttpClient createOkHttpInterceptor(String authToken) {
        return new OkHttpClient.Builder()
                .addInterceptor((chain) -> {
                    Request request = chain.request()
                            .newBuilder()
                            .headers(getAuthorisationHeader(authToken))
                            .build();
                    return chain.proceed(request);
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private static Headers getAuthorisationHeader(@Nullable String authToken) {
        Headers.Builder headersBuilders = new Headers.Builder();
        headersBuilders.add("Content-Type", "application/json");
        headersBuilders.add("Accept", "application/json");
        if (authToken != null) {
            headersBuilders.add("Authorization", authToken);
        }
        return headersBuilders.build();
    }
}
