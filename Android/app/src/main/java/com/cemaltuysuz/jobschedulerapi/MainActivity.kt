package com.cemaltuysuz.jobschedulerapi

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val tag = "MAIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)}


    fun transferStartButton(view: View) {
        Log.d(tag,"onStart Clicked")
        // Required parameters
        val service:JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val serviceName = ComponentName(packageName, DataTransferService::class.java.name)

        // Optional parameters
        val bundle = PersistableBundle()
        bundle.putString("key","xxx")

        val jobInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            JobInfo.Builder(Constants.DATA_TRANSFER_SERVICE_UUID, serviceName)
                .setExtras(bundle)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresBatteryNotLow(true)
                .build()
        } else {
            JobInfo.Builder(Constants.DATA_TRANSFER_SERVICE_UUID, serviceName)
                .setExtras(bundle)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(true)
                .build()
        }
        service.schedule(jobInfo)
    }

    fun transferStopButton(view: View) {
        Log.d(tag,"onStop Clicked")
        val service:JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        service.cancel(Constants.DATA_TRANSFER_SERVICE_UUID)
    }
}