'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Header from '../components/header/Header';
import Sidebar from '../components/sidebar/Sidebar';
import Footer from '../components/footer/Footer';
import styles from './consulta.module.css';

/**
 * Coloca a primeira letra do texto em maiúscula e o restante em minúscula.
 * 
 * @param {string} texto - Texto a ser formatado.
 * @returns {string} Texto com a primeira letra maiúscula.
 */
const formatarTexto = (texto) => {
  return texto.charAt(0).toUpperCase() + texto.slice(1).toLowerCase();
};

/**
 * Renderiza visualmente estrelas de acordo com a nota.
 * 
 * @param {number} nota - Nota de 0 a 5.
 * @returns {string} Estrelas cheias e vazias representando a nota.
 */
const renderizarEstrelas = (nota) => {
  const estrelasCheias = '★'.repeat(Math.floor(nota));
  const estrelasVazias = '☆'.repeat(5 - Math.floor(nota));
  return estrelasCheias + estrelasVazias;
};

/**
 * Página de exibição dos detalhes de uma consulta, com dados do paciente, da consulta,
 * do psicólogo e, se houver, da avaliação.
 * 
 * @component
 * @returns {JSX.Element} Componente da página de detalhes da consulta.
 */
export default function ConsultaPage() {
  const router = useRouter();

  // Estado com os dados do psicólogo
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

  // Estado da consulta, paciente e avaliação
  const [consulta, setConsulta] = useState(null);
  const [paciente, setPaciente] = useState(null);
  const [avaliacao, setAvaliacao] = useState(null);

  /**
   * Redireciona para a página inicial do psicólogo.
   */
  const handleHome = () => {
    router.push('/psicologo/home');
  };

  /**
   * Realiza logout limpando o localStorage e redirecionando para a tela de login.
   */
  const handleLogout = () => {
    localStorage.clear();
    router.push('/psicologo/login');
  };

  /**
   * Efeito que busca os dados necessários ao carregar a página:
   * consulta, paciente, psicólogo e avaliação (se houver).
   */
  useEffect(() => {
    const consultaId = localStorage.getItem('consultaId');
    const pacienteId = localStorage.getItem('pacienteId');
    const psicologoId = localStorage.getItem('psicologoId');

    if (!consultaId || !pacienteId || !psicologoId) {
      alert('Consulta, paciente ou psicólogo não selecionados!');
      return;
    }

    // Buscar dados da consulta
    fetch(`http://localhost:8080/consultas/${consultaId}`)
      .then(res => {
        if (!res.ok) throw new Error('Erro ao buscar consulta');
        return res.json();
      })
      .then(setConsulta)
      .catch(err => console.error('Erro na consulta:', err));

    // Buscar dados do paciente
    fetch(`http://localhost:8080/pacientes/${pacienteId}`)
      .then(res => {
        if (!res.ok) throw new Error('Erro ao buscar paciente');
        return res.json();
      })
      .then(setPaciente)
      .catch(err => console.error('Erro no paciente:', err));

    // Buscar dados do psicólogo
    fetch(`http://localhost:8080/psicologos/${psicologoId}`)
      .then(res => {
        if (!res.ok) throw new Error('Erro ao buscar psicólogo');
        return res.json();
      })
      .then(setPsicologo)
      .catch(err => console.error('Erro no psicólogo:', err));

    // Buscar avaliação (caso exista)
    fetch(`http://localhost:8080/avaliacoes/consulta/${consultaId}`)
      .then(res => res.ok ? res.json() : null)
      .then(data => {
        if (data) setAvaliacao(data);
      })
      .catch(err => console.error('Erro na avaliação:', err));

  }, []);

  if (!consulta || !paciente) {
    return (
      <main className={styles.main}>
        <Header />
        <p>Carregando dados...</p>
        <Footer />
      </main>
    );
  }

  const dataConsulta = new Date(consulta.dataConsulta);
  const dataFormatada = dataConsulta.toLocaleDateString();
  const horaFormatada = dataConsulta.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

  return (
    <main className={styles.main}>
      <Header />

      <div className={styles.mainContent}>
          <h1>Detalhes da Consulta</h1>

          {/* Seção da Consulta */}
          <section className={styles.consultaSection}>
            <h2>Informações da Consulta</h2>
            <p><strong>Status:</strong> {formatarTexto(consulta.status)}</p>
            <p><strong>Data:</strong> {dataFormatada}</p>
            <p><strong>Hora:</strong> {horaFormatada}</p>
            <p><strong>Modalidade:</strong> {formatarTexto(consulta.modalidade)}</p>
            <p><strong>Tipo:</strong> {formatarTexto(consulta.tipo)}</p>
            <p><strong>Valor:</strong> {Number(consulta.valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
          </section>

          {/* Seção do Paciente */}
          <section className={styles.pacienteSection}>
            <h2>Informações do Paciente</h2>
            <div className={styles.pacienteInfo}>
              <img
                src={paciente.foto || '/default-avatar.png'}
                alt={`Foto de ${paciente.nome}`}
                className={styles.pacienteFoto}
              />
              <div>
                <p><strong>Nome:</strong> {paciente.nome}</p>
                <p><strong>Bio:</strong> {paciente.bio}</p>
              </div>
            </div>
          </section>

          {/* Seção da Avaliação (se existir) */}
          {avaliacao && (
            <section className={styles.avaliacaoSection}>
              <h2>Avaliação</h2>
              <p>
                <strong>Nota:</strong> {renderizarEstrelas(avaliacao.nota)} ({avaliacao.nota})
              </p>
              <p><strong>Comentário:</strong> {avaliacao.comentario}</p>
            </section>
          )}
      </div>

      <Sidebar 
        psicologo={psicologo} 
        handleHome={handleHome} 
        handleLogout={handleLogout} 
      />
      <Footer />
    </main>
  );
}
