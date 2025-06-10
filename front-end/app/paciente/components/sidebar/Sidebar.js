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
  );
}
