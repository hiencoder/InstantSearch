package vn.edu.imic.instanstsearch.di.component;

import android.content.Context;

import dagger.Component;
import vn.edu.imic.instanstsearch.adapter.ContactAdapterDagger;
import vn.edu.imic.instanstsearch.di.module.ContactAdapterModule;
import vn.edu.imic.instanstsearch.di.module.RemoteSearchMvpModule;
import vn.edu.imic.instanstsearch.di.qualifier.ActivityContext;
import vn.edu.imic.instanstsearch.di.scope.ActivityScope;
import vn.edu.imic.instanstsearch.view.RemoteSearchActivity;
@ActivityScope
@Component(modules = {ContactAdapterModule.class, RemoteSearchMvpModule.class},
dependencies = AppComponent.class)
public interface RemoteSearchComponent {
    @ActivityContext
    Context getContext();

    void injectRemoteSearchActivity(RemoteSearchActivity remoteSearchActivity);
}
