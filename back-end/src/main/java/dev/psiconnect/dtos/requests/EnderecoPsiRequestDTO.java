package dev.psiconnect.dtos.requests;

public record EnderecoPsiRequestDTO(
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Double latitude,
        Double longitude
) {}
