package cr.ac.una.presentation_layer.Views;

import com.github.lgooddatepicker.components.DatePicker;
import cr.ac.una.domain_layer.User;
import cr.ac.una.domain_layer.Project;
import cr.ac.una.domain_layer.Task;
import cr.ac.una.presentation_layer.Controllers.ProjectAndTaskController;
import cr.ac.una.presentation_layer.Models.Model;
import cr.ac.una.presentation_layer.Models.ProjectTableModel;
import cr.ac.una.presentation_layer.Models.TaskTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.util.List;

/*
* View para mostrar los proyectos y las tareas
*/

public class ProjectAndTaskManagementView {
    private JPanel ContentPanel;
    private JPanel PanelBase;
    private JPanel ProyectosPanel;
    private JPanel ProyectosPanel2;
    private JPanel ProyectosPanel3;
    private JPanel ProyectosPanel4;
    private JPanel EncargadoPanel;
    private JPanel EncargadoPanel2;
    private JPanel EncargadoLPanel;
    private JLabel EncargadoLabel;
    private JPanel EncargadoScrollPanel;
    private JComboBox<User> EncargadoComboBox1;
    private JPanel DescripcionPanel;
    private JPanel DescripcionPanel2;
    private JPanel DescripcionPanel3;
    private JPanel DescripcionTextFieldPanel;
    private JTextField DescripcionTextField1;
    private JPanel TablaDeProyectosPanel;
    private JTable TablaDeProyectos;
    private JPanel TareasPanel;
    private JPanel TareasPanel2;
    private JPanel SeleccionadoPanel;
    private JLabel SeleccionadoLabel;
    private JPanel TareasPanel3;
    private JTable TablaDeTareas;
    private JPanel PanelBasee;
    private JPanel CrearPanelProyectos;
    private JPanel CrearPanelProyectos2;
    private JPanel SpacePanel;
    private JPanel CrearButtonPanel;
    private JButton crearButton;
    private DatePicker finishDatePicker;
    private JTextField descripcionTareaTextField;
    private JComboBox<String> priorityCombo;
    private JComboBox<String> statusCombo;
    private JComboBox<User> responsableCombo;
    private JButton crearTareaButton;
    private JPanel DataTaskPanel;
    private JPanel DataTaskPanel2;
    private JPanel DescripcionTaskPanel;
    private JPanel VenceTareasPanel;
    private JPanel PriorityTaskPanel;
    private JPanel StatusTaskPanel;
    private JPanel WorkerTaskPanel;
    private JPanel CrearTaskButton;
    private JScrollPane JScrollPaneProjects;
    private JScrollPane JScrollPaneTask;

    private final ProjectAndTaskController controller;
    private final Model model; private EditTaskView editTaskView;

    // ---- Constructor ----

    public ProjectAndTaskManagementView(ProjectAndTaskController controller, Model model) {
        this.controller = controller;
        this.model = model;
        this.editTaskView = new EditTaskView();
        initialize();
    }

    // ---- Inicializador ----

    private void initialize() {
        priorityCombo.addItem("Alta");
        priorityCombo.addItem("Media");
        priorityCombo.addItem("Baja");

        statusCombo.addItem("Abierta");
        statusCombo.addItem("En-progreso");
        statusCombo.addItem("En-revisión");
        statusCombo.addItem("Resuelta");

        EncargadoComboBox1.removeAllItems();
        responsableCombo.removeAllItems();
        List<User> users = controller.getUsers();
        for (User u : users) {
            EncargadoComboBox1.addItem(u);
            responsableCombo.addItem(u);
        }

        TablaDeProyectos.setModel(new ProjectTableModel(controller.getProjects()));
        TablaDeTareas.setModel(new TaskTableModel());

        DataTaskPanel.setVisible(false);

        // ---- Listeners ----
        crearButton.addActionListener(e -> onAgregarProyecto());
        crearTareaButton.addActionListener(e -> onAgregarTarea());

        TablaDeProyectos.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                int fila = TablaDeProyectos.getSelectedRow();
                if (fila >= 0) {
                    Project p = controller.getProjects().get(fila);

                    controller.reloadTasksForProject(p);

                    Project actualizado = controller.getSelectedProject();
                    SeleccionadoLabel.setText(actualizado.getDescription());

                    if (actualizado.getTasks() != null && !actualizado.getTasks().isEmpty()) {
                        TablaDeTareas.setModel(new TaskTableModel(actualizado.getTasks()));
                        DataTaskPanel.setVisible(true);
                    } else {
                        TablaDeTareas.setModel(new TaskTableModel());
                        DataTaskPanel.setVisible(true);
                    }
                }
            }
        });

        TablaDeTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = TablaDeTareas.getSelectedRow();
                    Project p = controller.getSelectedProject();
                    if (p != null && fila >= 0 && fila < p.getTasks().size()) {
                        Task t = p.getTasks().get(fila);
                        model.setTask(t);
                        editTaskView.setModel(model);
                        editTaskView.setController(controller);
                        editTaskView.setLocationRelativeTo(ContentPanel);
                        editTaskView.setVisible(true);
                        controller.reloadTasksForProject(p);
                        TablaDeTareas.setModel(new TaskTableModel(p.getTasks()));
                    }
                }
            }
        });
    }

    // ---- Agregar Proyectos ----

    private void onAgregarProyecto() {
        String descripcion = DescripcionTextField1.getText().trim();
        User encargado = (User) EncargadoComboBox1.getSelectedItem();

        if (descripcion.isEmpty() || encargado == null) {
            JOptionPane.showMessageDialog(ContentPanel, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.addProject(descripcion, encargado);
        TablaDeProyectos.setModel(new ProjectTableModel(controller.getProjects()));
        JOptionPane.showMessageDialog(ContentPanel, "Proyecto agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        DescripcionTextField1.setText("");
    }

    // ---- Agregar Tareas ----

    private void onAgregarTarea() {
        Project proyectoSeleccionado = controller.getSelectedProject();
        if (proyectoSeleccionado == null || proyectoSeleccionado.getCode().equals("-1")) {
            JOptionPane.showMessageDialog(ContentPanel, "Debe seleccionar un proyecto antes de crear una tarea", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String descripcion = descripcionTareaTextField.getText().trim();
        String fecha = finishDatePicker.getDate() != null ? finishDatePicker.getDate().toString() : "";
        String prioridad = (String) priorityCombo.getSelectedItem();
        String estado = (String) statusCombo.getSelectedItem();
        User responsable = (User) responsableCombo.getSelectedItem();

        if (descripcion.isEmpty() || fecha.isEmpty() || prioridad == null || estado == null || responsable == null) {
            JOptionPane.showMessageDialog(ContentPanel, "Debe completar todos los campos de la tarea", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.addTask(proyectoSeleccionado, descripcion, fecha, prioridad, estado, responsable);
        TablaDeTareas.setModel(new TaskTableModel(proyectoSeleccionado.getTasks()));
        JOptionPane.showMessageDialog(ContentPanel, "Tarea agregada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        descripcionTareaTextField.setText("");
        priorityCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        responsableCombo.setSelectedIndex(0);
    }

    // ---- Get ----

    public JPanel getContentPanel() { return ContentPanel; }
}


