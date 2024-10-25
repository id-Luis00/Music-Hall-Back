package LuQ.Music_hall.entities;

import org.springframework.data.jpa.domain.Specification;

public class SalaSpec {
    public static Specification<Sala> hasNomeSala(String nomeSala) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nomeSala"), "%" + nomeSala + "%");
    }

    public static Specification<Sala> hasComune(String comune) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("comune"), comune);
    }

    public static Specification<Sala> hasRegione(String regione) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regione"), regione);
    }

    public static Specification<Sala> hasIndirizzo(String indirizzo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("indirizzo"), "%" + indirizzo + "%");
    }

    public static Specification<Sala> hasCapienza(Integer capienza) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("capienza"), capienza);
    }

    public static Specification<Sala> hasPrezzoOrario(Double prezzoOrario) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("prezzoOrario"), prezzoOrario);
    }

    public static Specification<Sala> hasCap(String cap) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("cap"), cap);
    }
}
