package gm.tareas.controllers;

import gm.tareas.model.entities.Tarea;
import gm.tareas.services.ITareaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ITareaService tareaService;

    @FXML
    private TableView<Tarea> tareasTabla;

    @FXML
    private TableColumn<Tarea, Integer> idColumn;

    @FXML
    private TableColumn<Tarea, String> tareaColumn;

    @FXML
    private TableColumn<Tarea, String> responsableColumn;

    @FXML
    private TableColumn<Tarea, String> estadoColumn;

    @FXML
    private ComboBox<String> estadoCBox;

    @FXML
    private TextField tareaText;

    @FXML
    private TextField responsableText;

    private ObservableList<Tarea> tareasList = FXCollections.observableArrayList();

    private Integer idTareaAux;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareasTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        configurarComboBox();
        listarTareas();
    }

    private void configurarColumnas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        tareaColumn.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumn.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estadoTarea"));
        tareasTabla.setRowFactory(tareaTableView -> new TableRow<Tarea>() {

            @Override
            protected void updateItem(Tarea tarea, boolean empty) {
                super.updateItem(tarea, empty);

                if (empty || tarea == null) {
                    setStyle("");
                } else if ("Pendiente".equals(tarea.getEstadoTarea())) {
                    setStyle("-fx-background-color: #CD5C5C");
                } else if ("En proceso".equals(tarea.getEstadoTarea())) {
                    setStyle("-fx-background-color: #F0E68C");
                } else if ("Finalizado".equals(tarea.getEstadoTarea())) {
                    setStyle("-fx-background-color: #90EE90");
                }
            }
        });
    }

    private void listarTareas() {
        log.info("Ejecutando listado de tareas");
        tareasList.clear();
        tareasList.addAll(tareaService.getTareas());
        tareasTabla.setItems(tareasList);
    }

    private void configurarComboBox() {
        estadoCBox.getItems().addAll("Pendiente", "En proceso", "Finalizado");
    }

    public void agregarTarea() {

        Tarea nuevaTarea = new Tarea();

        //Se verifica que el usuario haya elegido el estado de la tarea del comboBox
        if (estadoCBox.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Por favor seleccione el estado de la Tarea.");
            estadoCBox.requestFocus();
            return;
        } else if (tareaText.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe ingresar alguna Tarea");
            tareaText.requestFocus();
        }

        nuevaTarea.setNombreTarea(tareaText.getText());
        nuevaTarea.setResponsable(responsableText.getText());
        nuevaTarea.setEstadoTarea(estadoCBox.getSelectionModel().getSelectedItem());

        if (idTareaAux != null) {
            nuevaTarea.setIdTarea(idTareaAux);
            mostrarAlerta(Alert.AlertType.INFORMATION,"Se ha actualizado la Tarea correctamente");
        }else {
            mostrarAlerta(Alert.AlertType.INFORMATION,"Se ha agregado una nueva Tarea");
        }
        tareaService.saveTarea(nuevaTarea);
        listarTareas();
        limpiarFormulario();
    }

    public void eliminarTarea(){

        //Verificamos que el usuario haya seleccionado alguna tarea
        if (tareaService.getTareaById(idTareaAux).isEmpty()){
            mostrarAlerta(Alert.AlertType.WARNING,"Debe seleccionar una Tarea para eliminar");
            return;
        }else {
            Optional<Tarea> tareaEliminar = tareaService.getTareaById(idTareaAux);
            tareaService.deleteTarea(tareaEliminar.get());
            tareasList.remove(tareaEliminar.get());
            listarTareas();
            limpiarFormulario();
            mostrarAlerta(Alert.AlertType.INFORMATION,"Se ha eliminado una Tarea");
        }



    }

    public void cargarTareaFormulario() {
        Tarea tareaSeleccionada = tareasTabla.getSelectionModel().getSelectedItem();

        if (tareaSeleccionada != null) {
            idTareaAux = tareaSeleccionada.getIdTarea();
            tareaText.setText(tareaSeleccionada.getNombreTarea());
            responsableText.setText(tareaSeleccionada.getResponsable());
            estadoCBox.setValue(tareaSeleccionada.getEstadoTarea());
        }
    }

    private void mostrarAlerta(Alert.AlertType alertType, String mensaje) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void limpiarFormulario() {
        idTareaAux = null;
        tareaText.clear();
        responsableText.clear();
        estadoCBox.getSelectionModel().clearSelection();
        estadoCBox.setPromptText("Seleccionar");
    }


}
