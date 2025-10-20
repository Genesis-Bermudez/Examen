package cr.ac.una.presentation_layer.Views;

import javax.swing.*;
import java.util.Dictionary;
import java.util.Enumeration;

public class MainWindow extends JFrame {
    private JPanel ContentPanel;
    private JTabbedPane MainTabPanel;

    public MainWindow() {
        setTitle("Sistema de Gestión de Proyectos");
        setContentPane(MainTabPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);
    }

    public void AddTabs(Dictionary<String, JPanel> tabs) {
        Enumeration<String> keys = tabs.keys();
        while (keys.hasMoreElements()) {
            String titulo = keys.nextElement();
            JPanel contenido = tabs.get(titulo);
            MainTabPanel.addTab(titulo, contenido);
        }
    }

    public void EditarTareaView() {
        //setTitle("Editando");
        setContentPane(ContentPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}
