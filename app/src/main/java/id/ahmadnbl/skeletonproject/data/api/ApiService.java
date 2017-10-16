package id.ahmadnbl.skeletonproject.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by billy on 8/8/16.
 *
 */

public class ApiService {
    //        public static final String DEFAULT_BASEURL = "https://myjne.jne.co.id:10443/jneone/service/";
//    public static final String DEFAULT_BASEURL = "http://192.168.8.102: 8080/jneone/service/";
    public static final String DEFAULT_BASEURL = "http://10.111.2.20:8080/jneone/service/";
    public final int CONNECTION_TIMEOUT_IN_SECOND = 90;

    public static String getUpdatePhotoUrl() {
        return DEFAULT_BASEURL + "users/mobile/updateImage";
    }

    public static String getTncUrl() {
        return "https://myjne.jne.co.id:10443/myjne-resources/term-condition.html";
    }

    private Api api;

    public ApiService() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Retrofit retrofitSvc = new Retrofit.Builder()
                .baseUrl(DEFAULT_BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getCustomClient())
                .build();
        this.api = retrofitSvc.create(Api.class);
    }

    private OkHttpClient getCustomClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder requestBuilder = chain.request().newBuilder();
//                        requestBuilder.header("Content-Type", "application/json");
                                return chain.proceed(requestBuilder.build());
                            }
                        }
                ).addInterceptor(logging)
                .connectTimeout(CONNECTION_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    public Api getApi() {
        return this.api;
    }
}
