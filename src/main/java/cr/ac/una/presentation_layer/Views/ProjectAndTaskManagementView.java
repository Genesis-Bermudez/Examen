package cr.ac.una.presentation_layer.Views;

import com.github.lgooddatepicker.components.DatePicker;
import cr.ac.una.domain_layer.Project;
import cr.ac.una.domain_layer.User;
import cr.ac.una.presentation_layer.Controllers.ProjectController;
import cr.ac.una.presentation_layer.Controllers.TaskController;
import cr.ac.una.presentation_layer.Models.ProjectTableModel;
import cr.ac.una.presentation_layer.Models.TaskTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
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
    private JComboBox EncargadoComboBox1;
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

    private EditTaskView editTaskView;
    private ProjectController proyectoController;
    private ProjectTableModel proyectoTableModel;

    private TaskController tareaController;
    private TaskTableModel tareaTableModel;

    public ProjectAndTaskManagementView(ProjectController proyectoController, ProjectTableModel proyectoTableModel, List<Project> datos) {
        editTaskView = new EditTaskView();
        createTaskPanel.setVisible(false);
        this.proyectoController = proyectoController;
        this.proyectoTableModel = proyectoTableModel;
        bind(datos);
        addListeners();
        
    }

    private void bind(List<Project> datos) {
        TablaDeProyectos.setModel(proyectoTableModel);
        proyectoTableModel.setRows(datos);
    }

    public JPanel getContentPanel() { return ContentPanel; }

    private void addListeners() {
        crearButton.addActionListener(e -> onAddProject());
        TablaDeProyectos.getSelectionModel().addListSelectionListener(this::onTableSelection);
    }

    private void onTableSelection(ListSelectionEvent listSelectionEvent) {
    }

    private void onAddProject() {
        try {
            String desc = DescripcionTextField1.getText().trim();
            User enc = (User) EncargadoComboBox1.getSelectedItem();
            proyectoController.agregar(desc, enc);
            clearProyectoForm();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void clearProyectoForm() {
        DescripcionTextField1.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(ContentPanel, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
