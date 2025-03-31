package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.EnderecoPsicologo;

public record EnderecoPsiResponseDTO(
        Long idPsicologo,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Double latitude,
        Double longitude
) {
    public EnderecoPsiResponseDTO(EnderecoPsicologo endereco) {
        this(
                endereco.getIdPsicologo(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getLatitude(),
                endereco.getLongitude()
        );
    }
}
