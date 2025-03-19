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
        String senhaHash,
        Double valorPadraoConsulta,
        Boolean aceitaValorSocial,
        String modalidadeAtendimento
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
                psicologo.getSenhaHash(),
                psicologo.getValorPadraoConsulta(),
                psicologo.getAceitaValorSocial(),
                psicologo.getModalidadeAtendimento().name()
        );
    }
}
