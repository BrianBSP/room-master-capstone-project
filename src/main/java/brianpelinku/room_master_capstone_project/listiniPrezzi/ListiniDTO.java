package brianpelinku.room_master_capstone_project.listiniPrezzi;

public record ListiniDTO(
        String periodoSoggiorno,
        String tipoCamera,
        String tipoServizio,
        double prezzoAdultoNotte,
        double prezzoBambinoNotte,
        String hotelId
) {
}
