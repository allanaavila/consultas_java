package br.com.avilamusic.music.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Musicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne
    private Artista artistas;

    public Musicas() {}

    public Musicas(String nomeMusica) {
        this.titulo = nomeMusica;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artista getArtista() {
        return artistas;
    }

    public void setArtista(Artista artistas) {
        this.artistas = artistas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return  "Música = '" + titulo + '\'' +
                ", artista = " + artistas.getNome();
    }
}
