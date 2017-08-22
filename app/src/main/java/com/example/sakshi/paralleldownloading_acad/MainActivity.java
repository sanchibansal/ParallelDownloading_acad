package com.example.sakshi.paralleldownloading_acad;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Declaring buttons and  progress bars
    Button startDownload;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    StartDownload startFirstDownload, startSecondDownload,  startthirdDownload, startfourthDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //associating buttons and progressbars
        startDownload = (Button)findViewById(R.id.download);
        progressBar1 = (ProgressBar) findViewById(R.id.download1);
        progressBar2 = (ProgressBar) findViewById(R.id.download2);
        progressBar3 = (ProgressBar) findViewById(R.id.download3);
        progressBar4 = (ProgressBar) findViewById(R.id.download4);

        //on click listener for button
        startDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executing first asynctask
                startFirstDownload = new StartDownload(progressBar1);
                startFirstDownload.execute();
                startSecondDownload = new StartDownload(progressBar2);
                //starting this task in parallel
                StartAsyncTaskInParallel(startSecondDownload);
                startthirdDownload = new StartDownload(progressBar3);
                startthirdDownload.execute();
                startfourthDownload = new StartDownload(progressBar4);
                StartAsyncTaskInParallel(startfourthDownload);


            }
        });

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(StartDownload task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            //enabling multi asyncTask
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }
    class StartDownload extends AsyncTask<Void,Integer,Void>{
        ProgressBar progressBar;
        public StartDownload(ProgressBar target) {
            progressBar= target;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //setting delay and publishing progress on progressbar
            for(int i=0;i<11;i++){
                sleep();
                publishProgress(i*10);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            //setting visibility of the progress bar
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            //updating progress of the progress bar
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //displaying a toast on completion of the task
            Toast.makeText(MainActivity.this, "Download completed!", Toast.LENGTH_SHORT).show();
        }

        public void sleep(){
            try{
                Thread.sleep(500);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

}
