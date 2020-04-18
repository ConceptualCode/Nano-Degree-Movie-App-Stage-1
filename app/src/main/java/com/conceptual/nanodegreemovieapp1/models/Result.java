package com.conceptual.nanodegreemovieapp1.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "videos")
public class Result implements Serializable {

    @PrimaryKey
    private Integer id;

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @SerializedName("popularity")
    @Expose
    private Float popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public Float getPopularity() {
        return popularity;
    }


    public Integer getVoteCount() {
        return voteCount;
    }



    public Boolean getVideo() {
        return video;
    }



    public String getPosterPath() {
        return posterPath;
    }



    public Integer getId() {
        return id;
    }



    public Boolean getAdult() {
        return adult;
    }



    public String getBackdropPath() {
        return backdropPath;
    }


    public String getOriginalLanguage() {
        return originalLanguage;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }


    public String getTitle() {
        return title;
    }


    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
