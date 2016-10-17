package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_relate")
    private String categoryRelate;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Draft> drafts = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Story> stories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryRelate() {
        return categoryRelate;
    }

    public Category categoryRelate(String categoryRelate) {
        this.categoryRelate = categoryRelate;
        return this;
    }

    public void setCategoryRelate(String categoryRelate) {
        this.categoryRelate = categoryRelate;
    }

    public Set<Draft> getDrafts() {
        return drafts;
    }

    public Category drafts(Set<Draft> drafts) {
        this.drafts = drafts;
        return this;
    }

    public Category addDraft(Draft draft) {
        drafts.add(draft);
        draft.setCategory(this.categoryName);
        return this;
    }

    public Category removeDraft(Draft draft) {
        drafts.remove(draft);
        draft.setCategory(null);
        return this;
    }

    public void setDrafts(Set<Draft> drafts) {
        this.drafts = drafts;
    }

    public Set<Story> getStories() {
        return stories;
    }

    public Category stories(Set<Story> stories) {
        this.stories = stories;
        return this;
    }

    public Category addStory(Story story) {
        stories.add(story);
        story.setCategory(this.categoryName);
        return this;
    }

    public Category removeStory(Story story) {
        stories.remove(story);
        story.setCategory(null);
        return this;
    }

    public void setStories(Set<Story> stories) {
        this.stories = stories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        if(category.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", categoryName='" + categoryName + "'" +
            ", categoryRelate='" + categoryRelate + "'" +
            '}';
    }
}
