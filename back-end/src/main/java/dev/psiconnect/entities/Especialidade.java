package dev.psiconnect.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "especialidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_especialidade", nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "especialidades")
    private List<Psicologo> psicologos;
}
