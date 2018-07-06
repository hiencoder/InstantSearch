package vn.edu.imic.instanstsearch.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import vn.edu.imic.instanstsearch.di.qualifier.ApplicationContext;
import vn.edu.imic.instanstsearch.di.scope.ApplicationScope;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext(){
        return context;
    }
}
