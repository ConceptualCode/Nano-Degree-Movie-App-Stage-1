package com.conceptual.nanodegreemovieapp1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieData {


    @SerializedName("results")
    @Expose
    private List<Result> results = null;





    public List<Result> getResults() {
        return results;
    }


}
