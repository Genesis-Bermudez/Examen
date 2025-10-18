package cr.ac.una.utilities;

import com.sun.istack.NotNull;
/*import cr.ac.una.data_access_layer.ClienteFileStore;
import cr.ac.una.data_access_layer.VeterinarioFileStore; */
import cr.ac.una.data_access_layer.IFileStore;
/*import cr.ac.una.domain_layer.Cliente;
import cr.ac.una.domain_layer.Veterinario; */
import java.io.File;
public class FileManagement {
    static File baseDir = new File(System.getProperty("user.dir"));

    /*
    @NotNull public static IFileStore<Cliente> getClientesFileStore(String fileName) {
        File clientsXml = new File(baseDir, fileName);
        return new ClienteFileStore(clientsXml);
    }

    @NotNull
    public static IFileStore<Veterinario> getVeterinariosFileStore(String fileName) {
        File vetsXml = new File(baseDir, fileName);
        return new VeterinarioFileStore(vetsXml);
     }
     */
}

