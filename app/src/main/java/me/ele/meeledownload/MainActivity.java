package me.ele.meeledownload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import me.ele.download.FinalHttp;
import me.ele.download.http.AjaxCallBack;
import me.ele.download.http.HttpHandler;

/**
 * Created by caoyubin on 15/3/16.
 */
public class MainActivity extends Activity {
    
    private ProgressBar progressBar;
    private TextView downloadMsg;
    
    private FinalHttp finalHttp;
    private HttpHandler<File> httpHandler;
    
    private String url = "http://gdown.baidu.com/data/wisegame/fb446df5fdbe3680/aiqiyishipin_80612.apk";
    String target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aa.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.download_progress);
        downloadMsg = (TextView) findViewById(R.id.download_msg);
        finalHttp = new FinalHttp();
    }
    
    public void start(View view) {
        if (httpHandler == null) {
            httpHandler = finalHttp.download(url,target, true, new AjaxCallBack<File>() {
                @Override
                public void onStart() {
                    super.onStart();
                    Log.e("test", "开始下载");
                    downloadMsg.setText("开始下载");
                }

                @Override
                public void onLoading(long count, long current) {
                    super.onLoading(count, current);
                    int progress = (int) (current * 100 / count);
                    progressBar.setProgress(progress);
                    downloadMsg.setText("已下载  " + progress + "%");
                }

                @Override
                public void onSuccess(File file) {
                    super.onSuccess(file);
                    Log.e("test", "下载成功");
                    downloadMsg.setText("下载成功");
                }

                @Override
                public int getRate() {
                    return super.getRate();
                }

                @Override
                public AjaxCallBack<File> progress(boolean progress, int rate) {
                    return super.progress(progress, rate);
                }

                @Override
                public boolean isProgress() {
                    return super.isProgress();
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                    Log.e("test", "errorNo:" + errorNo  + "  strMsg:" + strMsg);
                }
            });
        }
      
    }
    
    public void pause(View view) {
        if (httpHandler != null) {
            httpHandler.stop();
            httpHandler = null;
        }
    }

    public void delete(View view) {
        if (httpHandler != null) {
            new File(target).delete();
            httpHandler = null;
        }
    }
}
