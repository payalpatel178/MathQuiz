package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowResultItemActivity extends AppCompatActivity {

    //Declaration
    public static final String EXTRA_RESULTID = "resultId";
    ImageView photo;
    TextView tv_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result_item);
        initialize();
        getMyIntent();
    }

    private void initialize() {
        photo=findViewById(R.id.photo);
        tv_text=findViewById(R.id.tv_text);
    }

    private void getMyIntent() {
        //Get Intent and extract value
        String str=getIntent().getStringExtra("text");
        tv_text.setText(str);
        int image=getIntent().getIntExtra("image",0);
        if(image==0){
            photo.setImageResource(R.drawable.correct);
        }
        else{
            photo.setImageResource(R.drawable.incorrect);
        }
    }
}