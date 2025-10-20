package cr.ac.una.utilities;

import com.sun.istack.NotNull;
import cr.ac.una.data_access_layer.*;
import java.io.File;

public class FileManagement {
    static File baseDir = new File(System.getProperty("user.dir"));


    @NotNull public static IFileStore<Data> getDataFileStore(String fileName) {
        File dataXml = new File(baseDir, fileName);
        return new DataFileStore(dataXml);
    }
}

