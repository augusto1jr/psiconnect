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

  const [consultasAgendadas, setConsultasAgendadas] = useState([]);
  const [consultasConcluidas, setConsultasConcluidas] = useState([]);
  const [consultasCanceladas, setConsultasCanceladas] = useState([]);

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

  const handleHome = () => {
    router.push('/psicologo/home');
  };

  const handleLogout = () => {
    localStorage.clear();
    router.push('/psicologo/login');
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
                <div key={consulta.id} className={styles.consultaCard}>
                  <p><strong>Paciente:</strong> {consulta.nomePaciente}</p>
                  <p><strong>Data:</strong> {new Date(consulta.dataConsulta).toLocaleDateString()}</p>
                  <p><strong>Horário:</strong> {new Date(consulta.dataConsulta).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
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
                  <p><strong>Paciente:</strong> {consulta.nomePaciente}</p>
                  <p><strong>Data:</strong> {new Date(consulta.dataConsulta).toLocaleDateString()}</p>
                  <p><strong>Horário:</strong> {new Date(consulta.dataConsulta).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
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
                  <p><strong>Paciente:</strong> {consulta.nomePaciente}</p>
                  <p><strong>Data:</strong> {new Date(consulta.dataConsulta).toLocaleDateString()}</p>
                  <p><strong>Horário:</strong> {new Date(consulta.dataConsulta).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
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
