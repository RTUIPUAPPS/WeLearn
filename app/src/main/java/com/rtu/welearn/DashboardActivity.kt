package com.rtu.welearn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.Sheet
import com.rtu.welearn.ui.about.AboutActivity
import com.rtu.welearn.ui.tips.TipsActivity
import com.rtu.welearn.utils.AppUtils.isInternetAvailable
import com.rtu.welearn.utils.AppUtils.showToast
import com.rtu.welearn.databinding.ActivityDashboardBinding
import com.rtu.welearn.ui.firebase.FirebaseMainActivity
import com.rtu.welearn.ui.test.TestActivity

class DashboardActivity : BaseActivity() {
    companion object {
        private const val REQUEST_SIGN_IN = 1
    }

    val findSheetName = "Sheet1"
    var binding: ActivityDashboardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        binding?.cvAbout?.setOnClickListener {
            if (isInternetAvailable(applicationContext)) {
                launchActivity(AboutActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
        }
        binding?.cvMaterials?.setOnClickListener { launchActivity(MaterialsActivity.getIntent(this)) }
        binding?.cvTips?.setOnClickListener {
            if (isInternetAvailable(applicationContext)) {
                launchActivity(TipsActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
        }
        binding?.cvPractice?.setOnClickListener { launchActivity(ToolkitsActivity.getIntent(this)) }
        binding?.cvTest?.setOnClickListener {

            if (isInternetAvailable(applicationContext)) {
                launchActivity(TestActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
        }
        binding?.cvVideo?.setOnClickListener {
            if (isInternetAvailable(applicationContext)) {
                launchActivity(VideoPlayerActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
             }
        binding?.ivWeLearn?.setOnClickListener {

            launchActivity(FirebaseMainActivity.getIntent(this))
        }

//        requestSignIn()
//        val scopes = listOf(SheetsScopes.SPREADSHEETS)
//        val credential = GoogleAccountCredential.usingOAuth2(this, scopes)
////        credential.selectedAccount = account.account
//
//        val jsonFactory = JacksonFactory.getDefaultInstance()
//        // GoogleNetHttpTransport.newTrustedTransport()
//        val httpTransport =  AndroidHttp.newCompatibleTransport()
//
//        val service = Sheets.Builder(httpTransport, jsonFactory, credential)
//            .setApplicationName(getString(R.string.app_name))
//            .build()
//
//        val spreadsheet = service.spreadsheets().get("10ngWL-vlw4Micg0UjKKiqisr9SjEC7qeDVtGkJ7qaGU").execute()
//        val sheet = if (spreadsheet.sheets.size == 1) {
//            spreadsheet.sheets[0]
//        }else {
//            var defaultSheet: Sheet? = null
//            var findSheet: Sheet? = null
//            for (sheet in spreadsheet.sheets) {
//                if (sheet.properties.sheetId == 0) {
//                    defaultSheet = sheet
//                }
//                else if (sheet.properties.title == findSheetName) {
//                    findSheet = sheet
//                }
//            }
//
//            findSheet ?: defaultSheet!!
//        }
//
//        val sheetName = sheet.properties.title
//        val sheetId = sheet.properties.sheetId
    }

    private fun requestSignIn() {
        /*
        GoogleSignIn.getLastSignedInAccount(context)?.also { account ->
            Timber.d("account=${account.displayName}")
        }
         */

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // .requestEmail()
            // .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .build()
        val client = GoogleSignIn.getClient(this, signInOptions)


        startActivityForResult(client.signInIntent, REQUEST_SIGN_IN)
    }

//    private fun createSpreadsheet(service: Sheets) {
//        var spreadsheet = Spreadsheet()
//            .setProperties(
//                SpreadsheetProperties()
//                    .setTitle("CreateNewSpreadsheet")
//            )
//
//        launch(Dispatchers.Default) {
//            spreadsheet = service.spreadsheets().create(spreadsheet).execute()
//            Timber.d("ID: ${spreadsheet.spreadsheetId}")
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(data)
                    .addOnSuccessListener { account ->
                        val scopes = listOf(SheetsScopes.SPREADSHEETS)
                        val credential = GoogleAccountCredential.usingOAuth2(this, scopes)
                        credential.selectedAccount = account.account

                        val jsonFactory = JacksonFactory.getDefaultInstance()
                        // GoogleNetHttpTransport.newTrustedTransport()
                        val httpTransport = AndroidHttp.newCompatibleTransport()
                        val service = Sheets.Builder(httpTransport, jsonFactory, credential)
                            .setApplicationName(getString(R.string.app_name))
                            .build()


//                        val scopes = listOf(SheetsScopes.SPREADSHEETS)
//        val credential = GoogleAccountCredential.usingOAuth2(this, scopes)
//        credential.selectedAccount = account.account

//        val jsonFactory = JacksonFactory.getDefaultInstance()
                        // GoogleNetHttpTransport.newTrustedTransport()
//        val httpTransport =  AndroidHttp.newCompatibleTransport()

//        val service = Sheets.Builder(httpTransport, jsonFactory, credential)
//            .setApplicationName(getString(R.string.app_name))
//            .build()

                        val spreadsheet = service.spreadsheets()
                            .get("10ngWL-vlw4Micg0UjKKiqisr9SjEC7qeDVtGkJ7qaGU").execute()
                        val sheet = if (spreadsheet.sheets.size == 1) {
                            spreadsheet.sheets[0]
                        } else {
                            var defaultSheet: Sheet? = null
                            var findSheet: Sheet? = null
                            for (sheet in spreadsheet.sheets) {
                                if (sheet.properties.sheetId == 0) {
                                    defaultSheet = sheet
                                } else if (sheet.properties.title == findSheetName) {
                                    findSheet = sheet
                                }
                            }

                            findSheet ?: defaultSheet!!
                        }

                        val sheetName = sheet.properties.title
                        val sheetId = sheet.properties.sheetId

                    }
                    .addOnFailureListener { e ->
                        Log.e("", "$e")
                    }
            }
        }
    }
}