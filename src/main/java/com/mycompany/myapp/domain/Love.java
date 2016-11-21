package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Love.
 */
@Entity
@Table(name = "love")
public class Love implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "story_id")
    private Long storyId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "time")
    private ZonedDateTime time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoryId() {
        return storyId;
    }

    public Love storyId(Long storyId) {
        this.storyId = storyId;
        return this;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Love userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public Love userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Love time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Love love = (Love) o;
        if(love.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, love.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Love{" +
            "id=" + id +
            ", storyId='" + storyId + "'" +
            ", userId='" + userId + "'" +
            ", userName='" + userName + "'" +
            ", time='" + time + "'" +
            '}';
    }
}
