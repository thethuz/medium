package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Draft.
 */
@Entity
@Table(name = "draft")
public class Draft implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "author_id")
    private Long authorID;

    @Column(name = "time_create")
    private ZonedDateTime timeCreate;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorID() {
        return authorID;
    }

    public Draft authorID(Long authorID) {
        this.authorID = authorID;
        return this;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }

    public ZonedDateTime getTimeCreate() {
        return timeCreate;
    }

    public Draft timeCreate(ZonedDateTime timeCreate) {
        this.timeCreate = timeCreate;
        return this;
    }

    public void setTimeCreate(ZonedDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getTitle() {
        return title;
    }

    public Draft title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Draft content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public Draft category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Draft draft = (Draft) o;
        if(draft.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, draft.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Draft{" +
            "id=" + id +
            ", authorID='" + authorID + "'" +
            ", timeCreate='" + timeCreate + "'" +
            ", title='" + title + "'" +
            ", content='" + content + "'" +
            ", category='" + category + "'" +
            '}';
    }
}
