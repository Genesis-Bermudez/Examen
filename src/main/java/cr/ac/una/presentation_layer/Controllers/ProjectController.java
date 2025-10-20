package cr.ac.una.presentation_layer.Controllers;

import cr.ac.una.domain_layer.Project;
import cr.ac.una.domain_layer.User;
import cr.ac.una.service_layer.DataService;
import java.util.List;

public class ProjectController {
    private final DataService dataService;

    public ProjectController(DataService dataService) {
            this.dataService = dataService;
    }

    public void addProject(String descripcion, User encargado){
            validarDescripcion(descripcion);
            validarEncargado(encargado);
            Project newProject = new Project(descripcion, encargado);
            dataService.add(newProject);
        }

        public List<Project> leerTodos() {
            return dataService.leerTodos();
        }

        // --- Validaciones ---
        private void validarDescripcion(String descripcion) {
            if (descripcion == null || descripcion.trim().isEmpty())
                throw new IllegalArgumentException("La descripcion es obligatorio.");
        }

        private void validarEncargado(User encargado) {
            if (encargado == null || encargado.getName().trim().isEmpty())
                throw new IllegalArgumentException("El encargado es obligatorio.");
        }
    }
