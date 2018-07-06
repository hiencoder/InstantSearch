package vn.edu.imic.instanstsearch.di.module;

import dagger.Module;
import dagger.Provides;
import vn.edu.imic.instanstsearch.di.scope.ActivityScope;
import vn.edu.imic.instanstsearch.mvp.view.LocalSearchView;

@Module
public class LocalSearchMvpModule {
    //provide LocalSearchView
    private LocalSearchView.MainView localSearchView;

    public LocalSearchMvpModule(LocalSearchView.MainView localSearchView) {
        this.localSearchView = localSearchView;
    }

    @Provides
    @ActivityScope
    public LocalSearchView.MainView provideLocalSearchView(){
        return localSearchView;
    }
}
