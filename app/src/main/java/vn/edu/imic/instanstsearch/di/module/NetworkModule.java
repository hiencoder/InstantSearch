package vn.edu.imic.instanstsearch.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.imic.instanstsearch.di.scope.ApplicationScope;
import vn.edu.imic.instanstsearch.network.ApiService;
import vn.edu.imic.instanstsearch.utils.Constant;

@Module
public class NetworkModule {
    @Provides
    @ApplicationScope
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(Constant.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.REQUEST_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(Constant.REQUEST_TIMEOUT,TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);
        return httpClient.build();
    }

    @Provides
    @ApplicationScope
    public HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
