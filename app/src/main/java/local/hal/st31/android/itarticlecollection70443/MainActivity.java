package local.hal.st31.android.itarticlecollection70443;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://hal.architshin.com/st31/getItArticlesList.php";
    RecyclerView recyclerView;
    ListAdapter adapter;
    List<Bean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        list = new ArrayList<>();
        ListReceiver listReceiver = new ListReceiver();
        listReceiver.execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btnNew){
            Intent intent = new Intent(getApplicationContext(),ArticleAddActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    private class ListReceiver extends AsyncTask<String,Void,String>{

        private static final String DEBUG_TAG = "ListReceiver";
        @Override
        protected String doInBackground(String... params) {
            String urlStr = params[0];
            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";
            try {
                URL url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();

                result = is2String(is);

            } catch (MalformedURLException ex) {
                Log.e(DEBUG_TAG,"URL変換失敗",ex);
            } catch (IOException ex) {
                Log.e(DEBUG_TAG,"通信失敗",ex);
            }
            finally {
                if(con != null){
                    con.disconnect();
                }
                if(is!=null){
                    try{
                        is.close();
                    }
                    catch (IOException ex){
                        Log.e(DEBUG_TAG,"InputStream解放失敗",ex);
                    }
                }
            }
            return  result;
        }

        @Override
        protected void onPostExecute(String result) {
            int status = 0;
            String msg = "";
            try {
                JSONObject jsonObject = new JSONObject(result);
                status = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject dataObject = jsonArray.getJSONObject(i);
                    Bean bean = new Bean();
                    bean.setId(dataObject.getInt("id"));
                    bean.setTitle(dataObject.getString("title"));
                    bean.setUrl(dataObject.getString("url"));
                    bean.setComment(dataObject.getString("comment"));
                    bean.setStudent_id(dataObject.getInt("student_id"));
                    bean.setSeat_no(dataObject.getInt("seat_no"));
                    bean.setLast_name(dataObject.getString("last_name"));
                    bean.setFirst_name(dataObject.getString("first_name"));
                    bean.setCreated_at(dataObject.getString("created_at"));
                    list.add(bean);
                    Log.e(DEBUG_TAG,bean.getTitle());
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new ListAdapter(getApplicationContext());
                adapter.setData(list);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bean itemData = list.get(position);
                        Intent intent = new Intent(getApplicationContext(),ArticleDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title",itemData.getTitle());
                        bundle.putString("url",itemData.getUrl());
                        bundle.putString("comment",itemData.getComment());
                        bundle.putInt("studentId",itemData.getStudent_id());
                        bundle.putInt("seatNo",itemData.getSeat_no());
                        bundle.putString("lastName",itemData.getLast_name());
                        bundle.putString("firstName",itemData.getFirst_name());
                        bundle.putString("createdAt",itemData.getCreated_at());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

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
