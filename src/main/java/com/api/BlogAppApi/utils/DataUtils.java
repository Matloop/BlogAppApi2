package com.api.BlogAppApi.utils;

import com.api.BlogAppApi.models.BlogAppPostModel;
import com.api.BlogAppApi.repositories.BlogAppPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataUtils {
    private final BlogAppPostRepository blogAppPostRepository;

    @Autowired
    public DataUtils(BlogAppPostRepository blogAppPostRepository) {
        this.blogAppPostRepository = blogAppPostRepository;
    }

    public void savePosts() {
        List<BlogAppPostModel> postList = new ArrayList<>();
        postList.add(createPost("Lula", "Title 1", "Text 1"));
        postList.add(createPost("Lula", "Title 2", "Text 2"));

        for (BlogAppPostModel post : postList) {
            BlogAppPostModel postSaved = blogAppPostRepository.save(post);
            System.out.println("Saved post with ID: " + postSaved.getId());
        }
    }

    private BlogAppPostModel createPost(String autor, String titulo, String texto) {
        BlogAppPostModel post = new BlogAppPostModel();
        post.setAutor(autor);
        post.setData(LocalDateTime.now());
        post.setTitulo(titulo);
        post.setTexto(texto);
        return post;
    }
}