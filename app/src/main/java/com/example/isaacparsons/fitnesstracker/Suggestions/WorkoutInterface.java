package com.example.isaacparsons.fitnesstracker.Suggestions;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by isaacparsons on 2017-08-25.
 */

public interface WorkoutInterface {
    @GET("{thing}/?format=json")
    Observable<SuggestionClass> getTitles(
            @Path("thing") String exercise

    );
}
