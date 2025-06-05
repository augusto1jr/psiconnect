'use client';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Header from '../components/header/Header';
import Sidebar from '../components/sidebar/Sidebar';
import Footer from '../components/footer/Footer';
import styles from './home.module.css';

export default function HomePsicologo() {
  const router = useRouter();
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

  const [consultas, setConsultas] = useState([]);

  useEffect(() => {
    const psicologoId = localStorage.getItem('psicologoId');
    if (!psicologoId) {
      router.push('/login');
      return;
    }

    // Dados do psicólogo
    fetch(`http://localhost:8080/psicologos/${psicologoId}`)
      .then(res => res.json())
      .then(data => setPsicologo(data))
      .catch(err => {
        console.error('Erro ao buscar psicólogo:', err);
        router.push('/login');
      });

    // Consultas do psicólogo
    fetch(`http://localhost:8080/psicologos/${psicologoId}/consultas`)
      .then(res => res.json())
      .then(data => setConsultas(data))
      .catch(err => console.error('Erro ao buscar consultas:', err));
  }, []);

  const handleHome = () => {
    router.push('/paciente/home')
  }

  const handleLogout = () => {
    localStorage.clear();
    router.push('/paciente/login');
  };

  const consultasAgendadas = consultas.filter(c => c.status === 'AGENDADA');
  const consultasConcluidas = consultas.filter(c => c.status === 'CONCLUIDA');
  const consultasCanceladas = consultas.filter(c => c.status === 'CANCELADA');

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
                <div key={consulta.id} className={styles.consultaCard}>
                  <p><strong>Paciente:</strong> {consulta.pacienteNome}</p>
                  <p><strong>Data:</strong> {consulta.data}</p>
                  <p><strong>Horário:</strong> {consulta.horario}</p>
                  <p><strong>Modalidade:</strong> {consulta.modalidade}</p>
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
                <div key={consulta.id} className={styles.consultaCard}>
                  <p><strong>Paciente:</strong> {consulta.pacienteNome}</p>
                  <p><strong>Data:</strong> {consulta.data}</p>
                  <p><strong>Horário:</strong> {consulta.horario}</p>
                  <p><strong>Modalidade:</strong> {consulta.modalidade}</p>
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
                <div key={consulta.id} className={styles.consultaCard}>
                  <p><strong>Paciente:</strong> {consulta.pacienteNome}</p>
                  <p><strong>Data:</strong> {consulta.data}</p>
                  <p><strong>Horário:</strong> {consulta.horario}</p>
                  <p><strong>Modalidade:</strong> {consulta.modalidade}</p>
                </div>
              ))
            )}
          </div>

        </div>
      </div>

      <Sidebar psicologo={psicologo} handleHome={handleHome} handleLogout={handleLogout}/>
      <Footer />
    </main>
  );
}
