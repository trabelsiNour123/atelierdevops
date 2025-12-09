package tn.esprit.devopsatelier.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class animal {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nom;
        private String espece;
        private String etat;
}
