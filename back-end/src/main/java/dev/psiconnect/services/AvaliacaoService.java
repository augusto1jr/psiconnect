package dev.psiconnect.services;

import dev.psiconnect.entities.Avaliacao;
import dev.psiconnect.repositories.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public void salvar(Avaliacao avaliacao) {
        avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> buscarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> buscarPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Optional<Avaliacao> buscarPorConsultaId(Long consultaId) {
        return avaliacaoRepository.findByConsultaId(consultaId);
    }
}
