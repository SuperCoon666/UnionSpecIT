package DTO;

import app.Models.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDTO {

    private Long id;
    private String headline;
    private String content;
    private String url;
    private Long idUserCreated;


    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }

    public Long getIdUserCreated() {
        return idUserCreated;
    }

    public void setIdUserCreated(Long idUserCreated) {
        this.idUserCreated = idUserCreated;
    }


    // Статический метод для преобразования Post в PostDTO
    public static PostDTO from(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setHeadline(post.getHeadline());
        dto.setContent(post.getContent());
        dto.setUrl(post.getUrl());
        dto.setIdUserCreated(post.getUser().getId());

        return dto;
    }


    // Метод для преобразования списка Post в список PostDTO
    public static List<PostDTO> from(List<Post> posts) {
        return posts.stream().map(PostDTO::from).collect(Collectors.toList());
    }
}
