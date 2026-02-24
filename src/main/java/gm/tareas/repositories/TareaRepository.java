package gm.tareas.repositories;

import gm.tareas.model.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea,Integer> {
}
