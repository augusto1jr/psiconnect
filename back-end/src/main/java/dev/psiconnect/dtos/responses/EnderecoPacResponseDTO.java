package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.EnderecoPaciente;

public record EnderecoPacResponseDTO(
        Long idPaciente,
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
    public EnderecoPacResponseDTO(EnderecoPaciente endereco) {
        this(
                endereco.getIdPaciente(),
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
