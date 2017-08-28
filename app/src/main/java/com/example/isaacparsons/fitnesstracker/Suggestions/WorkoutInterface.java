package com.example.isaacparsons.fitnesstracker.Suggestions;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by isaacparsons on 2017-08-25.
 */

public interface WorkoutInterface {
    @Headers("Authorization: Token 3084427c243e9f7be5e428184d95390e6227c2ee")
    @GET("{thing}/?format=api")
    Observable<SuggestionClass> getTitles(
            @Path("thing") String exercise

    );
}
