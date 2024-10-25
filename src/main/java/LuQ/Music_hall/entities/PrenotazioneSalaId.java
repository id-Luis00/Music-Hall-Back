//package LuQ.Music_hall.entities;
//
//import jakarta.persistence.Embeddable;
//
//import java.io.Serializable;
//import java.util.Objects;
//import java.util.UUID;
//
//@Embeddable
//public class PrenotazioneSalaId implements Serializable {
//    private UUID idUtente;
//    private UUID idSala;
//
//    public PrenotazioneSalaId() {
//    }
//
//    public PrenotazioneSalaId(UUID idUtente, UUID idSala) {
//        this.idUtente = idUtente;
//        this.idSala = idSala;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PrenotazioneSalaId that = (PrenotazioneSalaId) o;
//        return Objects.equals(idUtente, that.idUtente) && Objects.equals(idSala, that.idSala);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(idUtente, idSala);
//    }
//}
