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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
    private JPanel CrearPanel;
    private JPanel CrearPanel2;
    private JPanel SpacePanel;
    private JPanel CrearButtonPanel;
    private JButton crearButton;
    private DatePicker finishDatePicker;

    private JTextField descripcionTareaTextField;
    private JComboBox<String> priorityCombo;
    private JComboBox<String> statusCombo;
    private JComboBox<User> responsableCombo;
    private JButton crearTareaButton;
    // -------------------------------------------------------

    private final ProjectAndTaskController controller;
    private final Model model;
    private EditTaskView editTaskView;

    public ProjectAndTaskManagementView(ProjectAndTaskController controller, Model model) {
        this.controller = controller;
        this.model = model;
        this.editTaskView = new EditTaskView();
        this.editTaskView.setController(controller);
        this.editTaskView.setModel(model);

        initialize();
    }

    public JPanel getContentPanel() {
        return ContentPanel;
    }

    private void initialize() {
        if (descripcionTareaTextField == null) descripcionTareaTextField = new JTextField(18);
        if (priorityCombo == null) priorityCombo = new JComboBox<>();
        if (statusCombo == null) statusCombo = new JComboBox<>();
        if (responsableCombo == null) responsableCombo = new JComboBox<>();
        if (crearTareaButton == null) crearTareaButton = new JButton("Crear Tarea");


        if (CrearPanel != null && CrearPanel.getComponentCount() == 0) {
            CrearPanel.add(new JLabel("Descripción:"));
            CrearPanel.add(descripcionTareaTextField);
            CrearPanel.add(new JLabel("Fecha fin:"));
            if (finishDatePicker == null) {
                finishDatePicker = new DatePicker();
            }
            CrearPanel.add(finishDatePicker);
            CrearPanel.add(new JLabel("Prioridad:"));
            CrearPanel.add(priorityCombo);
            CrearPanel.add(new JLabel("Estado:"));
            CrearPanel.add(statusCombo);
            CrearPanel.add(new JLabel("Responsable:"));
            CrearPanel.add(responsableCombo);
            CrearPanel.add(crearTareaButton);
        }

        List<User> usuarios = controller.getUsers();
        EncargadoComboBox1.removeAllItems();
        EncargadoComboBox1.addItem(null);
        for (User u : usuarios) {
            EncargadoComboBox1.addItem(u);
        }

        priorityCombo.removeAllItems();
        priorityCombo.addItem(null);
        priorityCombo.addItem("Alta");
        priorityCombo.addItem("Media");
        priorityCombo.addItem("Baja");

        statusCombo.removeAllItems();
        statusCombo.addItem(null);
        statusCombo.addItem("Abierta");
        statusCombo.addItem("En-progreso");
        statusCombo.addItem("En-revisión");
        statusCombo.addItem("Resuelta");

        responsableCombo.removeAllItems();
        responsableCombo.addItem(null);
        for (User u : usuarios) responsableCombo.addItem(u);

        List<Project> proyectosInit = controller.getProjects() != null ? controller.getProjects() : new ArrayList<>();
        TablaDeProyectos.setModel(new ProjectTableModel(proyectosInit));

        TablaDeTareas.setModel(new TaskTableModel(new java.util.ArrayList<>()));

        if (CrearPanel != null) CrearPanel.setVisible(false);

        SeleccionadoLabel.setText("No hay un proyecto seleccionado");

        crearButton.addActionListener(e -> onAgregarProyecto());

        TablaDeProyectos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = TablaDeProyectos.getSelectedRow();
                if (viewRow == -1) {
                    controller.selectProject(null);
                    actualizarPanelTareas(null);
                    return;
                }

                int modelRow = viewRow;
                if (TablaDeProyectos.getRowSorter() != null) {
                    modelRow = TablaDeProyectos.convertRowIndexToModel(viewRow);
                }

                List<Project> proyectos = controller.getProjects();
                if (proyectos == null || modelRow < 0 || modelRow >= proyectos.size()) {
                    controller.selectProject(null);
                    actualizarPanelTareas(null);
                    return;
                }

                Project p = proyectos.get(modelRow);

                controller.selectProject(p);

                actualizarPanelTareas(p);

                SwingUtilities.invokeLater(() -> {
                    TareasPanel.scrollRectToVisible(TareasPanel.getBounds());
                });
            }
        });


        TablaDeTareas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = TablaDeTareas.getSelectedRow();
                    Project p = model.getProject();
                    if (p != null && p.getTasks() != null && fila >= 0 && fila < p.getTasks().size()) {
                        Task t = p.getTasks().get(fila);
                        model.setTask(t);
                        editTaskView.setLocationRelativeTo(ContentPanel);
                        editTaskView.setVisible(true);
                        controller.reloadTasksForProject(p);
                        Project reloaded = controller.getSelectedProject();
                        TablaDeTareas.setModel(new TaskTableModel(reloaded != null && reloaded.getTasks() != null ? reloaded.getTasks() : new ArrayList<>()));
                    }
                }
            }
        });

        crearTareaButton.addActionListener(e -> onAgregarTarea());

        TablaDeProyectos.setModel(new ProjectTableModel(controller.getProjects() != null ? controller.getProjects() : new ArrayList<>()));
    }

    private void actualizarPanelTareas(Project p) {
        if (p == null || p.getCode().equals("-1")) {
            if (CrearPanel != null) CrearPanel.setVisible(false);
            SeleccionadoLabel.setText("No hay un proyecto seleccionado");
            TablaDeTareas.setModel(new TaskTableModel(new java.util.ArrayList<>()));
        } else {
            if (CrearPanel != null) CrearPanel.setVisible(true);
            SeleccionadoLabel.setText("Proyecto: " + p.getDescription());
            TablaDeTareas.setModel(new TaskTableModel(p.getTasks() != null ? p.getTasks() : new java.util.ArrayList<>()));

            responsableCombo.removeAllItems();
            responsableCombo.addItem(null);
            for (User u : controller.getUsers()) responsableCombo.addItem(u);

            descripcionTareaTextField.setText("");
            if (finishDatePicker != null) finishDatePicker.clear();
            priorityCombo.setSelectedIndex(0);
            statusCombo.setSelectedIndex(0);
            responsableCombo.setSelectedIndex(0);
        }
    }

    private void onAgregarProyecto() {
        try {
            String descripcion = DescripcionTextField1.getText().trim();
            User encargado = (User) EncargadoComboBox1.getSelectedItem();

            if (descripcion.isEmpty() || encargado == null) {
                JOptionPane.showMessageDialog(ContentPanel, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.addProject(descripcion, encargado);
            JOptionPane.showMessageDialog(ContentPanel, "Proyecto agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            DescripcionTextField1.setText("");
            TablaDeProyectos.setModel(new ProjectTableModel(controller.getProjects()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ContentPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAgregarTarea() {
        try {
            Project p = model.getProject();
            if (p == null || p.getCode().equals("-1")) {
                JOptionPane.showMessageDialog(ContentPanel, "No hay proyecto seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String desc = descripcionTareaTextField.getText().trim();
            String fecha = finishDatePicker != null && finishDatePicker.getDate() != null ? finishDatePicker.getDate().toString() : "";
            String priority = (String) priorityCombo.getSelectedItem();
            String status = (String) statusCombo.getSelectedItem();
            User resp = (User) responsableCombo.getSelectedItem();

            if (desc.isEmpty() || fecha.isEmpty() || priority == null || status == null || resp == null) {
                JOptionPane.showMessageDialog(ContentPanel, "Complete todos los campos de la tarea", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.addTask(p, desc, fecha, priority, status, resp);

            controller.reloadTasksForProject(p);
            Project updated = controller.getSelectedProject();
            if (updated != null) {
                TablaDeTareas.setModel(new TaskTableModel(updated.getTasks() != null ? updated.getTasks() : new ArrayList<>()));
            }

            descripcionTareaTextField.setText("");
            if (finishDatePicker != null) finishDatePicker.clear();
            priorityCombo.setSelectedIndex(0);
            statusCombo.setSelectedIndex(0);
            responsableCombo.setSelectedIndex(0);

            JOptionPane.showMessageDialog(ContentPanel, "Tarea agregada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ContentPanel, "Error creando tarea: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
