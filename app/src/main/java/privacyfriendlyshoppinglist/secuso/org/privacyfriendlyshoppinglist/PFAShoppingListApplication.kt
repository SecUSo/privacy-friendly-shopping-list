package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist


import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Configuration
import org.secuso.privacyfriendlybackup.api.pfa.BackupManager
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.backup.BackupCreator
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.backup.BackupRestorer
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.helpers.PreferenceKeys

class PFAShoppingListApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        BackupManager.backupCreator = BackupCreator()
        BackupManager.backupRestorer = BackupRestorer()

        when (PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(PreferenceKeys.APP_THEME, getString(R.string.pref_app_theme_default))) {
            "DARK" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            "LIGHT" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setMinimumLoggingLevel(Log.INFO).build()
}