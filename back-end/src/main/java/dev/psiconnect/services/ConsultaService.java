package dev.psiconnect.services;

import dev.psiconnect.entities.Consulta;
import dev.psiconnect.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void salvar(Consulta consulta) {
        consultaRepository.save(consulta);
    }

    public List<Consulta> buscarTodos() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public boolean existePorId(Long id) {
        return consultaRepository.existsById(id);
    }

    public void deletarPorId(Long id) {
        consultaRepository.deleteById(id);
    }
}
