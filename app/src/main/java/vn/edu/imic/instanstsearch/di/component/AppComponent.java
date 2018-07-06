package vn.edu.imic.instanstsearch.di.component;

import android.content.Context;

import dagger.Component;
import vn.edu.imic.instanstsearch.application.MyApplication;
import vn.edu.imic.instanstsearch.di.module.ContextModule;
import vn.edu.imic.instanstsearch.di.module.NetworkModule;
import vn.edu.imic.instanstsearch.di.qualifier.ApplicationContext;
import vn.edu.imic.instanstsearch.di.scope.ApplicationScope;
import vn.edu.imic.instanstsearch.network.ApiService;
@ApplicationScope
@Component(modules = {ContextModule.class, NetworkModule.class})
public interface AppComponent {
    ApiService getApiService();

    @ApplicationContext
    Context getContext();

    void injectMyApplication(MyApplication myApplication);
}
