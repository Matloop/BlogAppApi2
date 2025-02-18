package com.api.BlogAppApi.controllers;

import com.api.BlogAppApi.dtos.BlogAppRecordDto;
import com.api.BlogAppApi.models.BlogAppPostModel;
import com.api.BlogAppApi.services.BlogAppPostService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
public class BlogAppPostController {

    private final BlogAppPostService blogAppPostService;

    @Autowired
    public BlogAppPostController(BlogAppPostService blogAppPostService) {
        this.blogAppPostService = blogAppPostService;
    }

    // Endpoint para adicionar um novo post
    @PostMapping
    public ResponseEntity<BlogAppPostModel> addBlogAppPost(@RequestBody BlogAppPostModel blogAppPostModel) {
        BlogAppPostModel savedPost = blogAppPostService.addBlogAppPost(blogAppPostModel);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PostMapping("/newpost")
    public ResponseEntity<Object> savePost(@RequestBody @Valid BlogAppRecordDto blogAppRecordDto) {
        // Converte o DTO para o modelo de entidade
        BlogAppPostModel postModel = new BlogAppPostModel();
        BeanUtils.copyProperties(blogAppRecordDto, postModel);

        // Define a data atual (início do dia em UTC)
        postModel.setData(LocalDate.now(ZoneId.of("UTC")).atStartOfDay());

        // Persiste o post e retorna o objeto salvo com status 201 (Created)
        BlogAppPostModel savedPost = blogAppPostService.addBlogAppPost(postModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    // Endpoint para listar todos os posts
    @GetMapping
    public ResponseEntity<List<BlogAppPostModel>> getAllBlogAppPosts() {
        List<BlogAppPostModel> posts = blogAppPostService.getAllBlogAppPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Endpoint para buscar um post por ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogAppPostModel> getBlogAppPostById(@PathVariable UUID id) {
        Optional<BlogAppPostModel> post = blogAppPostService.getBlogAppPostById(id);
        return post.map(blogAppPostModel -> new ResponseEntity<>(blogAppPostModel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para atualizar um post existente
    @PutMapping("/{id}")
    public ResponseEntity<BlogAppPostModel> updateBlogAppPost(@PathVariable UUID id, @RequestBody BlogAppPostModel blogAppPostModel) {
        BlogAppPostModel updatedPost = blogAppPostService.updateBlogAppPost(blogAppPostModel);
        return updatedPost != null ? new ResponseEntity<>(updatedPost, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint para deletar um post por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogAppPost(@PathVariable UUID id) {
        blogAppPostService.deleteBlogAppPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        Optional<BlogAppPostModel> blogAppModelOptional = blogAppPostService.getBlogAppPostById(id);

        if (blogAppModelOptional.isEmpty()) {
            // Se o post não for encontrado, retorna 404 NOT FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Se encontrado, deleta o post pelo id e retorna 204 NO CONTENT
        blogAppPostService.deleteBlogAppPost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}