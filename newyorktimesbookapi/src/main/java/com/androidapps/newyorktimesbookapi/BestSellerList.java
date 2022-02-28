package com.androidapps.newyorktimesbookapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//list array within results object  (best sellers in each category)
public class BestSellerList {
   // public class List {

        @SerializedName("list_id")
        @Expose
        private Integer listId;
        @SerializedName("list_name")
        @Expose
        private String listName;
        @SerializedName("list_name_encoded")
        @Expose
        private String listNameEncoded;
        @SerializedName("display_name")
        @Expose
        private String displayName;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("list_image")
        @Expose
        private Object listImage;
        @SerializedName("list_image_width")
        @Expose
        private Object listImageWidth;
        @SerializedName("list_image_height")
        @Expose
        private Object listImageHeight;
        @SerializedName("books")
        @Expose
        private java.util.List<BookModel> books = null;

        public Integer getListId() {
            return listId;
        }

        public void setListId(Integer listId) {
            this.listId = listId;
        }

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
        }

        public String getListNameEncoded() {
            return listNameEncoded;
        }

        public void setListNameEncoded(String listNameEncoded) {
            this.listNameEncoded = listNameEncoded;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public Object getListImage() {
            return listImage;
        }

        public void setListImage(Object listImage) {
            this.listImage = listImage;
        }

        public Object getListImageWidth() {
            return listImageWidth;
        }

        public void setListImageWidth(Object listImageWidth) {
            this.listImageWidth = listImageWidth;
        }

        public Object getListImageHeight() {
            return listImageHeight;
        }

        public void setListImageHeight(Object listImageHeight) {
            this.listImageHeight = listImageHeight;
        }

        public java.util.List<BookModel> getBooks() {
            return books;
        }

        public void setBooks(java.util.List<BookModel> books) {
            this.books = books;
        }

    }

