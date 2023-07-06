package com.example.clevertap;
import android.app.NotificationManager;        //added by CleverTap Assistant
import com.clevertap.android.sdk.CTWebInterface;
import com.clevertap.android.sdk.CleverTapAPI; //added by CleverTap Assistant
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText etoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etoken = findViewById(R.id.etoken);

//        Push Notification tested on internal device
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);
                        Toast.makeText(MainActivity.this, "Your device registration token is:" + token, Toast.LENGTH_SHORT).show();
                        etoken.setText(token);
                    }
                });


		CleverTapAPI.createNotificationChannel(getApplicationContext(),"Test","mychannel","lDescription",NotificationManager.IMPORTANCE_MAX,true);        //added by CleverTap Assistant


        CleverTapAPI cleverTap = CleverTapAPI.getDefaultInstance(getApplicationContext());   //Initializing the CleverTap SDK


        //Product Viewed
        cleverTap.pushEvent("Product viewed");
        Toast.makeText(MainActivity.this, "Product viewed", Toast.LENGTH_SHORT).show();


        //onuserlogin
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button testButton = findViewById(R.id.testButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                CleverTapAPI.getDefaultInstance(getApplicationContext())
                        .onUserLogin(new HashMap<String, Object>() {{
                            put("Name", name);
                            put("Email", email);
                        }});


                 Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
            }
        });


        //test event
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cleverTap.pushEvent("Product viewed");


                Toast.makeText(MainActivity.this, "TEST event triggered", Toast.LENGTH_SHORT).show();
            }
        });


        //charged event
        HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
        chargeDetails.put("Amount", 300);
        chargeDetails.put("Payment Mode", "Credit card");
        chargeDetails.put("Charged ID", 24052013);

        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("Product category", "books");
        item1.put("Book name", "The Millionaire next door");
        item1.put("Quantity", 1);

        HashMap<String, Object> item2 = new HashMap<String, Object>();
        item2.put("Product category", "books");
        item2.put("Book name", "Achieving inner zen");
        item2.put("Quantity", 1);

        HashMap<String, Object> item3 = new HashMap<String, Object>();
        item3.put("Product category", "books");
        item3.put("Book name", "Chuck it, let's do it");
        item3.put("Quantity", 5);

        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        cleverTap.pushChargedEvent(chargeDetails, items);
    }
}
