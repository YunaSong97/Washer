package com.example.washer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText et_id, et_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr_id = et_id.getText().toString();
                String usr_password = et_password.getText().toString();
                boolean pass_the_login = true;
                if (!pass_the_login) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 로그인에 성공한 경우
                                    String usr_id = jsonObject.getString("usr_id");
                                    String usr_password = jsonObject.getString("usr_password");
                                    Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("usr_id", usr_id);
                                    intent.putExtra("usr_password", usr_password);
                                    startActivity(intent);
                                } else { // 로그인에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(usr_id, usr_password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    queue.add(loginRequest);
                }
                else{
                    //로그인 과정 패스한다(개발용)
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("usr_id", usr_id);
                    intent.putExtra("usr_password", usr_password);
                    startActivity(intent);
                }
            }
        });
    }
}