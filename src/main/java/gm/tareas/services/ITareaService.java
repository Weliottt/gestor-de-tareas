package gm.tareas.services;

import gm.tareas.model.entities.Tarea;

import java.util.List;
import java.util.Optional;

public interface ITareaService {

    public List<Tarea> getTareas();

    public Optional<Tarea> getTareaById(Integer idTarea);

    public void saveTarea(Tarea tarea);

    public void deleteTarea(Tarea tarea);
}
