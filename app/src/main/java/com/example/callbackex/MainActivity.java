package com.example.callbackex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity implements CopyProgressListener {


    boolean isFinish = false;

    private TextView fileNameText = null;
    private TextView notiText = null;
    private TextView progressText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fileNameText = (TextView) findViewById(R.id.textView2);
        this.notiText = (TextView) findViewById(R.id.textView);
        this.progressText = (TextView) findViewById(R.id.textView3);

        File fileCacheTempDir = new File(getCacheDir(), "temp");
        File fileCacheCopyFolderDir = new File(getCacheDir(), "copyFolder");


        File ffrompath = new File(fileCacheTempDir, "test.png");
        File ftopath = new File(fileCacheCopyFolderDir, "test.png");

       // File ffrompath = new File("/storge/emulated/0/Download/a.txt");
      //  File ftopath = new File("/storage/emulated/0/Music/a.txt");


        final CopyProgressListener listener = new CopyProgressListener(){
            @Override
            public void copyFileBegin(File fFromFile, long fileTotalLength) {

            }

            @Override
            public void copyFileProgress(long fileTotalLength, long copyTotalLength) {

            }

            @Override
            public void copyFileEnd(long fileTotalLength) {

            }

            @Override
            public void copyFileException(File fFromFile, Exception e) {

            }
        };


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FileCopyPaste.filecopy(ffrompath.getAbsolutePath(), ftopath.getAbsolutePath(), MainActivity.this)){
                     Toast.makeText(MainActivity.this, "복사성공", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "복사실패", Toast.LENGTH_LONG).show();
                }

                FileCopyPaste.filecopy(ffrompath.getAbsolutePath(), ftopath.getAbsolutePath(), listener);


            }
        });
    }

    @Override
    public void copyFileBegin(File fFromFile, long fileTotalLength) {
        isFinish = false;
        fileNameText.setText(fFromFile.getName());
        notiText.setText("copy 시작");
        Log.d("MainActivity", "FileCopy Start~!");
        Toast.makeText(MainActivity.this, "시작", Toast.LENGTH_LONG).show();

    }
    @Override
    public void copyFileProgress(long fileTotalLength, long copyTotalLength) {
        isFinish = false;
        notiText.setText("copy 진행중..");
        progressText.setText(copyTotalLength + "/" + fileTotalLength + "(" + ((float)copyTotalLength / (float)fileTotalLength) * 100 + "%)");
        Log.d("MainActivity", "FileCopy Progress : " + copyTotalLength + "/" + fileTotalLength);
    }

    @Override
    public void copyFileEnd(long fileTotalLength) {
        isFinish = true;
        notiText.setText("copy 종료");
        Toast.makeText(MainActivity.this, "복사 성공!", Toast.LENGTH_LONG).show();
        Log.d("MainActivity", "FileCopy End");
    }

    @Override
    public void copyFileException(File fToFile, Exception e) {
        isFinish = true;
        e.printStackTrace();
        Log.d("MainActivity", "FileCopy Error : " + e.getMessage());
    }

}
