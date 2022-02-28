package com.androidapps.newyorktimesbookapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//outer response
public class ResponseModel {
    //public class Example {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("copyright")
        @Expose
        private String copyright;
        @SerializedName("num_results")
        @Expose
        private Integer numResults;
        @SerializedName("results")
        @Expose
        private ResultModel results;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public Integer getNumResults() {
            return numResults;
        }

        public void setNumResults(Integer numResults) {
            this.numResults = numResults;
        }

        public ResultModel getResults() {
            return results;
        }

        public void setResults(ResultModel results) {
            this.results = results;
        }

    }

