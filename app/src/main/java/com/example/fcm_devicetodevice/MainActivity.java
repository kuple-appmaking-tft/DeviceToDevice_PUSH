package com.example.fcm_devicetodevice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequesQue;
    private String URL="https://fcm.googleapis.com/fcm/send";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.btn);

        mRequesQue= Volley.newRequestQueue(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
        findViewById(R.id.subcribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().subscribeToTopic("news");
            }
        });
    }

    private void sendNotification(){
        /* our json object will lokk loke
        {
            "to": "topics/topic name",
            notification:{
                title: "some title",
                 body: "some body"
            }
        }
        */
        JSONObject mainObj=new JSONObject();
        try {
            mainObj.put("to","/topics/"+"news");
            JSONObject notificationObj=new JSONObject();
            notificationObj.put("title","anytitle");
            notificationObj.put("body","any");
            mainObj.put("notification",notificationObj);


            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String ,String> header=new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAA1j6Icss:APA91bEqmdclfXD5zqp-beW6_4lCEBalNVfSfbzuM9M8tcrp3yE2C3BCFIn__k3q5MAPmVaLs9_i1dUdBOaqu-Skne8viJf1uWum1UZu_d7dOjlqHZz3-jacilOgqkt8cYzGVzvPd-gf");
                    return  header;
                }
            };

            mRequesQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
