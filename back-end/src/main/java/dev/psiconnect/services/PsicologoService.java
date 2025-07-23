package dev.psiconnect.services;

import dev.psiconnect.entities.Psicologo;
import dev.psiconnect.repositories.PsicologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PsicologoService {

    @Autowired
    private PsicologoRepository psicologoRepository;

    public void salvar(Psicologo psicologo) {
        psicologoRepository.save(psicologo);
    }

    public List<Psicologo> buscarTodos() {
        return psicologoRepository.findAll();
    }

    public Optional<Psicologo> buscarPorId(Long id) {
        return psicologoRepository.findById(id);
    }

    public Psicologo buscarPorEmail(String email) {
        return psicologoRepository.findByEmail(email);
    }

    public boolean existePorId(Long id) {
        return psicologoRepository.existsById(id);
    }

    public void deletarPorId(Long id) {
        psicologoRepository.deleteById(id);
    }
}
