package quizes.android.bharat.quizes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.IllegalFormatException;

import quizes.android.bharat.quizes.data.User_contract;
import quizes.android.bharat.quizes.data.User_dbhelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//private ArrayList<ModelQuestions> Questionslist;
private TextView textView,navUsername;
private String User_name,User_number;
private User_dbhelper m_dbhelper;
private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        User_name=getIntent().getStringExtra("User Name");
        User_number=getIntent().getStringExtra("User Number");
        textView=(TextView)findViewById(R.id.User_Textview);
        textView.setText(" Quizzz ");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

       /* m_dbhelper=new User_dbhelper(MainActivity.this);
        SQLiteDatabase db=m_dbhelper.getReadableDatabase();
        String[] Projection = {
                User_contract.User_entry.Column_User_Name,
                User_contract.User_entry.Column_User_Number};
        Cursor cursor=db.query(
                User_contract.User_entry.Table_Name,
                Projection,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            User_number=cursor.getString(cursor.getColumnIndex(User_contract.User_entry.Column_User_Number));
        }
       */
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.nav_header);
        navUsername.setText(User_name);
        //Questionslist=new ArrayList<>();
        textView=(TextView)findViewById(R.id.User_Textview);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.openDrawer(GravityCompat.START);
                //Snackbar.make(view, "That Question was deleted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        navigationView1.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {}
    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.main_quiz, menu);
          return true;
      }
  */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.General_knowledge) {
            // Handle the camera action
            Toast.makeText(this, "General Knowledge", Toast.LENGTH_LONG).show();
            categories(9);
        } else if (id == R.id.Science_nature) {
            Toast.makeText(this, "Science and nature", Toast.LENGTH_LONG).show();
            categories(17);
        } else if (id == R.id.Sports) {
            Toast.makeText(this, "Sports", Toast.LENGTH_LONG).show();
            categories(21);
        } else if (id == R.id.Politics) {
            Toast.makeText(this, "Politics", Toast.LENGTH_LONG).show();
            categories(24);
        } else if (id == R.id.Comics) {
            Toast.makeText(this, "Comics", Toast.LENGTH_LONG).show();
            categories(29);
        } else if (id == R.id.History) {
            Toast.makeText(this, "History", Toast.LENGTH_LONG).show();
            categories(23);
        }else if(id==R.id.Attended_question){
            Intent intent= new Intent(MainActivity.this,Saved_Questions.class);
           // intent.putExtra("count",1);
            startActivity(intent);
        }else if (id==R.id.User_Setting){
            final AlertDialog.Builder dailog = new AlertDialog.Builder(this);
            dailog.setMessage("User name : "+User_name+"\nContact Number : "+User_number);
            dailog.setTitle("Setting");
            dailog.create();
            //dailog.setCancelable(false);
            dailog.show();
        }
        return true;
    }

    public void categories(int cat_no) {
        final ProgressDialog dialog= new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);
        dialog.setIndeterminate(false);
        dialog.show();
        String url = "https://opentdb.com/api.php?amount=5&category=" + cat_no + "&difficulty=medium&type=multiple";
        Log.d("URL: ",url);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response.toString();
                    dialog.dismiss();
                    Intent intent=new Intent(MainActivity.this,Quiz2Activity.class);
                    intent.putExtra("Response",response);
                    startActivity(intent);
  /*                  try{
                        JSONObject Jobj1= new JSONObject(response);
                        JSONArray Jarr1= Jobj1.getJSONArray("results");
                        for(int i=0;i<4;i++){
                        JSONObject Jobj2=Jarr1.getJSONObject(i);
                        ModelQuestions Questions= new ModelQuestions();
                        Questions.setQuestion(Jobj2.getString("question").toString());
                        JSONArray Jarr2=Jobj2.getJSONArray("incorrect_answers");
                        String[] In_correct=new String[3];
                        for(int j=0;i<3;i++){
                            In_correct[i]=Jarr2.getString(i).toString();
                        }
                        Questions.setIncorrect_ans(In_correct);
                        Questions.setCorrect_ans(Jobj2.getString("correct_answer").toString());
                        textView.setText(Jobj2.getString("question").toString());
                        Questionslist.add(i,Questions);
                            //Log.d( "onResponse: ",Jobj2.getString("question").toString());
                        }

                    }catch (Exception e){e.printStackTrace();}
*/
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,"Connection error...(Please check your connection)",Toast.LENGTH_LONG).show();
                }
            }) ;
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        queue.add(stringRequest);

    }
}
