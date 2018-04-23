package quizes.android.bharat.quizes;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import quizes.android.bharat.quizes.data.User_contract.Quiz_entry;
import quizes.android.bharat.quizes.data.User_dbhelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class Quiz2Activity extends AppCompatActivity {
    private TextView Ques,choice1,choice2,choice3,choice4;
    private String response,clicked,correct_choice;
    private int count=0,p_count,view_count;
    private User_dbhelper m_dbhelper;
    private Boolean Flag=false;
    private Random r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        r= new Random();
        view_count=r.nextInt(5);
        m_dbhelper=new User_dbhelper(Quiz2Activity.this);
        response = getIntent().getStringExtra("Response");
        count = getIntent().getIntExtra("private count",0);
        p_count = getIntent().getIntExtra("progress count",0);
        final ProgressDialog dialog= new ProgressDialog(Quiz2Activity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);
        dialog.setIndeterminate(false);
        dialog.show();
        Ques=(TextView)findViewById(R.id.question_view);
        choice1=(TextView)findViewById(R.id.Choice1_view);
        choice2=(TextView)findViewById(R.id.Choice2_view);
        choice3=(TextView)findViewById(R.id.Choice3_view);
        choice4=(TextView)findViewById(R.id.Choice4_view);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       */
        TextView.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.Choice1_view){
                    v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    choice1.setTextColor(Color.parseColor("#FFFFFF"));
                    choice2.setTextColor(Color.parseColor("#000000"));
                    choice3.setTextColor(Color.parseColor("#000000"));
                    choice4.setTextColor(Color.parseColor("#000000"));
                    choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    clicked="choice1";
                }
                if(v.getId()==R.id.Choice2_view){
                    v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    choice2.setTextColor(Color.parseColor("#FFFFFF"));
                    choice1.setTextColor(Color.parseColor("#000000"));
                    choice3.setTextColor(Color.parseColor("#000000"));
                    choice4.setTextColor(Color.parseColor("#000000"));
                     choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    clicked="choice2";
                }
                if(v.getId()==R.id.Choice3_view){
                    v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    choice3.setTextColor(Color.parseColor("#FFFFFF"));
                    choice2.setTextColor(Color.parseColor("#000000"));
                    choice1.setTextColor(Color.parseColor("#000000"));
                    choice4.setTextColor(Color.parseColor("#000000"));
                    choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    clicked="choice3";
                }
                if(v.getId()==R.id.Choice4_view){
                    v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    choice4.setTextColor(Color.parseColor("#FFFFFF"));
                    choice2.setTextColor(Color.parseColor("#000000"));
                    choice3.setTextColor(Color.parseColor("#000000"));
                    choice1.setTextColor(Color.parseColor("#000000"));
                    choice2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choice1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    clicked="choice4";
                }
            }
        };
        choice1.setOnClickListener(listener);
        choice2.setOnClickListener(listener);
        choice3.setOnClickListener(listener);
        choice4.setOnClickListener(listener);
        try {
            JSONObject Jobj1 = new JSONObject(response);
            JSONArray Jarr1 = Jobj1.getJSONArray("results");
            //for (int i = 0; i < 5; i++) {
                JSONObject Jobj2 = Jarr1.getJSONObject(count);
               // ModelQuestions Questions = new ModelQuestions();
                //Questions.setQuestion(Jobj2.getString("question").toString());
                JSONArray Jarr2 = Jobj2.getJSONArray("incorrect_answers");
                //String[] In_correct = new String[3];
                //for (int j = 0; j < 3; j++) {
                 //   In_correct[j] = Jarr2.get(j).toString();
                //}
                //Questions.setIncorrect_ans(In_correct);
                //Questions.setCorrect_ans(Jobj2.getString("correct_answer").toString());
            Ques.setText(handleEscapeCharacter(Jobj2.getString("question").toString()));
                if(view_count==1) {
                    dialog.dismiss();
                    choice1.setText(Jarr2.get(0).toString());
                    choice2.setText(Jarr2.get(2).toString());
                    choice4.setText(Jarr2.get(1).toString());
                    choice3.setText(Jobj2.getString("correct_answer").toString());
                }else if(view_count==2){
                    dialog.dismiss();
                    choice3.setText(Jarr2.get(0).toString());
                    choice2.setText(Jarr2.get(2).toString());
                    choice4.setText(Jarr2.get(1).toString());
                    choice1.setText(Jobj2.getString("correct_answer").toString());}
                else if(view_count==3){
                    dialog.dismiss();
                    choice1.setText(Jarr2.get(0).toString());
                    choice3.setText(Jarr2.get(2).toString());
                    choice4.setText(Jarr2.get(1).toString());
                    choice2.setText(Jobj2.getString("correct_answer").toString());
                }else if(view_count==4){
                    dialog.dismiss();
                    choice1.setText(Jarr2.get(0).toString());
                    choice2.setText(Jarr2.get(2).toString());
                    choice3.setText(Jarr2.get(1).toString());
                    choice4.setText(Jobj2.getString("correct_answer").toString());
                }else{
                    dialog.dismiss();
                    choice1.setText(Jarr2.get(0).toString());
                    choice4.setText(Jarr2.get(2).toString());
                    choice2.setText(Jarr2.get(1).toString());
                    choice3.setText(Jobj2.getString("correct_answer").toString());
                }


                correct_choice=Jobj2.getString("correct_answer").toString();
            SQLiteDatabase db=m_dbhelper.getWritableDatabase();
            SQLiteDatabase dbr=m_dbhelper.getReadableDatabase();
            String[] Projection={
            Quiz_entry.Id,
            Quiz_entry.Column_Quiz_Question_Category,
            Quiz_entry.Column_Quiz_Question,
            Quiz_entry.Column_Quiz_Correct_Answer,
            Quiz_entry.Column_Quiz_Incorrect_First,
            Quiz_entry.Column_Quiz_Incorrect_Second,
            Quiz_entry.Column_Quiz_Incorrect_Third
            };
            Cursor cursor=dbr.query(
                         Quiz_entry.Table_Name,
                         Projection,
                       null,
                    null,
                        null,
                         null,
                        null
            );
            cursor.moveToFirst();

            if(cursor.getCount()>0){
                while(!cursor.isAfterLast()) {
                    String temp1=cursor.getString(cursor.getColumnIndex(Quiz_entry.Column_Quiz_Question)),
                            temp2=Jobj2.getString("question").toString();
                    if (temp1.equals(temp2)) {
                        Flag=true;
                        Toast.makeText(Quiz2Activity.this,"This question was already saved",Toast.LENGTH_LONG).show();
                       cursor.moveToLast();
                       cursor.moveToNext();
                    }else{cursor.moveToNext();}
                    cursor.moveToNext();
                }
            }
            if(cursor.getCount()<20){
                ContentValues values = new ContentValues();
                values.put(Quiz_entry.Column_Quiz_Question_Category, Jobj2.getString("category").toString());
                values.put(Quiz_entry.Column_Quiz_Question, Jobj2.getString("question").toString());
                values.put(Quiz_entry.Column_Quiz_Correct_Answer, Jobj2.getString("correct_answer").toString());
                values.put(Quiz_entry.Column_Quiz_Incorrect_First, Jarr2.get(0).toString());
                values.put(Quiz_entry.Column_Quiz_Incorrect_Second, Jarr2.get(2).toString());
                values.put(Quiz_entry.Column_Quiz_Incorrect_Third, Jarr2.get(1).toString());
                long newRowId = db.insert(Quiz_entry.Table_Name, null, values);
                if (newRowId == -1) {
                    Toast.makeText(Quiz2Activity.this, "Error with saving pet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Quiz2Activity.this, "Questions was saved at row id: " + newRowId, Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Quiz2Activity.this,"please delete some questions from attended questions",Toast.LENGTH_LONG).show();
            }
            //}
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(Quiz2Activity.this,MainActivity.class);
        startActivity(in);
    }

public void next(View view){
        int private_count=count;
        View focusView=null;
    private_count++;

    Intent intent=new Intent(this,Quiz2Activity.class);
    intent.putExtra("Response",response);
    intent.putExtra("private count",private_count);
     if(count<4 ){
    if("choice1".equals(clicked)){
            if (correct_choice.equals(choice1.getText())){
                p_count++;
                intent.putExtra("progress count",p_count);
                startActivity(intent);

             }
        } else if("choice2".equals(clicked)){
             if (correct_choice.equals(choice2.getText())){
                 p_count++;
                 intent.putExtra("progress count",p_count);
                 startActivity(intent);

             }
         }
         else if("choice3".equals(clicked)){
             if (correct_choice.equals(choice3.getText())){
                 p_count++;
                 intent.putExtra("progress count",p_count);
                 startActivity(intent);

             }
         }
         else if("choice4".equals(clicked)){
             if (correct_choice.equals(choice4.getText())){
                 p_count++;
                intent.putExtra("progress count",p_count);
                 startActivity(intent);

             }
         }
         if(TextUtils.isEmpty(clicked)){
             Ques.setError("Required Field");
             focusView=Ques;
             focusView.requestFocus();
         }else{startActivity(intent);}

     }else{
         AlertDialog.Builder dailog = new AlertDialog.Builder(this);
     dailog.setMessage("Quiz finished");
     dailog.setTitle("your score is "+ p_count +" out of 5");
     dailog.create();
     dailog.setPositiveButton(" Ok ",new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
             Intent in=new Intent(Quiz2Activity.this,MainActivity.class);
             startActivity(in);
         }
     });
     //dailog.setCancelable(false);
     dailog.show();
     }
    Log.d( "next: ",response);


    }

    public String handleEscapeCharacter( String str ) {
        String[] escapeCharacters = { "&gt;", "&lt;", "&amp;", "&quot;", "&apos;" };
        String[] onReadableCharacter = {">", "<", "&", "\"\"", "'"};
        for (int i = 0; i < escapeCharacters.length; i++) {
            str = str.replace(escapeCharacters[i], onReadableCharacter[i]);
        } return str;
    }
}

