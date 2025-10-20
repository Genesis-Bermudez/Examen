package cr.ac.una.data_access_layer;

import cr.ac.una.domain_layer.Project;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFileStore implements IFileStore<Data>{

    private final File xmlFile;

    public DataFileStore(File xmlFile) {
        this.xmlFile = xmlFile;
        ensureFile();
    }

    @Override
    public List<Data> readAll() {
        List<Data> out = new ArrayList<>();
        if (xmlFile.length() == 0) return Collections.singletonList(Data.getInstance());;

        try {
            JAXBContext ctx = JAXBContext.newInstance(Data.class);
            Unmarshaller u = ctx.createUnmarshaller();
            FileInputStream is = new FileInputStream(xmlFile);
            Data result = (Data) u.unmarshal(is);
            is.close();
            return Collections.singletonList(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.singletonList(Data.getInstance());
        }
    }

    @Override
    public void writeAll(List<Data> dataList) {
        if (dataList == null || dataList.isEmpty()) return;

        try {
            JAXBContext ctx = JAXBContext.newInstance(Data.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            FileOutputStream os = new FileOutputStream(xmlFile);
            m.marshal(dataList.get(0), os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ensureFile() {
        try {
            File parent = xmlFile.getParentFile();
            if (parent != null) parent.mkdirs();

            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
                writeAll(Collections.singletonList(Data.getInstance()));
            }
        } catch (Exception ignored) {}
    }
}
