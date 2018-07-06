package vn.edu.imic.instanstsearch.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import vn.edu.imic.instanstsearch.di.qualifier.ActivityContext;
import vn.edu.imic.instanstsearch.di.scope.ActivityScope;
import vn.edu.imic.instanstsearch.view.RemoteSearchActivityDagger;

@Module
public class RemoteSearchActivityContextModule {
    private RemoteSearchActivityDagger remoteSearchActivityDagger;

    private Context context;

    public RemoteSearchActivityContextModule(RemoteSearchActivityDagger remoteSearchActivityDagger) {
        this.remoteSearchActivityDagger = remoteSearchActivityDagger;
        context = remoteSearchActivityDagger;
    }

    @Provides
    @ActivityScope
    public RemoteSearchActivityDagger provideRemoteSearchActivity(){
        return remoteSearchActivityDagger;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext(){
        return context;
    }
}
