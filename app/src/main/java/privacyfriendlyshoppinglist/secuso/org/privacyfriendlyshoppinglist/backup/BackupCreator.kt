package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.backup

import android.content.Context
import android.preference.PreferenceManager
import android.util.JsonWriter
import android.util.Log
import org.secuso.privacyfriendlybackup.api.backup.DatabaseUtil.getSupportSQLiteOpenHelper
import org.secuso.privacyfriendlybackup.api.backup.DatabaseUtil.writeDatabase
import org.secuso.privacyfriendlybackup.api.backup.PreferenceUtil.writePreferences
import org.secuso.privacyfriendlybackup.api.pfa.IBackupCreator
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB
import java.io.OutputStream
import java.io.OutputStreamWriter

class BackupCreator : IBackupCreator {
    override fun writeBackup(context: Context, outputStream: OutputStream): Boolean {
        Log.d(TAG, "createBackup() started")
        val outputStreamWriter = OutputStreamWriter(outputStream, Charsets.UTF_8)
        val writer = JsonWriter(outputStreamWriter)
        writer.setIndent("")

        try {
            writer.beginObject()

            Log.d(TAG, "Writing database")
            writer.name("database")

            val database = getSupportSQLiteOpenHelper(context, DB.APP.dbName).readableDatabase

            writeDatabase(writer, database)
            database.close()

            Log.d(TAG, "Writing preferences")
            writer.name("preferences")

            val pref = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
            writePreferences(writer, pref)

            writer.endObject()
            writer.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error occurred", e)
            e.printStackTrace()
            return false
        }

        Log.d(TAG, "Backup created successfully")
        return true
    }

    companion object {
        const val TAG = "PFABackupCreator"
    }
}