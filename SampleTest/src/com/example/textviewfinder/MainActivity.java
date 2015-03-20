
package com.example.textviewfinder;

import java.io.File;
import java.util.ArrayList;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.amida.easydb.objectsql.Select;
import com.amida.easyhttp.ResponseListener;
import com.amida.easyhttp.ServiceFactory;
import com.amida.easyhttp.ServiceFactory.ServiceType;
import com.amida.easylog.EasyLog;
import com.example.textviewfinder.dbbean.Member;
import com.example.textviewfinder.dbbean.Team;
import com.example.textviewfinder.request.RequestDownPic;
import com.example.textviewfinder.request.RequestIpInfo;
import com.example.textviewfinder.response.ResponseIpInfo;
import com.example.textviewfinder.service.TestService;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {
    protected TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = (TextView) findViewById(R.id.hello);
        hello.setText("abc");

        // testEasyDownload();
        // testRequestJson();
        // testRequestJson();
        // testDownload();
        testDb();

    }

    private void testEasyDownload() {
        final String picPath = Environment.getExternalStorageDirectory() + File.separator + "dddd.png";
        EasyLog.d("path: " + picPath);
        File picFile = new File(picPath);

        RequestDownPic request = new RequestDownPic();
        ResponseListener responseListener = new ResponseListener<File>() {

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {

            }
        };

        ((TestService) ServiceFactory.create(this, ServiceType.DUMMY, TestService.class)).downloadPic(request, responseListener, picFile);
    }

    private void testRequestJson() {
        RequestIpInfo request = new RequestIpInfo();
        request.setFormat("json");
        ResponseListener responseListener = new ResponseListener<ResponseIpInfo>() {

            @Override
            public void onSuccess(ResponseIpInfo response) {
                super.onSuccess(response);
                EasyLog.d("response:" + new Gson().toJson(response));
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {

            }

        };

        ((TestService) ServiceFactory.create(this, ServiceType.NET, TestService.class)).getIpInfo(request, responseListener);
    }

    private void testDownload() {
        File dirFile = getDir("my_download", Context.MODE_PRIVATE);
        final String zipPath = dirFile.getAbsolutePath() + File.separator + "downloadZip.zip";
        // final String zipPath = Environment.getExternalStorageDirectory() +
        // File.separator + "downloadZip.zip";
        EasyLog.d("path: " + zipPath);
        File zipFile = new File(zipPath);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30 * 1000);
        client.setURLEncodingEnabled(false);
        FileAsyncHttpResponseHandler handler = new FileAsyncHttpResponseHandler(zipFile) {

            @Override
            public void onSuccess(int arg0, Header[] arg1, File arg2) {
                EasyLog.d("onSuccess:" + arg2.getAbsolutePath());
            }

            @Override
            public void onFailure(int errorCode, Header[] arg1, Throwable throwable, File arg3) {
                EasyLog.d("onFailure: " + errorCode + throwable != null ? ":" + throwable.getMessage() : "");
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int progress = (bytesWritten * 100) / totalSize;
                EasyLog.d("progress: " + progress);
            }
        };

        RequestParams params = new RequestParams();
        params.put("params", "{mobileNo=15202804547}");
        client.get("http://192.168.20.244:8080/invest/mobile/downloadZip.do", params, handler);

    }

    private void testDb() {

        
        ArrayList<Member> members = new ArrayList<Member>();

        Member member = new Member();
        member.setAge(2);
        member.setName("liao");
        members.add(member);

        member = new Member();
        member.setAge(3);
        member.setName("liao");
        members.add(member);

        Team team = new Team();
        team.setTeamLeader("liaoqianchuan");
        team.setTeamName("OneTeam");
        team.setMembers(members);

        team.save();

        ArrayList<Team> teams = new Select().from(Team.class).execute();
        if (teams != null) {
            for (Team tmpTeam : teams) {
                ArrayList<Member> tmpMembers = tmpTeam.getMembers();
                if (tmpMembers != null) {
                    for (Member tmpMember : tmpMembers) {
                        EasyLog.d(new Gson().toJson(tmpMember));
                    }
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
