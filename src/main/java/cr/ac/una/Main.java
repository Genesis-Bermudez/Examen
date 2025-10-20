package cr.ac.una;

import cr.ac.una.presentation_layer.Controllers.ProjectController;
import cr.ac.una.presentation_layer.Models.ProjectTableModel;
import cr.ac.una.presentation_layer.Views.MainWindow;
import cr.ac.una.presentation_layer.Views.ProjectAndTaskManagementView;

import cr.ac.una.service_layer.DataService;
import cr.ac.una.utilities.FileManagement;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;


public class Main {
    public static void main(String[] args) {


        // Configurar ProjectController
        DataService proyectoService = new DataService(FileManagement.getDataFileStore("data.xml"));
        ProjectController controller = new ProjectController(proyectoService);
        ProjectTableModel tableModel = new ProjectTableModel();
        ProjectAndTaskManagementView view = new ProjectAndTaskManagementView(controller, tableModel, controller.leerTodos());
        proyectoService.addObserver(tableModel);

        Dictionary<String, JPanel> tabs = new Hashtable<>();
        tabs.put("Proyectos", view.getContentPanel());

        MainWindow window = new MainWindow();
        window.AddTabs(tabs);
        window.setVisible(true);
    }
    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);

}