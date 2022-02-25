package com.erick.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String image;
    private UserDTO userAlbums;
}