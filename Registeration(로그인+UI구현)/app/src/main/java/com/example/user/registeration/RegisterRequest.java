package com.example.user.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2017-07-23.
 */

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://ozaki0412.cafe24.com/UserRegister.php"; // 회원 등록 요청 URL
    private Map<String, String> parameters;

    public RegisterRequest (String userID, String userPassword, String userGender, String userMajor ,String userAge, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL , listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userGender",userGender);
        parameters.put("userMajor",userMajor);
        parameters.put("userAge",userAge);
        parameters.put("userEmail",userEmail);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}

// 회원가입을 요청하는 class