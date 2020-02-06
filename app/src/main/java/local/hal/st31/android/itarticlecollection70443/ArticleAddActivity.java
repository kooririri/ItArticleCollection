package local.hal.st31.android.itarticlecollection70443;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ArticleAddActivity extends AppCompatActivity {
    private static final String URL = "http://hal.architshin.com/st31/insertItArticle.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_add);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btnSave){
            EditText etTitle = findViewById(R.id.etTitle);
            String title = etTitle.getText().toString();
            EditText etUrl = findViewById(R.id.etUrl);
            String url = etUrl.getText().toString();
            EditText etComment = findViewById(R.id.etComment);
            String comment = etComment.getText().toString();
            AddThread addThread = new AddThread();
            addThread.execute(URL,title,url,comment);
        }
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AddThread extends AsyncTask<String, Void, String> {
        private static final String DEBUG_TAG = "addThread";

        @Override
        protected String doInBackground(String... params) {
            String uri = params[0];
            String strTitle = params[1];
            String strUrl = params[2];
            String strComment = params[3];
            String lastname = "朴";
            String firstname = "祥璐";
            int studentid = 70443;
            int seatno = 13;

            String postData ="title="+strTitle+"&url="+strUrl+"&comment="+strComment+"&lastname="+lastname+"&firstname="+firstname+"&studentid="+studentid+"&seatno="+seatno;
            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";

            try {
                java.net.URL url = new URL(uri);
                con = (HttpURLConnection) url.openConnection();
//                con.setRequestProperty("Content-Type", "application/json; utf-8"); // 追記
//                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");
                con.setConnectTimeout(2000);
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();
                int status = con.getResponseCode();
                if(status != 200){
                    throw new IOException("ステータスコード："+status);
                }
                is = con.getInputStream();
                result = is2String(is);
            } catch (MalformedURLException ex) {
                Log.e(DEBUG_TAG, "URL変換失敗", ex);
            } catch (IOException ex) {
                Log.e(DEBUG_TAG, "通信失敗", ex);
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        Log.e(DEBUG_TAG, "InputStream解放失敗", ex);
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                int status = object.getInt("status");
                String msg = object.getString("msg");
                if(status == 0){
                    Bundle extras = new Bundle();
                    extras.putString("msg",msg);
                    ErrorDialogFragment dialog = new ErrorDialogFragment();
                    dialog.setArguments(extras);
                    FragmentManager manager = getSupportFragmentManager();
                    dialog.show(manager,"ErrorDialogFragment");
                }
                else{
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }



    private String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while (0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }
}
