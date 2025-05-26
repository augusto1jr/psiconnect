'use client';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import styles from '../page.module.css';

export default function Home() {
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

  useEffect(() => {
    const id = localStorage.getItem('pacienteId');
    if (!id) {
      router.push('/app/login');
      return;
    }

    fetch(`http://localhost:8080/pacientes/${id}`)
      .then(res => {
        if (!res.ok) {
          throw new Error('Erro ao buscar paciente');
        }
        return res.json();
      })
      .then(data => setPaciente(data))
      .catch(err => {
        console.error('Erro ao buscar paciente:', err);
        router.push('/app/login');
      });
  }, []);

  const handleHome = () => {
    router.push('/paciente/home')
  }

  const handleLogout = () => {
    localStorage.clear();
    router.push('/paciente/login');
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
            {/*<h1>Bem-vindo, {paciente.nome}</h1>*/}
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
