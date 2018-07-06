package vn.edu.imic.instanstsearch.di.module;

import dagger.Module;
import dagger.Provides;
import vn.edu.imic.instanstsearch.adapter.ContactAdapterDagger;
import vn.edu.imic.instanstsearch.di.scope.ActivityScope;
import vn.edu.imic.instanstsearch.interfaces.OnContactListener;
import vn.edu.imic.instanstsearch.view.RemoteSearchActivityDagger;

@Module(includes = {RemoteSearchActivityContextModule.class})
public class ContactAdapterModule {
    //Provide ContactAdapter
    @Provides
    @ActivityScope
    public ContactAdapterDagger provideContactAdapter(OnContactListener contactListener){
        return new ContactAdapterDagger(contactListener);
    }

    @Provides
    @ActivityScope
    public OnContactListener provideOnContactListener(RemoteSearchActivityDagger remoteSearchActivity){
        return remoteSearchActivity;
    }
}
