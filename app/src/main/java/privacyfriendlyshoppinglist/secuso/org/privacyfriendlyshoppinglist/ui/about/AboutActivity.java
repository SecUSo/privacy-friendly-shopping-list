package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.about;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.BuildConfig;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.R;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.baseactivity.BaseActivity;

/**
 * Created by yonjuni on 15.06.16.
 */
public class AboutActivity extends AppCompatActivity
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar ab = getSupportActionBar();
        if ( ab != null )
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        View mainContent = findViewById(R.id.main_content);
        if ( mainContent != null )
        {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(BaseActivity.MAIN_CONTENT_FADEIN_DURATION);
        }

        overridePendingTransition(0, 0);

        ((TextView)findViewById(R.id.secusoWebsite)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.githubURL)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.textFieldVersionName)).setText(BuildConfig.VERSION_NAME);
    }



    //@Override
    //protected int getNavigationDrawerID() {
    //    return R.id.nav_about;
    //}
}

