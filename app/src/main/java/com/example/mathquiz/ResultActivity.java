package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathquiz.model.QuizDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaration
    RadioGroup radio_group_buttons;
    Button button_back;
    EditText edit_text_user_name;
    TextView text_view_user_score;

    ArrayList<QuizDetails> myArrayList;
    ListView listView;
    ArrayList<QuizDetails> myShowList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initialize();
        myIntent();
    }

    private void initialize() {
        listView=findViewById(R.id.listView);
        radio_group_buttons=findViewById(R.id.radio_group_buttons);

        button_back=findViewById(R.id.button_back);
        button_back.setOnClickListener(this);

        edit_text_user_name=findViewById(R.id.edit_text_user_name);
        text_view_user_score=findViewById(R.id.text_view_user_score);
    }

    private void myIntent() {
        //Get Intent and extract list and score
        Bundle bundle = getIntent().getBundleExtra("intentExtra");

        Serializable bundleUserScore = bundle.getSerializable("bundleUserScore");
        text_view_user_score.setText(bundleUserScore.toString()+" %");

        Serializable bundleMyList=bundle.getSerializable("bundleMyList");
        myArrayList= (ArrayList<QuizDetails>) bundleMyList;
        myShowList= (ArrayList<QuizDetails>) bundleMyList;

        //call to method to show ListView
        showListView(myArrayList);
    }
    @Override
    public void onClick(View v) {
        goToMainActivity();
    }

    //method to go back to Main Activity
    private void goToMainActivity() {
        // Go to the main page
        Intent intent = new Intent(this, MainActivity.class);
        String user=edit_text_user_name.getText().toString();
        //if user does not provide any name then display Thank you instead name
        if(user.equals("")){
            user="Thank you ";
        }
        intent.putExtra("username", user);
        intent.putExtra("score", text_view_user_score.getText().toString());

        startActivity(intent);
    }

    //method to display ListView depends on radio button chosen
    public void showMe(View view) {
        int selectedRadioBtn=radio_group_buttons.getCheckedRadioButtonId();
        switch (selectedRadioBtn){

            case R.id.radio_button_all:
                showAllAnswers();
                break;

            case R.id.radio_button_right:
                getOnlyRightAnswers();
                break;

            case R.id.radio_button_wrong:
                getOnlyWrongAnswers();
                break;

            case R.id.radio_button_sort_asc:
                sortResultByAsc();
                break;

            case R.id.radio_button_sort_desc:
                sortResultByDesc();
                break;
        }
    }

    //method to display ListView
    private void showListView(final ArrayList<QuizDetails> myShowList) {

        ArrayAdapter<QuizDetails> arrayAdapter = new ArrayAdapter<QuizDetails>(this,
                android.R.layout.simple_list_item_1, myShowList);

        listView.setAdapter(arrayAdapter);
        //Sets the view to show if the adapter is empty i.e display no records found message
        listView.setEmptyView(findViewById(R.id.empty));

        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listViewAdapterView, View itemView, int position, long id) {
                Toast.makeText(ResultActivity.this, "position = "+
                        position+" id = "+id, Toast.LENGTH_LONG).show();

                Intent intent=new Intent(ResultActivity.this,ShowResultItemActivity.class);
                intent.putExtra(ShowResultItemActivity.EXTRA_RESULTID,(int) id);
                intent.putExtra("text",listViewAdapterView.getItemAtPosition(position).toString());
                int image= 1;
                String str=listViewAdapterView.getItemAtPosition(position).toString();
                String[] parts = str.split("\n");
                String[] part1=parts[1].split(" : ");
                String[] part2=parts[2].split(" : ");
                if(part1[1].equals(part2[1])){
                    image=0;
                }
                intent.putExtra("image",image);
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }

    //method to display all the operations and answers
    private void showAllAnswers() {
        myShowList=new ArrayList<QuizDetails>();
        for(int i=0;i<myArrayList.size();i++){
            myShowList.add(myArrayList.get(i));
        }
        showListView(myShowList);
    }

    //method to display only the correct answers
    private void getOnlyRightAnswers() {
        myShowList=new ArrayList<QuizDetails>();
        for(int i=0;i<myArrayList.size();i++){
            if(Double.compare(myArrayList.get(i).getCorrect_answer(),myArrayList.get(i).getUser_answer())==0){
                myShowList.add(myArrayList.get(i));
            }
        }
        showListView(myShowList);
    }

    //method to display only the incorrect answers
    private void getOnlyWrongAnswers() {
        myShowList=new ArrayList<QuizDetails>();
        for(int i=0;i<myArrayList.size();i++){
            if(Double.compare(myArrayList.get(i).getCorrect_answer(),myArrayList.get(i).getUser_answer())!=0){
                myShowList.add(myArrayList.get(i));
            }
        }
        showListView(myShowList);
    }

    //method to sort the ListView in ascending
    private void sortResultByAsc() {
        //when sorting if displayed ListView is empty then display all the operations in asc
        checkListToBeSort();
        Collections.sort(myShowList);
        showListView(myShowList);
    }

    //method to sort the ListView in descending
    private void sortResultByDesc() {
        //when sorting if displayed ListView is empty then display all the operations in asc
        checkListToBeSort();
        Collections.sort(myShowList,Collections.<QuizDetails>reverseOrder());
        showListView(myShowList);
    }

    //if displayed ListView is empty show entire list
    private void checkListToBeSort() {
        if (myShowList.isEmpty() == true) {
            myShowList = new ArrayList<QuizDetails>();
            for (int i = 0; i < myArrayList.size(); i++) {
                myShowList.add(myArrayList.get(i));
            }
        }
    }
}