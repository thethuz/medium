package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Story.
 */
@Entity
@Table(name = "story")
public class Story implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "author_id")
    private Long authorID;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "time_created")
    private ZonedDateTime timeCreated;

    @Column(name = "place_created")
    private String placeCreated;

    @Max(value = 3)
    @Column(name = "time_to_read")
    private Integer timeToRead;

    @Column(name = "category")
    private String category;

    @Column(name = "number_of_love")
    private Integer numberOfLove;

    @Column(name = "number_of_comment")
    private Integer numberOfComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Story title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Story content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorID() {
        return authorID;
    }

    public Story authorID(Long authorID) {
        this.authorID = authorID;
        return this;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Story authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public Story timeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getPlaceCreated() {
        return placeCreated;
    }

    public Story placeCreated(String placeCreated) {
        this.placeCreated = placeCreated;
        return this;
    }

    public void setPlaceCreated(String placeCreated) {
        this.placeCreated = placeCreated;
    }

    public Integer getTimeToRead() {
        return timeToRead;
    }

    public Story timeToRead(Integer timeToRead) {
        this.timeToRead = timeToRead;
        return this;
    }

    public void setTimeToRead(Integer timeToRead) {
        this.timeToRead = timeToRead;
    }

    public String getCategory() {
        return category;
    }

    public Story category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNumberOfLove() {
        return numberOfLove;
    }

    public Story numberOfLove(Integer numberOfLove) {
        this.numberOfLove = numberOfLove;
        return this;
    }

    public void setNumberOfLove(Integer numberOfLove) {
        this.numberOfLove = numberOfLove;
    }

    public Integer getNumberOfComment() {
        return numberOfComment;
    }

    public Story numberOfComment(Integer numberOfComment) {
        this.numberOfComment = numberOfComment;
        return this;
    }

    public void setNumberOfComment(Integer numberOfComment) {
        this.numberOfComment = numberOfComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Story story = (Story) o;
        if(story.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, story.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Story{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", content='" + content + "'" +
            ", authorID='" + authorID + "'" +
            ", authorName='" + authorName + "'" +
            ", timeCreated='" + timeCreated + "'" +
            ", placeCreated='" + placeCreated + "'" +
            ", timeToRead='" + timeToRead + "'" +
            ", category='" + category + "'" +
            ", numberOfLove='" + numberOfLove + "'" +
            ", numberOfComment='" + numberOfComment + "'" +
            '}';
    }
}
