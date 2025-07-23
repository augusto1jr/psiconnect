package dev.psiconnect.services;

import dev.psiconnect.entities.Paciente;
import dev.psiconnect.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void salvar(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente buscarPorEmail(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public boolean existePorId(Long id) {
        return pacienteRepository.existsById(id);
    }

    public void deletarPorId(Long id) {
        pacienteRepository.deleteById(id);
    }
}
