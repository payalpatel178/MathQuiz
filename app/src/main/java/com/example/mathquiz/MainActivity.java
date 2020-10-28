package com.example.mathquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathquiz.model.QuizDetails;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaration
    TextView text_view_quiz,text_view_equation;
    EditText edit_text_user_answer;
    Button button_one,button_two,button_three,button_four,button_five,button_six;
    Button button_seven,button_eight,button_nine,button_zero,button_dot,button_minus;
    Button button_generate,button_validate,button_clear;
    Button button_score,button_finish;
    private Double userAnswer,rightResult;
    private int score=0,scorePercentage=0;

    ArrayList<QuizDetails> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        myGetIntent();
    }

    private void initialize() {
        myList = new ArrayList<QuizDetails>();
        text_view_quiz=findViewById(R.id.text_view_quiz);
        text_view_equation=findViewById(R.id.text_view_equation);
        edit_text_user_answer=findViewById(R.id.edit_text_user_answer);

        button_one=findViewById(R.id.button_one);
        button_one.setOnClickListener(this);

        button_two=findViewById(R.id.button_two);
        button_two.setOnClickListener(this);

        button_three=findViewById(R.id.button_three);
        button_three.setOnClickListener(this);

        button_four=findViewById(R.id.button_four);
        button_four.setOnClickListener(this);

        button_five=findViewById(R.id.button_five);
        button_five.setOnClickListener(this);

        button_six=findViewById(R.id.button_six);
        button_six.setOnClickListener(this);

        button_seven=findViewById(R.id.button_seven);
        button_seven.setOnClickListener(this);

        button_eight=findViewById(R.id.button_eight);
        button_eight.setOnClickListener(this);

        button_nine=findViewById(R.id.button_nine);
        button_nine.setOnClickListener(this);

        button_zero=findViewById(R.id.button_zero);
        button_zero.setOnClickListener(this);

        button_dot=findViewById(R.id.button_dot);
        button_dot.setOnClickListener(this);

        button_minus=findViewById(R.id.button_minus);
        button_minus.setOnClickListener(this);

        button_generate=findViewById(R.id.button_generate);
        button_generate.setOnClickListener(this);

        button_validate=findViewById(R.id.button_validate);
        button_validate.setOnClickListener(this);

        button_clear=findViewById(R.id.button_clear);
        button_clear.setOnClickListener(this);

        button_score=findViewById(R.id.button_score);
        button_score.setOnClickListener(this);

        button_finish=findViewById(R.id.button_finish);
        button_finish.setOnClickListener(this);
    }

    private void myGetIntent() {
        // 1- Get Intent and extract username and score
        String username = getIntent().getStringExtra("username");
        String score = getIntent().getStringExtra("score");

        String strResult = username+ " : "+score;
        if(username!=null && score!=null){
            //set username and score in TextView
            text_view_quiz.setText(strResult);
        }
    }

    @Override
    public void onClick(View v) {

        String userInput="";
        switch (v.getId()){
            case R.id.button_one:
                userInput="1";
                break;

            case R.id.button_two:
                userInput="2";
                break;

            case R.id.button_three:
                userInput="3";
                break;

            case R.id.button_four:
                userInput="4";
                break;

            case R.id.button_five:
                userInput="5";
                break;

            case R.id.button_six:
                userInput="6";
                break;

            case R.id.button_seven:
                userInput="7";
                break;

            case R.id.button_eight:
                userInput="8";
                break;

            case R.id.button_nine:
                userInput="9";
                break;

            case R.id.button_dot:
                userInput=".";
                //disabling point button after one click
                button_dot.setEnabled(false);
                break;

            case R.id.button_zero:
                userInput="0";
                break;

            case R.id.button_minus:
                userInput="-";
                break;

            case R.id.button_generate:
                goToGenerate();
                break;

            case R.id.button_validate:
                goToValidate();
                break;

            case R.id.button_clear:
                goToClear();
                break;

            case R.id.button_score:
                goToResultActivity();
                break;

            case R.id.button_finish:
                goToFinish();
                break;

        }
        //setting edit text depends on user input
        edit_text_user_answer.setText(edit_text_user_answer.getText().toString()+userInput);
        //disabling negative sign button
        if(edit_text_user_answer.length()>0){
            button_minus.setEnabled(false);
        }
    }

    //method to generate random operation/equation
    private void goToGenerate() {
        //Generate two random numbers
        Random random=new Random();
        int operand1=random.nextInt(10);
        int operand2=random.nextInt(10);

        String operator="";
        int op=random.nextInt(4);

        //handing Arithmetic exception: division by zero error
        while(operand2==0 && op==3) {
            operand2 = random.nextInt(10);
        }
        switch (op){
            case 0:
                operator="+";
                rightResult= Double.valueOf(operand1+operand2);
                break;

            case 1:
                operator="-";
                rightResult= Double.valueOf(operand1-operand2);
                break;

            case 2:
                operator="*";
                rightResult= Double.valueOf(operand1*operand2);
                break;

            case 3:
                operator="/";
                rightResult= Double.valueOf(operand1)/operand2;
                break;
        }
        String equation=String.valueOf(operand1)+" "+operator+" "+String.valueOf(operand2)+" = ? ";
        //setting text view for equation
        text_view_equation.setText(equation);
        //clearing edit text for user input
        goToClear();
    }

    //method to validate user input with actual answer
    private void goToValidate() {

        String strResult;
        //checking if operation is not generated then display message
        if(text_view_equation.getText().toString().equals("")){
            showDialog("Please click on Generate to generate the equation");
            //Toast.makeText(this, "Please click on Generate to generate the equation", Toast.LENGTH_SHORT).show();
        }
        //checking if user has not provide any input
        else if(edit_text_user_answer.getText().toString().equals("")){
            showDialog("Please provide input to validate with the equation");
            //Toast.makeText(this, "Please provide input to validate with the equation", Toast.LENGTH_SHORT).show();
        }
        else{
            //validate
            userAnswer = Double.valueOf(edit_text_user_answer.getText().toString());
            Double value1=Double.parseDouble(String.format("%.2f",rightResult));
            Double value2=Double.parseDouble(String.format("%.2f",userAnswer));
            if (Double.compare(value1,value2)==0) {
                strResult = "Correct Answer";
                score+=1;
            } else {
                strResult = "Wrong, the correct answer was: "+String.format("%.2f",rightResult);
            }
            //Toast the result
            Toast.makeText(this, strResult, Toast.LENGTH_SHORT).show();
            //call to method to add the operation into list
            addToList();
            //clear user input edit text
            goToClear();
            //clear operation text view
            text_view_equation.setText("");
            //enable the score button
            button_score.setEnabled(true);
        }
    }

    //method to display message
    private void showDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s)
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //method to add operation with user input and actual result into list
    private void addToList() {
        String question=text_view_equation.getText().toString();
        Double correct_answer=Double.valueOf(String.format("%.2f",rightResult));
        Double user_answer=userAnswer;
        QuizDetails details=new QuizDetails(question,correct_answer,user_answer);
        myList.add(details);
    }

    //method to clear user input and enable the point and negative sign button
    private void goToClear() {
        edit_text_user_answer.setText("");
        button_dot.setEnabled(true);
        button_minus.setEnabled(true);
    }

    //method to go to result activity
    private void goToResultActivity() {

        if(myList.isEmpty()==false){
            // Go to the result page
            Bundle bundle=new Bundle();
            bundle.putSerializable("bundleMyList",myList);
            scorePercentage=(int)((score*100)/myList.size());
            bundle.putSerializable("bundleUserScore",scorePercentage);

            Intent intent=new Intent(this,ResultActivity.class);
            intent.putExtra("intentExtra",bundle);
            startActivity(intent);
        }
        else{
            button_score.setEnabled(false);
        }
    }

    //method to close the application
    //finishAffinity() -> Closes all the activities present in the current Stack
    //finish() -> Closes only opened activity
    private void goToFinish() {
        finishAffinity();
    }
}