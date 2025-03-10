package com.api.BlogAppApi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="TB-POSTCOMENTARIO")
public class PostComentarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Lob
    @Column(columnDefinition = "text")
    private String comentario;

    @ManyToOne
    private BlogAppPostModel postModel;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BlogAppPostModel getPostModel() {
        return postModel;
    }

    public void setPostModel(BlogAppPostModel postModel) {
        this.postModel = postModel;
    }
}
