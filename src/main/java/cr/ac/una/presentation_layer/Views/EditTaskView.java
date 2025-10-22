package cr.ac.una.presentation_layer.Views;

import cr.ac.una.domain_layer.Task;
import cr.ac.una.presentation_layer.Controllers.ProjectAndTaskController;
import cr.ac.una.presentation_layer.Models.Model;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/*
* Vista para editar la tarea seleccionada
*/

public class EditTaskView extends JDialog implements PropertyChangeListener {
    private JPanel ContentPanel;
    private JPanel BasePanel;
    private JComboBox<String> PrioritycomboBox1;
    private JComboBox<String> StatuscomboBox2;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel BasePanel2;
    private JPanel SelectionPanel;
    private JPanel ButtonPanel;
    private JPanel PriorityPanel;
    private JPanel StatusPanel;
    private JLabel PriorityJLabel;
    private JLabel StatusJLabel;

    private ProjectAndTaskController controller;
    private Model model;

    // ---- Constructor ----

    public EditTaskView() {
        setContentPane(ContentPanel);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);
        setSize(350, 150);
        setLocationRelativeTo(null);

        OKButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { onCancel(); }
        });

        ContentPanel.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        inicializarCombos();
    }

    // ---- Inicializador de los Combos ----

    private void inicializarCombos() {
        PrioritycomboBox1.removeAllItems();
        PrioritycomboBox1.addItem("Alta");
        PrioritycomboBox1.addItem("Media");
        PrioritycomboBox1.addItem("Baja");

        StatuscomboBox2.removeAllItems();
        StatuscomboBox2.addItem("Abierta");
        StatuscomboBox2.addItem("En-progreso");
        StatuscomboBox2.addItem("En-revisión");
        StatuscomboBox2.addItem("Resuelta");
    }

    // ---- Botón Aceptar ----

    private void onOK() {
        if (controller == null || model == null) {
            JOptionPane.showMessageDialog(this, "Error interno", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Task task = model.getTask();
        if (task == null) {
            JOptionPane.showMessageDialog(this, "No hay tarea seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String priority = (String) PrioritycomboBox1.getSelectedItem();
        String status = (String) StatuscomboBox2.getSelectedItem();

        if (priority == null || status == null) {
            JOptionPane.showMessageDialog(this, "Seleccione prioridad y estado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.updateTask(model.getProject(), task, priority, status);
        controller.reloadTasksForProject(model.getProject());
        model.setTasks(model.getProject().getTasks());

        JOptionPane.showMessageDialog(this, "Se ha editado la tarea", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    // ---- Botón Cancelar ----

    private void onCancel() {
        dispose();
    }

    // ---- Sets ----

    public void setController(ProjectAndTaskController controller) { this.controller = controller; }
    public void setModel(Model model) {
        if (this.model != null) {
            try { this.model.removePropertyChangeListener(this); } catch (Exception ignored) {}
        }
        this.model = model;
        if (this.model != null) this.model.addPropertyChangeListener(this);
        Task t = model != null ? model.getTask() : null;
        if (t != null) {
            PrioritycomboBox1.setSelectedItem(t.getPriority());
            StatuscomboBox2.setSelectedItem(t.getStatus());
        }
    }

    // ---- PropertyChange ----

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (model == null) return;
        if (Model.TASK.equals(evt.getPropertyName())) {
            Task t = model.getTask();
            if (t != null) {
                PrioritycomboBox1.setSelectedItem(t.getPriority());
                StatuscomboBox2.setSelectedItem(t.getStatus());
                setTitle("Editando: " + t.getCode() + " - " + (t.getDescription() == null ? "" : t.getDescription()));
            } else {
                PrioritycomboBox1.setSelectedIndex(-1);
                StatuscomboBox2.setSelectedIndex(-1);
            }
        }
    }
}