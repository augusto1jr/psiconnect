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
        String bio,
        String formacao,
        String contato,
        Double valorPadraoConsulta,
        Boolean aceitaValorSocial,
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
                psicologo.getBio(),
                psicologo.getFormacao(),
                psicologo.getContato(),
                psicologo.getValorPadraoConsulta(),
                psicologo.getAceitaValorSocial(),
                psicologo.getModalidadeAtendimento().name(),
                psicologo.getEndereco() != null ? new EnderecoPsiResponseDTO(psicologo.getEndereco()) : null,
                psicologo.getEspecialidades() != null ? psicologo.getEspecialidades().stream().map(Especialidade::getNome).collect(Collectors.toList()) : null,
                psicologo.getAbordagens() != null ? psicologo.getAbordagens().stream().map(Abordagem::getNome).collect(Collectors.toList()) : null
        );
    }
}
