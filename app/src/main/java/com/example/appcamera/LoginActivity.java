package com.example.appcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcamera.utils.User;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    private String usernameText;
    private String passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button b = (Button) findViewById(R.id.login);

        Ion.getDefault(LoginActivity.this).getConscryptMiddleware().enable(false);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText username = (EditText) findViewById(R.id.username);
                final EditText password = (EditText) findViewById(R.id.password);

                usernameText = username.getText().toString();
                passwordText = password.getText().toString();
                String url = "http://192.168.10.11/lpro/api/login.php?key=iot1235&username=" + usernameText + "&password=" + passwordText;
                login(LoginActivity.this, url);
            }
        });
    }

    private void login(Context ctx, String url) {
        Ion.with(ctx).load(url).
                asString()
                .withResponse()
                .setCallback((e, result) -> {
                    if (result == null) {
                        Toast.makeText(ctx, "RIEN\n"+url, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ctx, "LOGIN", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jObject = new JSONObject(String.valueOf(result.getResult()));
                            System.out.println(jObject);
                            System.out.println("token : "+ jObject.getString("token"));
                            String token = jObject.getString("token");
                            User user = new User(usernameText,passwordText, token);
                            System.out.println(user);

                            // ouvrir main activity et envoyer user
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }
}