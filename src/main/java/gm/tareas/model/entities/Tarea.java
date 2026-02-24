package gm.tareas.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarea;

    @Column
    private String nombreTarea;

    @Column
    private String responsable;

    @Column
    private String estadoTarea;

}

