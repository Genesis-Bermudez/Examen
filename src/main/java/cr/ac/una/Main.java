package cr.ac.una;

import cr.ac.una.presentation_layer.Controllers.ProjectAndTaskController;
import cr.ac.una.presentation_layer.Models.Model;
import cr.ac.una.presentation_layer.Views.ProjectAndTaskManagementView;
import cr.ac.una.service_layer.DataService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                DataService service = DataService.getInstance();
                Model model = new Model();
                ProjectAndTaskController controller = new ProjectAndTaskController(service, model);

                ProjectAndTaskManagementView view = new ProjectAndTaskManagementView(controller, model);

                JFrame frame = new JFrame("Gestión de Proyectos y Tareas");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(view.getContentPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setSize(780, 600);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al iniciar: " + e.getMessage());
            }
        });
    }
}
