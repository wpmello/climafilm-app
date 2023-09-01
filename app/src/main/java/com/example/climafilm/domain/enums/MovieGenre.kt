package com.example.climafilm.domain.enums

enum class MovieGenre(val id: Int, val genreName: String) {
    ACAO(28, "Ação"),
    AVENTURA(12, "Aventura"),
    ANIMACAO(16, "Animação"),
    COMEDIA(35, "Comédia"),
    CRIME(80, "Crime"),
    DOCUMENTARIO(99, "Documentário"),
    DRAMA(18, "Drama"),
    FAMILIA(10751, "Família"),
    FANTASIA(14, "Fantasia"),
    HISTORIA(36, "História"),
    TERROR(27, "Terror"),
    MUSICA(10402, "Música"),
    MISTERIO(9648, "Mistério"),
    ROMANCE(10749, "Romance"),
    FICCAO_CIENTIFICA(878, "Ficção Científica"),
    CINEMA_TV(10770, "Cinema TV"),
    THRILLER(53, "Thriller"),
    GUERRA(10752, "Guerra"),
    FAROESTE(37, "Faroeste");
}

fun mapGenreIdsToNames(genreIds: List<Int>): List<String> {
    return genreIds.map { id ->
        MovieGenre.values().find { it.id == id }?.genreName ?: "Desconhecido"
    }
}