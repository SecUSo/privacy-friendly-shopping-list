package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist


import android.app.Application
import android.util.Log
import androidx.work.Configuration
import org.secuso.privacyfriendlybackup.api.pfa.BackupManager
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.backup.BackupCreator
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.backup.BackupRestorer

class PFAShoppingListApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        BackupManager.backupCreator = BackupCreator()
        BackupManager.backupRestorer = BackupRestorer()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(Log.INFO).build()
    }
}