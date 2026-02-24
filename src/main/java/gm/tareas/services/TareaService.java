package gm.tareas.services;

import gm.tareas.model.entities.Tarea;
import gm.tareas.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService implements ITareaService{

    @Autowired
    TareaRepository tareaRepository;

    @Override
    public List<Tarea> getTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> getTareaById(Integer idTarea) {
        return tareaRepository.findById(idTarea);
    }

    @Override
    public void saveTarea(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    @Override
    public void deleteTarea(Tarea tarea) {
        tareaRepository.delete(tarea);
    }
}
