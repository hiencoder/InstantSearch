package vn.edu.imic.instanstsearch.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.edu.imic.instanstsearch.R;
import vn.edu.imic.instanstsearch.interfaces.OnContactListener;

public class RemoteSearchActivityDagger extends AppCompatActivity implements OnContactListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_search_dagger);
    }

    @Override
    public void onContactClick(int position) {

    }
}
