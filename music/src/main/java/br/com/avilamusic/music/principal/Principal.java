package br.com.avilamusic.music.principal;

import br.com.avilamusic.music.model.Artista;
import br.com.avilamusic.music.model.Musicas;
import br.com.avilamusic.music.model.TipoArtista;
import br.com.avilamusic.music.repository.ArtistaRepository;
import br.com.avilamusic.music.service.ConsultaMyMemory;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final ArtistaRepository repositorio;
    private Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    === MUSIC ÁVILA ===
                    
                    1. Cadastrar artistas
                    2. Cadastrar músicas
                    3. Listar músicas
                    4. Buscar músicas por artistas
                    5. Excluir cadastro
                    6. Alterar nome do artista
                    
                    0. Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscasMusicasPorArtista();
                    break;
                case 5:
                    excluirCadastroDoArtista();
                    break;
                case 6:
                    alterarCadastroDoArtista();
                    break;
                case 0:
                    System.out.println("Você saiu da aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void cadastrarArtistas() {
        var cadastrarNovo = "S";

        while(cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do artista: ");
            var nome = leitura.nextLine();
            System.out.println("Qual é o tipo musical do artista: (SOLO, DUPLA ou BANDA): ");
            var tipoMusical = leitura.nextLine();

            TipoArtista tipoArtista = TipoArtista.valueOf(tipoMusical.toUpperCase());

            Artista artista = new Artista(nome, tipoArtista);
            repositorio.save(artista);
            System.out.println("Deseja cadastrar um novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }


    public void cadastrarMusicas() {
        System.out.println("Cadastrar música de que artista?: ");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);

        if (artista.isPresent()) {
            System.out.println("Informe o título da música: ");
            var nomeMusica = leitura.nextLine();
            Musicas musica = new Musicas(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        } else {
            System.out.println("Artista não encontrado");
        }
    }


    public void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a ->
                a.getMusicas().forEach(System.out::println));
    }

    public void buscasMusicasPorArtista() {
        System.out.println("Buscas música de que artista? ");
        var nome = leitura.nextLine();
        List<Musicas> musicas = repositorio.buscarMusicaPorArtista(nome);
        musicas.forEach(System.out::println);

    }

    public void excluirCadastroDoArtista() {
        System.out.println("Excluir cadastro de qual artista?: ");
        var nomeArtista = leitura.nextLine();

        Optional<Artista> artistaOptional = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

        if (artistaOptional.isPresent()) {
            Artista artista = artistaOptional.get();
            List<Musicas> musicas = artista.getMusicas();
            musicas.forEach(m -> repositorio.deleteMusicaById(m.getId()));
            repositorio.delete(artista);
            System.out.println("Cadastro excluído com sucesso!");
        } else {
            System.out.println("Artista não encontrado.");
        }
    }

    public void alterarCadastroDoArtista() {
        System.out.println("Atualizar dados de qual artista?: ");
        var nomeArtista = leitura.nextLine();
        Optional<Artista> artistaOptional = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

        if (artistaOptional.isPresent()) {
            Artista artista = artistaOptional.get();
            // Atualize o nome do artista
            System.out.println("Novo nome para o artista: ");
            var novoNome = leitura.nextLine();
            if (!novoNome.isBlank()) {
                artista.setNome(novoNome);
                repositorio.save(artista);
                System.out.println("Nome do artista atualizado com sucesso!");
            } else {
                System.out.println("Nome não alterado.");
            }
        } else {
            System.out.println("Artista não encontrado.");
        }
    }
}
