import styles from './Sidebar.module.css';

export default function Sidebar({ psicologo, handleHome, handleLogout }) {
  return (
    <div className={styles.sideBar}>

        {/* Perfil */}
        <div className={styles.profileBlock}>
            <img src={psicologo.foto || '/default-avatar.jpeg'} alt="Foto de perfil" className={styles.avatar} />
            <div className={styles.profileInfo}>
                <span>{psicologo.nome}</span>
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

        {/* Botão Agendadas */}
        <button className={styles.navButton}>
            <span className="material-symbols-outlined">event_available</span>
            <span>Agendadas</span>
        </button>

        {/* Botão Concluídas */}
        <button className={styles.navButton}>
            <span className="material-symbols-outlined">task_alt</span>
            <span>Concluídas</span>
        </button>

        {/* Botão Canceladas */}
        <button className={styles.navButton}>
            <span className="material-symbols-outlined">cancel</span>
            <span>Canceladas</span>
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
                <span className="material-symbols-outlined">logout</span>
                Sair
            </button>
        </div>
    </div>
  );
}
