'use client';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import styles from '../page.module.css';
import AgendaPsicologo from '../home/AgendaPsicologo';


export default function PerfilPsicologo() {
  const router = useRouter();

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

  const [consultasPorPsicologo, setConsultasPorPsicologo] = useState({});
  const [avaliacoesPorPsicologo, setAvaliacoesPorPsicologo] = useState({});
  const disponibilidadeMockada = [
  { diaSemana: 'SEG', data: '26 MAI', horarios: ['15:00'] },
  { diaSemana: 'TER', data: '27 MAI', horarios: ['15:00'] },
  { diaSemana: 'QUA', data: '28 MAI', horarios: ['14:00', '15:00', '16:00'] },
  { diaSemana: 'QUI', data: '29 MAI', horarios: ['18:00'] },
  ];

  useEffect(() => {
    const pacienteId = localStorage.getItem('pacienteId');
    const psicologoId = localStorage.getItem('psicologoId');

    if (!pacienteId || !psicologoId) {
      router.push('/app/paciente/login');
      return;
    }
    
    // Dados do paciente
    fetch(`http://localhost:8080/pacientes/${pacienteId}`)
    .then(res => res.json())
    .then(data => setPaciente(data))
    .catch(err => {
      console.error('Erro ao buscar paciente:', err);
      router.push('/app/login');
    });

    // Dados do psicólogo
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

    // Consultas e Avaliações de Psicólogos
  const fetchConsultasEAvaliacoes = (id) => {
  // Consultas
  fetch(`http://localhost:8080/psicologos/${id}/consultas`)
    .then(res => res.json())
    .then(data => {
      setConsultasPorPsicologo(prev => ({
        ...prev,
        [id]: data
      }));
    })
    .catch(err => console.error(err));

  // Avaliações
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

  const handleHome = () => {
    router.push('/paciente/home');
  };

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

      {/* Conteúdo Principal */}
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
              <div 
                key={psicologo.id} 
                className={styles.psicologoCard}
              >
                {/* Foto de Perfil */}
                <img
                  src={psicologo.foto || '/default-avatar.jpeg'}
                  alt={`Foto de ${psicologo.nome}`}
                  className={styles.psicologoFoto}
                />

                {/* Informações Básicas do Psicólogo */}
                <div className={styles.psicologoCardContent}>
                  <div className={styles.psicologoInfo}>
                    <h3>{psicologo.nome}</h3>
                    <p className={styles.psicologoCrp}>CRP: {psicologo.crp}</p>

                    {/* Informações de Abordagens e Especialidades */}
                    <div className={styles.chipsContainer}>
                      {psicologo.especialidades.slice(0,3).map((esp) => (
                        <span key={esp} className={styles.chip}>{esp}</span>
                      ))}
                      {psicologo.abordagens.slice(0,3).map((abo) => (
                        <span key={abo} className={styles.chip}>{abo}</span>
                      ))}
                    </div>

                    {/* Informações de Atendimento */}
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

                    {/* Avaliações e Número de Consultas */}
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

                  {/* Agenda do Psicólogo */}
                  <div className={styles.agendaContainer}>
                    <AgendaPsicologo disponibilidade={disponibilidadeMockada}/>
                  </div>
                </div>
              </div>
            );
          })()
        ) : (
          <p>Carregando perfil...</p>
        )}

        {/* Informações Extra do Psicólogo */}
        <div className={styles.psicologoInfoExtra}>

        </div>

      </div>

      {/* Barra Lateral */}
      <div className={styles.sideBar}>
        {/* Perfil */}
        <div className={styles.profileBlock}>
          <img src={paciente.foto || '/default-avatar.jpeg'} alt="Foto de perfil" className={styles.avatar} />
          <div className={styles.profileInfo}>
            <span>{paciente.nome}</span>
            <button className={styles.editButton}>
              <span className="material-symbols-outlined">edit</span>
            </button>
          </div>
        </div>

        {/* Botão Home */}
        <button onClick={handleHome} className={styles.navButton}>
          <span className="material-symbols-outlined">home</span>
          <span>Início</span>
        </button>

        {/* Botão Pesquisar */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">search</span>
          <span>Pesquisar</span>
        </button>

        {/* Botão Favoritos */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">favorite</span>
          <span>Favoritos</span>
        </button>

        {/* Botão Agenda */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">calendar_month</span>
          <span>Agenda</span>
        </button>

        {/* Botão Ajustes */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">settings</span>
          <span>Ajustes</span>
        </button>

        {/* Botão Ajuda */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">help</span>
          <span>Ajuda</span>
        </button>

        {/* Botão Sair */}
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
