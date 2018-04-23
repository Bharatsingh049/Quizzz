package quizes.android.bharat.quizes;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import quizes.android.bharat.quizes.data.User_dbhelper;
import quizes.android.bharat.quizes.data.User_contract.User_entry;
import quizes.android.bharat.quizes.data.User_contract.Quiz_entry;

import org.json.JSONObject;

import java.util.Random;

public class OTPActvity extends AppCompatActivity {
   private TextView Sign_up_text_view;
   private EditText Number_edit,Name_edit,Enter_OTP;
   private Button Sign_in,Verify;
   private String  Details,Status,User_name,User_number,User_OTP_number;
   private int OTP_number;
   private String Verified_Details,Verified_Status;
   private User_dbhelper m_dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        m_dbhelper= new User_dbhelper(OTPActvity.this);
        Sign_up_text_view=(TextView)findViewById(R.id.sign_up_Text_view);
        Number_edit=(EditText) findViewById(R.id.number);
        Name_edit=(EditText)findViewById(R.id.name);
        Enter_OTP=(EditText)findViewById(R.id.Enter_OTP);
        Sign_in=(Button)findViewById(R.id.Sign_in);
        Verify=(Button)findViewById(R.id.Verify_OTP);

  /*      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        get_database(OTPActvity.this);

        Sign_in.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r=new Random();
                OTP_number= r.nextInt(4000-2000);



                 View focusview=null;
                 Boolean cancel=false;
                User_number = Number_edit.getText().toString();
                User_name = Name_edit.getText().toString();
                String
                        url="http://2factor.in/API/V1/579f3796-4543-11e8-a895-0200cd936042/SMS/"+User_number+"/"+OTP_number+"";


                if(TextUtils.isEmpty(User_number)){
                    cancel=true;
                    Number_edit.setError("Invalid Number");
                    focusview=Number_edit;
                }
                if(TextUtils.isEmpty(User_name)){
                    cancel=true;
                    Name_edit.setError("Please enter your Name");
                    focusview=Name_edit;
                }
                if (cancel==true){
                    focusview.requestFocus();
                }else{

                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response.toString();
                                try {
                                    JSONObject Jobject = new JSONObject(response);
                                    Status = Jobject.getString("Status");
                                    Details = Jobject.getString("Details");
                                    Log.d( "onResponse: ",response);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OTPActvity.this, "Connection error...(Please check your connection)", Toast.LENGTH_LONG).show();
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(OTPActvity.this);
                        queue.add(request);

                      Sign_up_text_view.setText("Verify your Number");
                      Number_edit.setVisibility(View.GONE);
                      Name_edit.setVisibility(View.GONE);
                      Sign_in.setVisibility(View.GONE);
                      Enter_OTP.setVisibility(View.VISIBLE);
                      Verify.setVisibility(View.VISIBLE);}


            }
        });

        Verify.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog= new ProgressDialog(OTPActvity.this);
                dialog.setTitle("Verifying OTP...");
                dialog.setMessage("Please wait");
                dialog.setCancelable(true);
                dialog.setIndeterminate(false);
                 dialog.show();
                User_OTP_number = Enter_OTP.getText().toString();
                View focusview=null;
                Boolean cancel=false;
                String url = "http://2factor.in/API/V1/579f3796-4543-11e8-a895-0200cd936042/SMS/VERIFY/" + Details + "/" + User_OTP_number + "";

                if( TextUtils.isEmpty(User_OTP_number) ){
                    cancel=true;
                    Enter_OTP.setError("Enter the OTP");
                    focusview=Enter_OTP;
                }
                if(cancel==true){
                    focusview.requestFocus();
                }else {

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response.toString();
                            try {dialog.dismiss();
                                JSONObject Jobj1 = new JSONObject(response);
                                Verified_Details = Jobj1.getString("Details");
                                Verified_Status = Jobj1.getString("Status");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OTPActvity.this, "Connection error...(Please check your connection)", Toast.LENGTH_LONG).show();

                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(OTPActvity.this);
                    queue.add(request);

                    if( !"OTP Matched".equals(Verified_Details)){
                        Enter_OTP.setError("Invalid OTP");
                        focusview=Enter_OTP;
                        focusview.requestFocus();
                    }else{

                        SQLiteDatabase db= m_dbhelper.getWritableDatabase();
                        ContentValues values=new ContentValues();
                    values.put(User_entry.Column_User_Name,User_name);
                    values.put(User_entry.Column_User_Number,User_number);
                    long newRowID = db.insert(User_entry.Table_Name,null,values);
                    if(newRowID==-1){
                        Toast.makeText(OTPActvity.this, "Error with saving pet", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(OTPActvity.this, "Recipe saved with row id: " + newRowID, Toast.LENGTH_SHORT).show();
                        Toast.makeText(OTPActvity.this, "OTP Verified", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(OTPActvity.this,MainActivity.class);
                        intent.putExtra("User Name",User_name);
                        intent.putExtra("User Number",User_number);
                        startActivity(intent);
                    }

                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Number_edit.setVisibility(View.VISIBLE);
        Name_edit.setVisibility(View.VISIBLE);
        Sign_in.setVisibility(View.VISIBLE);
        Enter_OTP.setVisibility(View.GONE);
        Verify.setVisibility(View.GONE);
    }

    public void get_database(Context context){
        SQLiteDatabase db=m_dbhelper.getReadableDatabase();
        String[] Projection = {
                User_entry.Column_User_Name,
                User_entry.Column_User_Number};
        Cursor cursor=db.query(
                User_entry.Table_Name,
                Projection,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("User Name",cursor.getString(cursor.getColumnIndex(User_entry.Column_User_Name)));
        intent.putExtra("User Number",cursor.getString(cursor.getColumnIndex(User_entry.Column_User_Number)));
        startActivity(intent);
        }
    }




}
