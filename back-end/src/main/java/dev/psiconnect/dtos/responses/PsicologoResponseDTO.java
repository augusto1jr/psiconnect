package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Psicologo;
import dev.psiconnect.entities.Especialidade;
import dev.psiconnect.entities.Abordagem;

import java.util.List;
import java.util.stream.Collectors;

public record PsicologoResponseDTO(
        Long id,
        String crp,
        String nome,
        String email,
        String foto,
        String bio,
        String formacao,
        String contato,
        Double valorConsulta,
        Boolean aceitaBeneficio,
        String modalidadeAtendimento,
        EnderecoPsiResponseDTO endereco,
        List<String> especialidades,
        List<String> abordagens
) {
    public PsicologoResponseDTO(Psicologo psicologo) {
        this(
                psicologo.getId(),
                psicologo.getCrp(),
                psicologo.getNome(),
                psicologo.getEmail(),
                psicologo.getFoto(),
                psicologo.getBio(),
                psicologo.getFormacao(),
                psicologo.getContato(),
                psicologo.getValorConsulta(),
                psicologo.getAceitaBeneficio(),
                psicologo.getModalidadeAtendimento().name(),
                psicologo.getEndereco() != null ? new EnderecoPsiResponseDTO(psicologo.getEndereco()) : null,
                psicologo.getEspecialidades() != null ? psicologo.getEspecialidades().stream().map(Especialidade::getNome).collect(Collectors.toList()) : null,
                psicologo.getAbordagens() != null ? psicologo.getAbordagens().stream().map(Abordagem::getNome).collect(Collectors.toList()) : null
        );
    }
}
