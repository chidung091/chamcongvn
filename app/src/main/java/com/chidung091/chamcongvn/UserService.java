package com.chidung091.chamcongvn;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("auth/sign_in")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
}

