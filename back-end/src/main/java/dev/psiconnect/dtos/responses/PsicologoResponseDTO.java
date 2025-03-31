package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Psicologo;

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
        EnderecoPsiResponseDTO endereco
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
                psicologo.getEndereco() != null ? new EnderecoPsiResponseDTO(psicologo.getEndereco()) : null
        );
    }
}
