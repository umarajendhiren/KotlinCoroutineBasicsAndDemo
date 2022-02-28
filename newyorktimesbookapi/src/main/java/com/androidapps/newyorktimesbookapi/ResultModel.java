package com.androidapps.newyorktimesbookapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//results object contain list array of book array
public class ResultModel {
    //public class Results {

        @SerializedName("bestsellers_date")
        @Expose
        private String bestsellersDate;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("published_date_description")
        @Expose
        private String publishedDateDescription;
        @SerializedName("previous_published_date")
        @Expose
        private String previousPublishedDate;
        @SerializedName("next_published_date")
        @Expose
        private String nextPublishedDate;
        @SerializedName("lists")
        @Expose
        private java.util.List<BestSellerList> lists = null;

        public String getBestsellersDate() {
            return bestsellersDate;
        }

        public void setBestsellersDate(String bestsellersDate) {
            this.bestsellersDate = bestsellersDate;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getPublishedDateDescription() {
            return publishedDateDescription;
        }

        public void setPublishedDateDescription(String publishedDateDescription) {
            this.publishedDateDescription = publishedDateDescription;
        }

        public String getPreviousPublishedDate() {
            return previousPublishedDate;
        }

        public void setPreviousPublishedDate(String previousPublishedDate) {
            this.previousPublishedDate = previousPublishedDate;
        }

        public String getNextPublishedDate() {
            return nextPublishedDate;
        }

        public void setNextPublishedDate(String nextPublishedDate) {
            this.nextPublishedDate = nextPublishedDate;
        }

        public java.util.List<BestSellerList> getLists() {
            return lists;
        }

        public void setLists(java.util.List<BestSellerList> lists) {
            this.lists = lists;
        }

    }

