package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.User;
import cr.ac.una.service_layer.IServiceObserver;
import cr.ac.una.utilities.ChangeType;

import javax.swing.table.AbstractTableModel;

public class Form extends AbstractTableModel implements IServiceObserver<User> {
    @Override
    public void onDataChanged(ChangeType type, User entity) {

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