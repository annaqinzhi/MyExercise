package com.dohman.myexercise;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetWorkInterface {
    @GET("/users/{login}")
    Observable<User> getUser (@Path("login") String login1);
}
