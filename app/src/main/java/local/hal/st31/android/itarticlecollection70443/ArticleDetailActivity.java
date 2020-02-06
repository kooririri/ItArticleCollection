package local.hal.st31.android.itarticlecollection70443;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ArticleDetailActivity extends AppCompatActivity {

    private String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        String title = bundle.getString("title");
        url = bundle.getString("url");
        String comment = bundle.getString("comment");
        int studentId = bundle.getInt("studentId");
        int seatNo = bundle.getInt("seatNo");
        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String createdAt = bundle.getString("createdAt");

        TextView titleView = findViewById(R.id.detail_title);
        TextView urlView = findViewById(R.id.detail_url);
        TextView commentView = findViewById(R.id.detail_comment);
        TextView studentIdView = findViewById(R.id.detail_student_id);
        TextView seatNoView = findViewById(R.id.detail_seat_no);
        TextView nameView = findViewById(R.id.detail_name);
        TextView timeView = findViewById(R.id.detail_time);

        Log.e("pxl",comment);
        titleView.setText("タイトル："+title);
        urlView.setText(url);
        commentView.setText("コメント："+comment);
        studentIdView.setText("学籍番号："+studentId);
        seatNoView.setText("座席番号："+seatNo);
        nameView.setText("姓名："+lastName+firstName);
        timeView.setText("投稿時間："+createdAt);
    }

    public void urlClick(View view){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
