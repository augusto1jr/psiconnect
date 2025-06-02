import { useState } from 'react';
import styles from './AgendaPsicologo.module.css';

const AgendaPsicologo = ({ disponibilidade }) => {
  const [horarioSelecionado, setHorarioSelecionado] = useState(null);

  const handleSelecionarHorario = (dia, hora) => {
    setHorarioSelecionado({ dia, hora });
  };

  return (
    <div className={styles.agendaContainer}>
      
      <div className={styles.diasContainer}>
        {disponibilidade.map((dia, index) => (
          <div key={index} className={styles.dia}>
            <div className={styles.diaCabecalho}>
              <p className={styles.diaSemana}>{dia.diaSemana}</p>
              <p className={styles.diaData}>{dia.data}</p>
            </div>
            <div className={styles.horarios}>
              {dia.horarios.map((hora, i) => {
                const isSelecionado =
                  horarioSelecionado?.dia === dia.data &&
                  horarioSelecionado?.hora === hora;

                return (
                  <div
                    key={i}
                    className={`${styles.horario} ${
                      isSelecionado ? styles.horarioSelecionado : ''
                    }`}
                    onClick={() => handleSelecionarHorario(dia.data, hora)}
                  >
                    {hora}
                  </div>
                );
              })}
            </div>
          </div>
        ))}
      </div>

      <button
        className={styles.agendarButton}
        disabled={!horarioSelecionado}
      >
        <span className="material-symbols-outlined">calendar_month</span>
        <span>Agendar</span>
      </button>
    </div>
  );
};

export default AgendaPsicologo;
