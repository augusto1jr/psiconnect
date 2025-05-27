import styles from './AgendaPsicologo.module.css';

const AgendaPsicologo = ({ disponibilidade }) => {
  return (
    <div className={styles.agendaContainer}>
      {disponibilidade.map((dia, index) => (
        <div key={index} className={styles.dia}>
          <div className={styles.diaCabecalho}>
            <p className={styles.diaSemana}>{dia.diaSemana}</p>
            <p className={styles.diaData}>{dia.data}</p>
          </div>
          <div className={styles.horarios}>
            {dia.horarios.map((hora, i) => (
              <div key={i} className={styles.horario}>
                {hora}
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default AgendaPsicologo;
