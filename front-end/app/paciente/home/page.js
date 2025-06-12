'use client';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import styles from '../page.module.css';
import AgendaPsicologo from './AgendaPsicologo';

/**
 * Página inicial do paciente.
 * Exibe lista de psicólogos recomendados com informações e agenda.
 * Também contém barra lateral com navegação e botão de logout.
 */
export default function Home() {
  const router = useRouter();

  /**
   * Estado que armazena os dados do paciente logado.
   * @type {Object}
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
   * Lista de psicólogos recomendados ao paciente.
   * @type {Array}
   */
  const [psicologos, setPsicologos] = useState([]);

  /**
   * Consultas agrupadas por ID de psicólogo.
   * @type {Object.<string, Array>}
   */
  const [consultasPorPsicologo, setConsultasPorPsicologo] = useState({});

  /**
   * Avaliações agrupadas por ID de psicólogo.
   * @type {Object.<string, Array>}
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

  // useEffect para carregar dados ao montar a página
  useEffect(() => {
    const id = localStorage.getItem('pacienteId');
    if (!id) {
      router.push('/app/login');
      return;
    }

    // Buscar dados do paciente
    fetch(`http://localhost:8080/pacientes/${id}`)
      .then(res => res.json())
      .then(data => setPaciente(data))
      .catch(err => {
        console.error('Erro ao buscar paciente:', err);
        router.push('/app/login');
      });

    // Buscar psicólogos recomendados
    fetch('http://localhost:8080/pacientes/recomendados')
      .then(res => res.json())
      .then(data => {
        setPsicologos(data);
        data.forEach(psicologo => {
          fetchConsultasEAvaliacoes(psicologo.id);
        });
      })
      .catch(err => console.error('Erro ao buscar psicólogos:', err));
  }, []);

  /**
   * Busca consultas e avaliações de um psicólogo específico.
   * Atualiza os estados `consultasPorPsicologo` e `avaliacoesPorPsicologo`.
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
   * Redireciona o paciente para a tela principal.
   */
  const handleHome = () => {
    router.push('/paciente/home');
  };

  /**
   * Realiza o logout do paciente, limpando o localStorage.
   */
  const handleLogout = () => {
    localStorage.clear();
    router.push('/paciente/login');
  };

  /**
   * Armazena os IDs no localStorage e redireciona para a página do psicólogo.
   * @param {string} pacienteId - ID do paciente.
   * @param {string} psicologoId - ID do psicólogo.
   */
  const handleOpenPerfilPsicologo = (pacienteId, psicologoId) => {
    localStorage.clear();
    localStorage.setItem('pacienteId', pacienteId);
    localStorage.setItem('psicologoId', psicologoId);
    router.push('/paciente/perfil-psicologo');
  };

  return (
    <main className={styles.main}>
        {/* Cabeçalho */}
        <header className={styles.header}>
            <div className={styles.logoContainer}>
                <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage}/>
                <h1 className={styles.title}>PsiConnect</h1>
            </div>
        </header>

      {/* Conteúdo Principal */}
      <div className={styles.mainContent}>
        <div className={styles.psicologoList}>
          {psicologos.map(psicologo => {
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
                  onClick={() => handleOpenPerfilPsicologo(paciente.id, psicologo.id)}
                  style={{ cursor: 'pointer' }}
                />

                {/* Informações Básicas do Psicólogo */}
                <div className={styles.psicologoCardContent}>
                  <div 
                  className={styles.psicologoInfo}
                  onClick={() => handleOpenPerfilPsicologo(paciente.id, psicologo.id)}
                  style={{ cursor: 'pointer' }}>

                    {/* Nome e CRP do Psicólogo */}
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
                    {/* Informações de Atendimento: Bio, Valor, Benefício, Modalidade */}
                    <p className={styles.psicologoBio}>{psicologo.bio}</p>
                    <p className={styles.psicologoValor}>
                      {psicologo.valorConsulta}R$/50min{' '}
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
          })}
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
