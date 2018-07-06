package vn.edu.imic.instanstsearch.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import vn.edu.imic.instanstsearch.di.scope.ActivityScope;
import vn.edu.imic.instanstsearch.view.LocalSearchActivityDagger;

@Module
public class LocalSearchActivityContextModule {
    //Provide context, LocalSearchActivity
    private LocalSearchActivityDagger activityDagger;
    private Context context;

    public LocalSearchActivityContextModule(LocalSearchActivityDagger activityDagger) {
        this.activityDagger = activityDagger;
        context = activityDagger;
    }

    @Provides
    @ActivityScope
    public LocalSearchActivityDagger provideLocalSearchActivity(){
        return activityDagger;
    }

    @Provides
    @ActivityScope
    public Context provideContext(){
        return context;
    }
}
