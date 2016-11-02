package ro.deschis;

import android.location.Location;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    @Override public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public Location getCurrentLocation() {
        return ((DeschisApplication) getApplication()).currentLocation;
    }
}
