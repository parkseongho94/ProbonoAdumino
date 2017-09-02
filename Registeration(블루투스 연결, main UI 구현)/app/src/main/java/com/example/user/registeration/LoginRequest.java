package com.example.user.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2017-07-24.
 */

public class LoginRequest extends StringRequest {

    final static private String URL = "http://ozaki0412.cafe24.com/UserLogin.php"; // 회원 등록 요청 URL
    private Map<String, String> parameters;

    public LoginRequest (String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL , listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
