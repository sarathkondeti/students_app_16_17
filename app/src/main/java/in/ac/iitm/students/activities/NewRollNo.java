package in.ac.iitm.students.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import in.ac.iitm.students.R;
import in.ac.iitm.students.others.MySingleton;
import in.ac.iitm.students.others.UtilStrings;
import in.ac.iitm.students.others.Utils;

public class NewRollNo extends AppCompatActivity {
    TextView personName,personRollno,newRoomNo,oldRoomNo;
    String name,rollNo,OLDROOM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_roll_no);

        personName = (TextView) findViewById(R.id.person_name);
        personRollno = (TextView) findViewById(R.id.person_rollno);
        oldRoomNo = (TextView) findViewById(R.id.old_room_no);
        newRoomNo = (TextView) findViewById(R.id.new_room_no);

        name = Utils.getprefString(UtilStrings.NAME, this);
        rollNo = Utils.getprefString(UtilStrings.ROLLNO, this);
        OLDROOM = Utils.getprefString(UtilStrings.HOSTEl, this);

        personName.setText(name);
        personRollno.setText(rollNo);
        oldRoomNo.setText(OLDROOM);
        Uri.Builder builder = new Uri.Builder();


        getNewRoom();
    }

    private void getNewRoom() {

        String url = "";

        StringRequest jsonObjRqt = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    String hostel="",roomno="";

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        hostel = jsonObject.getString("hostel");
                        roomno = jsonObject.getString("roomno");
                    }

                    newRoomNo.setText(hostel+" "+roomno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(NewRollNo.this,"Couldn't connect to the server.",Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjRqt);
    }

}
