'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Header from '../components/header/Header';
import Sidebar from '../components/sidebar/Sidebar';
import Footer from '../components/footer/Footer';
import styles from './home.module.css';

/**
 * Página principal do psicólogo, exibindo as consultas agendadas, concluídas e canceladas.
 *
 * @component
 * @returns {JSX.Element} Componente da página Home do psicólogo
 */
export default function HomePsicologo() {
  const router = useRouter();

  /**
   * Estado que armazena os dados do psicólogo logado.
   * @type {[object, Function]}
   */
  const [psicologo, setPsicologo] = useState({
    id: '',
    crp: '',
    nome: '',
    email: '',
    foto: '',
    bio: '',
    formacao: '',
    contato: '',
    valorConsulta: '',
    aceitaBeneficio: false,
    modalidadeAtendimento: '',
    endereco: {
      rua: '',
      numero: '',
      bairro: '',
      cidade: '',
      estado: '',
      cep: ''
    },
    especialidades: [],
    abordagens: []
  });

  /**
   * Consultas com status "AGENDADA".
   * @type {[Array, Function]}
   */
  const [consultasAgendadas, setConsultasAgendadas] = useState([]);

  /**
   * Consultas com status "CONCLUIDA".
   * @type {[Array, Function]}
   */
  const [consultasConcluidas, setConsultasConcluidas] = useState([]);

  /**
   * Consultas com status "CANCELADA".
   * @type {[Array, Function]}
   */
  const [consultasCanceladas, setConsultasCanceladas] = useState([]);

  /**
   * Lista de pacientes cadastrados no sistema.
   * @type {[Array, Function]}
   */
  const [pacientes, setPacientes] = useState([]);

  /**
   * Hook que roda ao montar o componente. Responsável por buscar os dados do psicólogo, pacientes e consultas.
   */
  useEffect(() => {
    const psicologoId = localStorage.getItem('psicologoId');
    if (!psicologoId) {
      router.push('/login');
      return;
    }

    // Buscar dados do psicólogo
    fetch(`http://localhost:8080/psicologos/${psicologoId}`)
      .then(res => res.json())
      .then(data => setPsicologo(data))
      .catch(err => {
        console.error('Erro ao buscar psicólogo:', err);
        router.push('/login');
      });

    // Buscar pacientes
    fetch(`http://localhost:8080/pacientes`)
      .then(res => res.json())
      .then(data => setPacientes(data))
      .catch(err => console.error('Erro ao buscar pacientes:', err));

    // Buscar consultas AGENDADAS
    fetch(`http://localhost:8080/psicologos/${psicologoId}/consultas?status=AGENDADA`)
      .then(res => res.json())
      .then(data => setConsultasAgendadas(data))
      .catch(err => console.error('Erro ao buscar consultas agendadas:', err));

    // Buscar consultas CONCLUIDAS
    fetch(`http://localhost:8080/psicologos/${psicologoId}/consultas?status=CONCLUIDA`)
      .then(res => res.json())
      .then(data => setConsultasConcluidas(data))
      .catch(err => console.error('Erro ao buscar consultas concluídas:', err));

    // Buscar consultas CANCELADAS
    fetch(`http://localhost:8080/psicologos/${psicologoId}/consultas?status=CANCELADA`)
      .then(res => res.json())
      .then(data => setConsultasCanceladas(data))
      .catch(err => console.error('Erro ao buscar consultas canceladas:', err));
  }, []);

  /**
   * Redireciona para a página inicial do psicólogo.
   */
  const handleHome = () => {
    router.push('/psicologo/home');
  };

  /**
   * Efetua logout limpando o localStorage e redireciona para a tela de login.
   */
  const handleLogout = () => {
    localStorage.clear();
    router.push('/psicologo/login');
  };

  /**
   * Retorna a URL da foto do paciente ou uma imagem padrão.
   *
   * @param {number} idPaciente - ID do paciente
   * @returns {string} URL da foto
   */
  const getFotoPaciente = (idPaciente) => {
    const paciente = pacientes.find(p => p.id === idPaciente);
    return paciente ? paciente.foto : '/default-avatar.png';
  };

  /**
   * Armazena no localStorage os IDs da consulta e do paciente, e redireciona para a tela de consulta.
   *
   * @param {number} consultaId - ID da consulta
   * @param {number} pacienteId - ID do paciente
   */
  const handleOpenConsulta = (consultaId, pacienteId) => {
    localStorage.setItem('consultaId', consultaId);
    localStorage.setItem('pacienteId', pacienteId);
    router.push('/psicologo/consulta');
  };

  return (
    <main className={styles.main}>
      <Header />

      <div className={styles.mainContent}>
        <h1 className={styles.pageTitle}>Minhas Consultas</h1>

        <div className={styles.consultasContainer}>

          {/* Consultas Agendadas */}
          <div className={styles.consultaSection}>
            <h2>Agendadas</h2>
            {consultasAgendadas.length === 0 ? (
              <p>Sem consultas agendadas.</p>
            ) : (
              consultasAgendadas.map(consulta => (
                <div
                  key={consulta.id}
                  className={styles.consultaCard}
                  onClick={() => handleOpenConsulta(consulta.id, consulta.idPaciente)}
                >
                  <div className={styles.infoConsulta}>
                    <p><strong>Paciente:</strong> {consulta.nomePaciente}</p>
                    <p><strong>Data:</strong> {new Date(consulta.dataConsulta).toLocaleDateString()}</p>
                    <p><strong>Horário:</strong> {new Date(consulta.dataConsulta).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
                    <p><strong>Modalidade:</strong> {consulta.modalidade}</p>
                  </div>
                  <div className={styles.fotoPaciente}>
                    <img src={getFotoPaciente(consulta.idPaciente)} alt={`Foto de ${consulta.nomePaciente}`} />
                  </div>
                </div>
              ))
            )}
          </div>

          {/* Consultas Concluídas */}
          <div className={styles.consultaSection}>
            <h2>Concluídas</h2>
            {consultasConcluidas.length === 0 ? (
              <p>Sem consultas concluídas.</p>
            ) : (
              consultasConcluidas.map(consulta => (
                <div
                  key={consulta.id}
                  className={styles.consultaCard}
                  onClick={() => handleOpenConsulta(consulta.id, consulta.idPaciente)}
                >
                  <div className={styles.infoConsulta}>
                    <p><strong>Paciente:</strong> {consulta.nomePaciente}</p>
                    <p><strong>Data:</strong> {new Date(consulta.dataConsulta).toLocaleDateString()}</p>
                    <p><strong>Horário:</strong> {new Date(consulta.dataConsulta).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
                    <p><strong>Modalidade:</strong> {consulta.modalidade}</p>
                  </div>
                  <div className={styles.fotoPaciente}>
                    <img src={getFotoPaciente(consulta.idPaciente)} alt={`Foto de ${consulta.nomePaciente}`} />
                  </div>
                </div>
              ))
            )}
          </div>

          {/* Consultas Canceladas */}
          <div className={styles.consultaSection}>
            <h2>Canceladas</h2>
            {consultasCanceladas.length === 0 ? (
              <p>Sem consultas canceladas.</p>
            ) : (
              consultasCanceladas.map(consulta => (
                <div
                  key={consulta.id}
                  className={styles.consultaCard}
                  onClick={() => handleOpenConsulta(consulta.id, consulta.idPaciente)}
                >
                  <div className={styles.infoConsulta}>
                    <p><strong>Paciente:</strong> {consulta.nomePaciente}</p>
                    <p><strong>Data:</strong> {new Date(consulta.dataConsulta).toLocaleDateString()}</p>
                    <p><strong>Horário:</strong> {new Date(consulta.dataConsulta).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
                    <p><strong>Modalidade:</strong> {consulta.modalidade}</p>
                  </div>
                  <div className={styles.fotoPaciente}>
                    <img src={getFotoPaciente(consulta.idPaciente)} alt={`Foto de ${consulta.nomePaciente}`} />
                  </div>
                </div>
              ))
            )}
          </div>

        </div>
      </div>

      <Sidebar psicologo={psicologo} handleHome={handleHome} handleLogout={handleLogout} />
      <Footer />
    </main>
  );
}
