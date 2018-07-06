package vn.edu.imic.instanstsearch.di.module;

import dagger.Module;
import dagger.Provides;
import vn.edu.imic.instanstsearch.di.scope.ActivityScope;
import vn.edu.imic.instanstsearch.mvp.view.RemoteSearchView;

@Module
public class RemoteSearchMvpModule {
    private RemoteSearchView.MainView remoteSearchView;

    public RemoteSearchMvpModule(RemoteSearchView.MainView remoteSearchView) {
        this.remoteSearchView = remoteSearchView;
    }

    @Provides
    @ActivityScope
    public RemoteSearchView.MainView provideRemoteSearchView(){
        return remoteSearchView;
    }
}
