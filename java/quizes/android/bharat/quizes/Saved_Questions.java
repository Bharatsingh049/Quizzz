package quizes.android.bharat.quizes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import quizes.android.bharat.quizes.data.User_contract.Quiz_entry;
import quizes.android.bharat.quizes.data.User_dbhelper;

public class Saved_Questions extends AppCompatActivity {
private TextView Saved_question,Saved_choice3,Saved_choice1,
        Saved_choice2,Saved_choice4,Answer_view;
private int count=0,view_count,Last_position,List_count=0;
private Random r;
private Toolbar toolbar;
private Boolean Flag=false;
private User_dbhelper m_dbhelper;
private String Saved_Category,Present_Question;
//private Cursor cursor;
private SQLiteDatabase dbw;
private ArrayList<String> Question_list,Correct_list,Incorrect_first_list,
        Incorrect_second_list,Incorrect_third_list,Category_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved__questions);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        r=new Random();
        m_dbhelper=new User_dbhelper(Saved_Questions.this);
        dbw=m_dbhelper.getWritableDatabase();
        Answer_view=(TextView)findViewById(R.id.Answer_view);
        Category_list=new ArrayList<>();
        Question_list=new ArrayList<>();
        Correct_list=new ArrayList<>();
        Incorrect_first_list=new ArrayList<>();
        Incorrect_second_list=new ArrayList<>();
        Incorrect_third_list=new ArrayList<>();
        //Cur_current_pos=getIntent().getIntExtra("count",0);
        m_dbhelper=new User_dbhelper(Saved_Questions.this);
        Saved_question=(TextView)findViewById(R.id.Saved_question_view);
        Saved_choice3=(TextView)findViewById(R.id.Saved_Choice3_view);
        Saved_choice1=(TextView)findViewById(R.id.Saved_Choice1_view);
        Saved_choice2=(TextView)findViewById(R.id.Saved_Choice2_view);
        Saved_choice4=(TextView)findViewById(R.id.Saved_Choice4_view);
        Fetching_from_DataBase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbw.delete(Quiz_entry.Table_Name,Quiz_entry.Column_Quiz_Question + "=?", new String[]{ Question_list.get(List_count)});
                Category_list.remove(List_count);
                Question_list.remove(List_count);
                Correct_list.remove(List_count);
                Incorrect_first_list.remove(List_count);
                Incorrect_second_list.remove(List_count);
                Incorrect_third_list.remove(List_count);
                List_count++;

                Snackbar.make(view, "That Question was deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void Fetching_from_DataBase() {
        SQLiteDatabase db = m_dbhelper.getReadableDatabase();
        String[] Projection = {
                Quiz_entry.Id,
                Quiz_entry.Column_Quiz_Question_Category,
                Quiz_entry.Column_Quiz_Question,
                Quiz_entry.Column_Quiz_Correct_Answer,
                Quiz_entry.Column_Quiz_Incorrect_First,
                Quiz_entry.Column_Quiz_Incorrect_Second,
                Quiz_entry.Column_Quiz_Incorrect_Third
        };

       Cursor cursor = db.query(Quiz_entry.Table_Name,
                Projection,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
/*
        if (cursor.isBeforeFirst()){
            cursor.moveToFirst();
            //Cur_current_pos=cursor.getPosition();
           // Cur_get_count=cursor.getCount();
           }else {
            cursor.moveToPosition(Cur_current_pos);}
*/      int i=0;
        while(!cursor.isAfterLast()){
               Category_list.add(i,cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Question_Category)));
               Question_list.add(i,cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Question)));
               Correct_list.add(i,cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Correct_Answer)));
               Incorrect_first_list.add(i,cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_First)));
               Incorrect_second_list.add(i,cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Second)));
               Incorrect_third_list.add(i,cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Third)));


           /* Saved_question.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Question)));
            if(view_count==1){
                Saved_choice1.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_First)));
                Saved_choice2.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Second)));
                Saved_choice3.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Correct_Answer)));
                //Saved_choice4.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Third)));
            }else if(view_count==2){
                Saved_choice3.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_First)));
                Saved_choice2.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Second)));
                Saved_choice1.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Correct_Answer)));
                //Saved_choice4.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Third)));
            }else if(view_count==3){
                Saved_choice1.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_First)));
                Saved_choice3.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Second)));
                Saved_choice2.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Correct_Answer)));
                //Saved_choice4.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Third)));
            }else if(view_count==4){
                Saved_choice1.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_First)));
                Saved_choice2.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Second)));
                Saved_choice4.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Correct_Answer)));
                //Saved_choice3.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Third)));
            }else{
                Saved_choice1.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_First)));
                Saved_choice4.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Second)));
                Saved_choice3.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Correct_Answer)));
                //Saved_choice2.setText(cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Incorrect_Third)));
            }

*/
        i++;
        cursor.moveToNext();
        }
        view_count=r.nextInt(5);
        Last_position=i;
        Log.d("Last position",""+Last_position);
        Log.d("Current_position",""+List_count);
        Present_Question=Question_list.get(List_count);
        //toolbar.setTitle(Category_list.get(List_count));
        //setSupportActionBar(toolbar);
        Saved_question.setText(Question_list.get(List_count));
        //Answer_view.setText("Answer : "+Correct_list.get(List_count));
        if(view_count==1){
            Saved_choice1.setText(Correct_list.get(List_count));
            Saved_choice1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Saved_choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice2.setText(Incorrect_first_list.get(List_count));
            Saved_choice3.setText(Incorrect_second_list.get(List_count));
            Saved_choice4.setText(Incorrect_third_list.get(List_count));
        }else if(view_count==2){
            Saved_choice3.setText(Incorrect_second_list.get(List_count));
            Saved_choice2.setText(Correct_list.get(List_count));
            Saved_choice2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Saved_choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice1.setText(Incorrect_first_list.get(List_count));
            Saved_choice4.setText(Incorrect_third_list.get(List_count));
        }else if(view_count==3){
            Saved_choice1.setText(Incorrect_first_list.get(List_count));
            Saved_choice2.setText(Incorrect_second_list.get(List_count));
            Saved_choice3.setText(Correct_list.get(List_count));
            Saved_choice3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Saved_choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice4.setText(Incorrect_third_list.get(List_count));
        }else if(view_count==4){
            Saved_choice1.setText(Correct_list.get(List_count));
            Saved_choice1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Saved_choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice2.setText(Incorrect_second_list.get(List_count));
            Saved_choice4.setText(Incorrect_first_list.get(List_count));
            Saved_choice3.setText(Incorrect_third_list.get(List_count));
        }else{
            Saved_choice1.setText(Incorrect_first_list.get(List_count));
            Saved_choice4.setText(Correct_list.get(List_count));
            Saved_choice4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Saved_choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            Saved_choice3.setText(Incorrect_second_list.get(List_count));
            Saved_choice2.setText(Incorrect_second_list.get(List_count));
        }
    }

    public void Next_button(View view){



        if(List_count<Last_position-1){
            Log.d("Next_Current",""+List_count);
            List_count++;
            Log.d("Next_Incremented",""+List_count);
            view_count=r.nextInt(5);
            //toolbar.setTitle(Category_list.get(List_count));
            //setSupportActionBar(toolbar);
            Present_Question=Question_list.get(List_count);
            Saved_question.setText(handleEscapeCharacter(Question_list.get(List_count)));
           // Answer_view.setText("Answer : "+Correct_list.get(List_count));
            if(view_count==1){
                Saved_choice1.setText(Correct_list.get(List_count));
                Saved_choice1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                Saved_choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice2.setText(Incorrect_first_list.get(List_count));
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice4.setText(Incorrect_second_list.get(List_count));
            }else if(view_count==2){
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice2.setText(Correct_list.get(List_count));
                Saved_choice2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                Saved_choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice1.setText(Incorrect_first_list.get(List_count));
                Saved_choice4.setText(Incorrect_third_list.get(List_count));
            }else if(view_count==3){
                Saved_choice1.setText(Incorrect_first_list.get(List_count));
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice2.setText(Correct_list.get(List_count));
                Saved_choice2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                Saved_choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice4.setText(Incorrect_third_list.get(List_count));
            }else if(view_count==4){
                Saved_choice3.setText(Correct_list.get(List_count));
                Saved_choice3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                Saved_choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice2.setText(Incorrect_second_list.get(List_count));
                Saved_choice4.setText(Incorrect_first_list.get(List_count));
                Saved_choice1.setText(Incorrect_third_list.get(List_count));
            }else{
                Saved_choice1.setText(Incorrect_first_list.get(List_count));
                Saved_choice4.setText(Correct_list.get(List_count));
                Saved_choice4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                Saved_choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice2.setText(Incorrect_third_list.get(List_count));
            }

        }
        /*
        if(cursor.isAfterLast()){
            Toast.makeText(Saved_Questions.this," This is your last saved question",Toast.LENGTH_LONG).show();
        }else {
            cursor.moveToNext();
            Intent intent=new Intent(this,Saved_Questions.class);
            intent.putExtra("count",cursor.getPosition());
            startActivity(intent);
        }
       */
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(Saved_Questions.this,MainActivity.class);
        startActivity(in);
    }

    public void Previous_button(View view){


        view_count=r.nextInt(5);

        if(List_count>0){
            Log.d("Previous_Current",""+List_count);
            List_count--;
            Log.d("Previous_Decrement",""+List_count);
            //toolbar.setTitle(Category_list.get(List_count));
            //setSupportActionBar(toolbar);
            //Present_Question=Question_list.get(List_count);
            Saved_question.setText(handleEscapeCharacter(Question_list.get(List_count)));
            //Answer_view.setText("Answer : "+Correct_list.get(List_count));
            if(view_count==1){
                Saved_choice1.setText(Correct_list.get(List_count));
                Saved_choice2.setText(Incorrect_first_list.get(List_count));
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice4.setText(Incorrect_third_list.get(List_count));
            }else if(view_count==2){
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice2.setText(Correct_list.get(List_count));
                Saved_choice1.setText(Incorrect_first_list.get(List_count));
                Saved_choice4.setText(Incorrect_third_list.get(List_count));
            }else if(view_count==3){
                Saved_choice1.setText(Incorrect_first_list.get(List_count));
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice2.setText(Correct_list.get(List_count));
                Saved_choice4.setText(Incorrect_third_list.get(List_count));
            }else if(view_count==4){
                Saved_choice1.setText(Correct_list.get(List_count));
                Saved_choice2.setText(Incorrect_second_list.get(List_count));
                Saved_choice4.setText(Incorrect_first_list.get(List_count));
                Saved_choice3.setText(Incorrect_third_list.get(List_count));
            }else{
                Saved_choice1.setText(Incorrect_first_list.get(List_count));
                Saved_choice4.setText(Correct_list.get(List_count));
                Saved_choice3.setText(Incorrect_second_list.get(List_count));
                Saved_choice2.setText(Incorrect_third_list.get(List_count));
            }

        }


       /* if(cursor.isBeforeFirst()){
         Toast.makeText(Saved_Questions.this,"you can't do this",Toast.LENGTH_LONG).show();
        }else {
            count--;
            Intent intent=new Intent(this,Saved_Questions.class);
            intent.putExtra("count",Cur_current_pos);
            startActivity(intent);}
        */
    }

    public String handleEscapeCharacter( String str ) {
        String[] escapeCharacters = { "&gt;", "&lt;", "&amp;", "&quot;", "&apos;" };
        String[] onReadableCharacter = {">", "<", "&", "\"\"", "'"};
        for (int i = 0; i < escapeCharacters.length; i++) {
            str = str.replace(escapeCharacters[i], onReadableCharacter[i]);
        } return str;
    }
}
