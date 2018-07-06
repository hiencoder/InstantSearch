package vn.edu.imic.instanstsearch.application;

import android.app.Application;
import android.content.Context;

import vn.edu.imic.instanstsearch.di.component.AppComponent;
import vn.edu.imic.instanstsearch.di.component.DaggerAppComponent;
import vn.edu.imic.instanstsearch.di.module.ContextModule;

public class MyApplication extends Application{
    private AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        appComponent.injectMyApplication(this);
    }

    public static MyApplication getMyApplication(Context context){
        return (MyApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
