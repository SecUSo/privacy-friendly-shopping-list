package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui;

import android.location.LocationManager;
import android.os.Bundle;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.demo.DemoApplication;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.demo.DemoBaseActivity;

import javax.inject.Inject;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.05.16 creation date
 */
public class MainActivity extends DemoBaseActivity
{
    @Inject
    LocationManager locationManager;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DemoApplication)getApplication()).inject(this);
    }

    @Override
    protected final void onStart() {
        super.onStart();
    }

    @Override
    protected final void onStop() {
        super.onStop();
    }
}
