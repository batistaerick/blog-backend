package com.erick.blog.dtos;

import java.io.Serializable;

public class AlbumDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String image;
    private UserDTO userAlbums;

    public AlbumDTO() {
    }

    public AlbumDTO(Long id, String image, UserDTO userAlbums) {
        this.id = id;
        this.image = image;
        this.userAlbums = userAlbums;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserDTO getUserAlbums() {
        return userAlbums;
    }

    public void setUserAlbums(UserDTO userAlbums) {
        this.userAlbums = userAlbums;
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
        AlbumDTO other = (AlbumDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}