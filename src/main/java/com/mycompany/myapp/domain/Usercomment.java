package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Usercomment.
 */
@Entity
@Table(name = "usercomment")
public class Usercomment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "story_id")
    private Integer storyID;

    @Lob
    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "user_comment_id")
    private Integer userCommentID;

    @Column(name = "user_comment_name")
    private String userCommentName;

    @Column(name = "time_commented")
    private ZonedDateTime timeCommented;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStoryID() {
        return storyID;
    }

    public Usercomment storyID(Integer storyID) {
        this.storyID = storyID;
        return this;
    }

    public void setStoryID(Integer storyID) {
        this.storyID = storyID;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Usercomment commentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getUserCommentID() {
        return userCommentID;
    }

    public Usercomment userCommentID(Integer userCommentID) {
        this.userCommentID = userCommentID;
        return this;
    }

    public void setUserCommentID(Integer userCommentID) {
        this.userCommentID = userCommentID;
    }

    public String getUserCommentName() {
        return userCommentName;
    }

    public Usercomment userCommentName(String userCommentName) {
        this.userCommentName = userCommentName;
        return this;
    }

    public void setUserCommentName(String userCommentName) {
        this.userCommentName = userCommentName;
    }

    public ZonedDateTime getTimeCommented() {
        return timeCommented;
    }

    public Usercomment timeCommented(ZonedDateTime timeCommented) {
        this.timeCommented = timeCommented;
        return this;
    }

    public void setTimeCommented(ZonedDateTime timeCommented) {
        this.timeCommented = timeCommented;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usercomment usercomment = (Usercomment) o;
        if(usercomment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, usercomment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usercomment{" +
            "id=" + id +
            ", storyID='" + storyID + "'" +
            ", commentContent='" + commentContent + "'" +
            ", userCommentID='" + userCommentID + "'" +
            ", userCommentName='" + userCommentName + "'" +
            ", timeCommented='" + timeCommented + "'" +
            '}';
    }
}
