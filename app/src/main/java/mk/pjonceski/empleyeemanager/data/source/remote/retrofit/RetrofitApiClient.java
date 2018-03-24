package mk.pjonceski.empleyeemanager.data.source.remote.retrofit;

import android.support.annotation.NonNull;
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
 * Int this class the retrofit client is build.
 * Basic authorisation is configured.
 * All requests are intercepted and authorisation  headers are added.
 */

public final class RetrofitApiClient {
    /**
     * Creating the retrofit api client.
     * It has basic authorization if username and password are provided.
     *
     * @param baseUrl  the base url of the rest api.
     * @param username the username for authorisation.
     * @param password the password for authorisation.
     * @return returns the RetroFit client.
     */
    public static RetrofitApi createClient(@NonNull String baseUrl, @Nullable String username, @Nullable String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(createOkHttpInterceptor(createBasicAuthorisationToken(username, password)))
                .build();
        return retrofit.create(RetrofitApi.class);
    }

    /**
     * Creates the interceptor for the client.
     * Intercepts all outgoing requests and adds authorisation headers{@link #getAuthorisationHeaders(String)}.
     *
     * @param authToken the authorisation token.
     * @return the client with interceptor.
     */
    private static OkHttpClient createOkHttpInterceptor(String authToken) {
        return new OkHttpClient.Builder()
                .addInterceptor((chain) -> {
                    Request request = chain.request()
                            .newBuilder()
                            .headers(getAuthorisationHeaders(authToken))
                            .build();
                    return chain.proceed(request);
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Creates authorisation http headers for provided authorisation token.
     *
     * @param authToken the authorisation token.
     * @return the http headers.
     */
    private static Headers getAuthorisationHeaders(@Nullable String authToken) {
        Headers.Builder headersBuilders = new Headers.Builder();
        headersBuilders.add("Content-Type", "application/json");
        headersBuilders.add("Accept", "application/json");
        if (authToken != null) {
            headersBuilders.add("Authorization", authToken);
        }
        return headersBuilders.build();
    }

    /**
     * Constructs base authorisation token with provided username and password.
     *
     * @param username the username for authorisation.
     * @param password the password for authorisation.
     * @return the auth token if username and password are not nulls,null otherwise.
     */
    @Nullable
    private static String createBasicAuthorisationToken(@Nullable String username, @Nullable String password) {
        if (username != null && password != null) {
            return Credentials.basic(username, password);
        }
        return null;
    }
}
