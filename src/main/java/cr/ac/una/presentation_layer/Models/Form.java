package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.ClaseBase;
import cr.ac.una.service_layer.IServiceObserver;
import cr.ac.una.utilities.ChangeType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class Form extends AbstractTableModel implements IServiceObserver<ClaseBase> {
    @Override
    public void onDataChanged(ChangeType type, ClaseBase entity) {

    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}