package com.androidapps.newyorktimesbookapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//book array within list
public class BookModel {
        @SerializedName("age_group")
        @Expose
        private String ageGroup;
        @SerializedName("amazon_product_url")
        @Expose
        private String amazonProductUrl;
        @SerializedName("article_chapter_link")
        @Expose
        private String articleChapterLink;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("book_image")
        @Expose
        private String bookImage;
        @SerializedName("book_image_width")
        @Expose
        private Integer bookImageWidth;
        @SerializedName("book_image_height")
        @Expose
        private Integer bookImageHeight;
        @SerializedName("book_review_link")
        @Expose
        private String bookReviewLink;
        @SerializedName("book_uri")
        @Expose
        private String bookUri;
        @SerializedName("contributor")
        @Expose
        private String contributor;
        @SerializedName("contributor_note")
        @Expose
        private String contributorNote;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("first_chapter_link")
        @Expose
        private String firstChapterLink;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("primary_isbn10")
        @Expose
        private String primaryIsbn10;
        @SerializedName("primary_isbn13")
        @Expose
        private String primaryIsbn13;
        @SerializedName("publisher")
        @Expose
        private String publisher;
        @SerializedName("rank")
        @Expose
        private Integer rank;
        @SerializedName("rank_last_week")
        @Expose
        private Integer rankLastWeek;
        @SerializedName("sunday_review_link")
        @Expose
        private String sundayReviewLink;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("weeks_on_list")
        @Expose
        private Integer weeksOnList;
        @SerializedName("buy_links")
        @Expose
        private List<BuyLink> buyLinks = null;

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String getAmazonProductUrl() {
            return amazonProductUrl;
        }

        public void setAmazonProductUrl(String amazonProductUrl) {
            this.amazonProductUrl = amazonProductUrl;
        }

        public String getArticleChapterLink() {
            return articleChapterLink;
        }

        public void setArticleChapterLink(String articleChapterLink) {
            this.articleChapterLink = articleChapterLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBookImage() {
            return bookImage;
        }

        public void setBookImage(String bookImage) {
            this.bookImage = bookImage;
        }

        public Integer getBookImageWidth() {
            return bookImageWidth;
        }

        public void setBookImageWidth(Integer bookImageWidth) {
            this.bookImageWidth = bookImageWidth;
        }

        public Integer getBookImageHeight() {
            return bookImageHeight;
        }

        public void setBookImageHeight(Integer bookImageHeight) {
            this.bookImageHeight = bookImageHeight;
        }

        public String getBookReviewLink() {
            return bookReviewLink;
        }

        public void setBookReviewLink(String bookReviewLink) {
            this.bookReviewLink = bookReviewLink;
        }

        public String getBookUri() {
            return bookUri;
        }

        public void setBookUri(String bookUri) {
            this.bookUri = bookUri;
        }

        public String getContributor() {
            return contributor;
        }

        public void setContributor(String contributor) {
            this.contributor = contributor;
        }

        public String getContributorNote() {
            return contributorNote;
        }

        public void setContributorNote(String contributorNote) {
            this.contributorNote = contributorNote;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFirstChapterLink() {
            return firstChapterLink;
        }

        public void setFirstChapterLink(String firstChapterLink) {
            this.firstChapterLink = firstChapterLink;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrimaryIsbn10() {
            return primaryIsbn10;
        }

        public void setPrimaryIsbn10(String primaryIsbn10) {
            this.primaryIsbn10 = primaryIsbn10;
        }

        public String getPrimaryIsbn13() {
            return primaryIsbn13;
        }

        public void setPrimaryIsbn13(String primaryIsbn13) {
            this.primaryIsbn13 = primaryIsbn13;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public Integer getRankLastWeek() {
            return rankLastWeek;
        }

        public void setRankLastWeek(Integer rankLastWeek) {
            this.rankLastWeek = rankLastWeek;
        }

        public String getSundayReviewLink() {
            return sundayReviewLink;
        }

        public void setSundayReviewLink(String sundayReviewLink) {
            this.sundayReviewLink = sundayReviewLink;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public Integer getWeeksOnList() {
            return weeksOnList;
        }

        public void setWeeksOnList(Integer weeksOnList) {
            this.weeksOnList = weeksOnList;
        }

        public List<BuyLink> getBuyLinks() {
            return buyLinks;
        }

        public void setBuyLinks(List<BuyLink> buyLinks) {
            this.buyLinks = buyLinks;
        }

    }




