package com.example.user.registeration;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter; // 스피너에 추가할 array.xml을 받아오기 위한 어댑터
    private Spinner spinner;
    private ArrayAdapter adapter2; // 스피너에 추가할 array.xml을 받아오기 위한 어댑터
    private Spinner spinner2;

    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userAge;
    private String userEmail;

    private AlertDialog dialog;
    private  boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.ageSpinner);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.age, android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        final EditText idText  = (EditText) findViewById(R.id.idText);
        final EditText passwordText  = (EditText) findViewById(R.id.passwordText);
        final EditText emailText  = (EditText) findViewById(R.id.emailText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId(); // 현재 젠더 그룹 확인
        userGender = ((RadioButton)findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString();
            }
        });

        /* 회원 검사 버튼 */
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals("")) // 아이디 입력이 빈칸일 경우 에러메세진 출력
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈칸 일수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> resonseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) { // 사용할수 있는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false); // 아이디 값을 확인하고 나면 수정할수 없도록 고정
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, resonseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });


        /* 회원 등록 버튼 */
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userAge = spinner2.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(userID.equals("")||userPassword.equals("")||userMajor.equals("")||userAge.equals("")||userEmail.equals("")||userGender.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> resonseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 성공하였습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                finish();
                            }

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원등록에 실패하였습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,userGender,userMajor,userAge,userEmail, resonseListener);
                // 회원 등록 진행
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog= null;
        }
    }
}
