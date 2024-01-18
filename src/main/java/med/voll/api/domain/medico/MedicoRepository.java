package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico,Long> {

    Page<Medico> findByActivoTrue(Pageable paginacion);
//primer cambio en intellij
@Query("""
        select n from Medico n
        where n.activo= 1 and
        n.especialidad=:especialidad and
        n.id not in(
        select c.medico.id from Consulta c
        c.data=:fecha
        )
        order by rand()
        limit 1
        """)
    Medico selecionarMedicoConEspecialidad(Especialidad especialidad, LocalDateTime fecha);
}
