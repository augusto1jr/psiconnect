'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import styles from '../page.module.css';
import AgendaPsicologo from '../home/AgendaPsicologo';
import AvaliacoesPsicologo from './AvaliacoesPsicologo';

/**
 * Componente que exibe o perfil completo de um psicólogo para o paciente.
 * Inclui foto, CRP, especialidades, abordagens, bio, valor da consulta, agenda,
 * avaliações e número de atendimentos.
 * Também possui barra lateral com menu de navegação e informações do paciente logado.
 * 
 * @component
 * @returns {JSX.Element}
 */
export default function PerfilPsicologo() {
  const router = useRouter();

  /**
   * Estado para armazenar os dados do paciente logado.
   * @type {[Object, Function]}
   */
  const [paciente, setPaciente] = useState({
    id: '',
    cpf: '',
    nome: '',
    email: '',
    foto: '',
    bio: '',
    contato: '',
    beneficioSocial: '',
    endereco: {
      rua: '',
      numero: '',
      bairro: '',
      cidade: '',
      estado: '',
      cep: ''
    },
    prefEspecialidades: [],
    prefAbordagens: []
  });

  /**
   * Estado para armazenar os dados do psicólogo visualizado.
   *  @type {(Object|Function)[]} 
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
   * Armazena as consultas do psicólogo por ID.
   * @type {[Object.<string, Array>, Function]}
   */
  const [consultasPorPsicologo, setConsultasPorPsicologo] = useState({});

  /**
   * Armazena as avaliações do psicólogo por ID.
   * @type {[Object.<string, Array>, Function]}
   */
  const [avaliacoesPorPsicologo, setAvaliacoesPorPsicologo] = useState({});

  /**
   * Disponibilidade fictícia usada para exibição de agenda.
   * @type {Array<{diaSemana: string, data: string, horarios: string[]}>}
   */
  const disponibilidadeMockada = [
    { diaSemana: 'QUA', data: '18 JUN', horarios: ['18:00'] },
    { diaSemana: 'QUI', data: '19 JUN', horarios: ['14:00', '15:00', '16:00'] },
    { diaSemana: 'SEX', data: '20 JUN', horarios: ['18:00'] },
    { diaSemana: 'SAB', data: '21 JUN', horarios: ['10:00'] }
  ];

  /**
   * Efeito que roda ao montar o componente.
   * Busca os dados do paciente e do psicólogo no backend.
   */
  useEffect(() => {
    const pacienteId = localStorage.getItem('pacienteId');
    const psicologoId = localStorage.getItem('psicologoId');

    if (!pacienteId || !psicologoId) {
      router.push('/app/paciente/login');
      return;
    }

    // Buscar dados do paciente
    fetch(`http://localhost:8080/pacientes/${pacienteId}`)
      .then(res => res.json())
      .then(data => setPaciente(data))
      .catch(err => {
        console.error('Erro ao buscar paciente:', err);
        router.push('/app/login');
      });

    // Buscar dados do psicólogo
    fetch(`http://localhost:8080/psicologos/${psicologoId}`)
      .then(res => res.json())
      .then(data => {
        setPsicologo(data);
        fetchConsultasEAvaliacoes(data.id);
      })
      .catch(err => {
        console.error('Erro ao buscar psicólogo:', err);
        router.push('/app/login');
      });
  }, []);

  /**
   * Busca as consultas e avaliações de um psicólogo específico.
   * @param {string} id - ID do psicólogo.
   */
  const fetchConsultasEAvaliacoes = (id) => {
    fetch(`http://localhost:8080/psicologos/${id}/consultas`)
      .then(res => res.json())
      .then(data => {
        setConsultasPorPsicologo(prev => ({
          ...prev,
          [id]: data
        }));
      })
      .catch(err => console.error(err));

    fetch(`http://localhost:8080/psicologos/${id}/avaliacoes`)
      .then(res => res.json())
      .then(data => {
        setAvaliacoesPorPsicologo(prev => ({
          ...prev,
          [id]: data
        }));
      })
      .catch(err => console.error(err));
  };

  /**
   * Redireciona para a tela inicial do paciente.
   */
  const handleHome = () => {
    router.push('/paciente/home');
  };

  /**
   * Realiza logout limpando o localStorage e redirecionando para o login.
   */
  const handleLogout = () => {
    localStorage.clear();
    router.push('/paciente/login');
  };

  return (
    <main className={styles.main}>
      {/* Cabeçalho */}
      <header className={styles.header}>
        <div className={styles.logoContainer}>
          <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage} />
          <h1 className={styles.title}>PsiConnect</h1>
        </div>
      </header>

      {/* Conteúdo principal com perfil do psicólogo */}
      <div className={styles.mainContent}>
        {psicologo.id ? (
          (() => {
            const consultas = consultasPorPsicologo[psicologo.id] || [];
            const avaliacoes = avaliacoesPorPsicologo[psicologo.id] || [];

            const totalAtendimentos = consultas.length;
            const mediaAvaliacoes =
              avaliacoes.length > 0
                ? (avaliacoes.reduce((sum, item) => sum + item.nota, 0) / avaliacoes.length).toFixed(1)
                : 'N/A';

            return (
              <div className={styles.psicologoContainer}>
                {/* Card principal do psicólogo */}
                <div className={styles.psicologoCard}>
                  <img
                    src={psicologo.foto || '/default-avatar.jpeg'}
                    alt={`Foto de ${psicologo.nome}`}
                    className={styles.psicologoFoto}
                  />
                  <div className={styles.psicologoCardContent}>
                    <div className={styles.psicologoInfo}>
                      <h3>{psicologo.nome}</h3>
                      <p className={styles.psicologoCrp}>CRP: {psicologo.crp}</p>

                      {/* Chips com especialidades e abordagens */}
                      <div className={styles.chipsContainer}>
                        {psicologo.especialidades.slice(0, 3).map((esp) => (
                          <span key={esp} className={styles.chip}>{esp}</span>
                        ))}
                        {psicologo.abordagens.slice(0, 3).map((abo) => (
                          <span key={abo} className={styles.chip}>{abo}</span>
                        ))}
                      </div>

                      <p className={styles.psicologoBio}>{psicologo.bio}</p>
                      <p className={styles.psicologoValor}>
                        {psicologo.valorConsulta} R$/50min{' '}
                        {psicologo.aceitaBeneficio && (
                          <span className={styles.valorSocial}>Valor Social Disponível</span>
                        )}
                      </p>
                      <p className={styles.psicologoModalidade}>
                        {psicologo.modalidadeAtendimento === 'REMOTO'
                          ? 'Remoto'
                          : psicologo.modalidadeAtendimento === 'PRESENCIAL'
                          ? 'Presencial'
                          : 'Híbrido'}
                      </p>

                      {/* Estatísticas */}
                      <div className={styles.psicologoStats}>
                        <div className={styles.psicologoStat}>
                          <span className="material-symbols-outlined starIcon">star</span>
                          <strong>{mediaAvaliacoes}</strong>
                          <span>({avaliacoes.length} comentários)</span>
                        </div>
                        <div className={styles.psicologoStat}>
                          <span className="material-symbols-outlined">group</span>
                          <strong>{totalAtendimentos}</strong>
                          <span>atendimentos</span>
                        </div>
                      </div>
                    </div>
                    {/* Agenda do psicólogo */}
                    <div className={styles.agendaContainer}>
                      <AgendaPsicologo disponibilidade={disponibilidadeMockada} />
                    </div>
                  </div>
                </div>

                {/* Informações extras */}
                <div className={styles.psicologoInfoExtra}>
                  <div className={styles.psicologosEspecialidades}>
                    <h3>Especialidades do Psicólogo:</h3>
                    <ul>
                      {psicologo.especialidades.map((esp) => (
                        <li key={esp}>{esp}</li>
                      ))}
                    </ul>
                  </div>

                  <div className={styles.psicologosAbordagens}>
                    <h3>Abordagens que o Psicólogo trabalha:</h3>
                    <ul>
                      {psicologo.abordagens.map((abo) => (
                        <li key={abo}>{abo}</li>
                      ))}
                    </ul>
                  </div>

                  <div className={styles.psicologosFormacao}>
                    <h3>Formação do Psicólogo:</h3>
                    <p>{psicologo.formacao}</p>
                  </div>
                </div>

                {/* Avaliações dos pacientes */}
                <div className={styles.avaliacoesContainer}>
                  <AvaliacoesPsicologo avaliacoes={avaliacoes} />
                </div>
              </div>
            );
          })()
        ) : (
          <p>Carregando perfil...</p>
        )}
      </div>

      {/* Barra lateral de navegação */}
      <div className={styles.sideBar}>
        <div className={styles.profileBlock}>
          <img src={paciente.foto || '/default-avatar.jpeg'} alt="Foto de perfil" className={styles.avatar} />
          <div className={styles.profileInfo}>
            <span>{paciente.nome}</span>
            <button className={styles.editButton}>
              <span className="material-symbols-outlined">edit</span>
            </button>
          </div>
        </div>

        {/* Botões de navegação */}
        <button onClick={handleHome} className={styles.navButton}>
          <span className="material-symbols-outlined">home</span>
          <span>Início</span>
        </button>

        <button className={styles.navButton}>
          <span className="material-symbols-outlined">search</span>
          <span>Pesquisar</span>
        </button>

        <button className={styles.navButton}>
          <span className="material-symbols-outlined">favorite</span>
          <span>Favoritos</span>
        </button>

        <button className={styles.navButton}>
          <span className="material-symbols-outlined">calendar_month</span>
          <span>Agenda</span>
        </button>

        <button className={styles.navButton}>
          <span className="material-symbols-outlined">settings</span>
          <span>Ajustes</span>
        </button>

        <button className={styles.navButton}>
          <span className="material-symbols-outlined">help</span>
          <span>Ajuda</span>
        </button>

        <div className={styles.logoutBlock}>
          <button onClick={handleLogout} className={styles.logoutButton}>
            <span className="material-symbols-outlined">logout</span>Sair
          </button>
        </div>
      </div>

      {/* Rodapé */}
      <footer className={styles.footer}>
        <p>PsiConnect &copy;</p>
      </footer>
    </main>
  );
}
