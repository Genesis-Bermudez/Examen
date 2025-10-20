package cr.ac.una.presentation_layer.Views;

import cr.ac.una.domain_layer.Task;

import cr.ac.una.presentation_layer.Controllers.TaskController;
import cr.ac.una.presentation_layer.Models.Model;


import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditTaskView extends JDialog implements PropertyChangeListener {
    private JPanel BasePanel;
    private JComboBox PrioritycomboBox1;
    private JComboBox StatuscomboBox2;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel ContentPanel;
    private JPanel BasePanel2;
    private JPanel SelectionPanel;
    private JPanel ButtonPanel;
    private JPanel PriorityPanel;
    private JPanel StatusPanel;
    private JLabel PriorityJLabel;
    private JLabel StatusJLabel;

    private TaskController taskController;
    private Model model;

    public EditTaskView() {
        setContentPane(ContentPanel);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);
        setTitle("Editar una tarea");
        setSize(720, 400);

        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        ContentPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        Task task = model.getTask();
        String priority = PrioritycomboBox1.getSelectedItem().toString();
        String status = StatuscomboBox2.getSelectedItem().toString();
        task.setPriority(priority);
        task.setStatus(status);
        /*taskController.reloadTasks();*/
        JOptionPane.showMessageDialog(ContentPanel,"Se ha editado la tarea","Exito",JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void setController(TaskController taskController) {
        this.taskController = taskController;
    }
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public JPanel getPanel(){
        return ContentPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.TASK:
                Task selectedTask = model.getTask();
                if (selectedTask == null) {
                    /*selectedProjectLabel.setText("Seleccione una tarea");*/
                }
                else{
                    /*selectedProjectLabel.setText(selectedTask.getDescription());*/
                    PrioritycomboBox1.setSelectedItem(selectedTask.getPriority());
                    StatuscomboBox2.setSelectedItem(selectedTask.getStatus());
                }
        }
    }
}
