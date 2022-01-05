package com.erick.blog.dtos;

import java.io.Serializable;
import java.time.Instant;

public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String text;
    private Instant date;
    private PostDTO post;
    private UserDTO userComment;

    public CommentDTO() {
    }

    public CommentDTO(Long id, String text, Instant date, PostDTO post, UserDTO userComment) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.post = post;
        this.userComment = userComment;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    public UserDTO getUserComment() {
        return userComment;
    }

    public void setUserComment(UserDTO userComment) {
        this.userComment = userComment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommentDTO other = (CommentDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}