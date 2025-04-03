package dev.psiconnect.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "abordagens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Abordagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_abordagem", nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "abordagens")
    private List<Psicologo> psicologos;
}
