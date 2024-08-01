package br.com.avilamusic.music.repository;

import br.com.avilamusic.music.model.Artista;
import br.com.avilamusic.music.model.Musicas;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nome);


    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome ILIKE %:nome%")
    List<Musicas> buscarMusicaPorArtista(String nome);

    //excluindo
    @Modifying
    @Transactional
    @Query("DELETE FROM Musicas m WHERE m.id = :id")
    void deleteMusicaById(Long id);
}
