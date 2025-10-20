package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.Project;
import cr.ac.una.service_layer.IServiceObserver;
import cr.ac.una.utilities.ChangeType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProjectTableModel extends AbstractTableModel implements IServiceObserver<Project> {
    private final String[] cols = {"Código", "Descripción", "Encargado"};
    private final Class<?>[] types = { String.class, String.class, String.class};
    private final List<Project> rows = new ArrayList<>();

    public void setRows(List<Project> data) {
        rows.clear();
        if (data != null) rows.addAll(data);
        fireTableDataChanged();
    }

    public Project getAt(int row) { return (row >= 0 && row < rows.size()) ? rows.get(row) : null; }

    // ----- AbstractTableModel -----
    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column){ return cols[column]; }
    @Override public Class<?> getColumnClass(int columnIndex) { return types[columnIndex];}
    @Override public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Project project = rows.get(rowIndex);
        switch (columnIndex) {
            case 0: return project.getCode();
            case 1: return project.getDescription();
            case 2: return project.getLeader();
            default: return null;
        }
    }

    // Observer
    @Override
    public void onDataChanged(ChangeType type, Project project) {
        switch (type) {
            case CREATED: {
                rows.add(project);
                int i = rows.size() - 1;
                fireTableRowsInserted(i, i);
                break;
            }

            case UPDATED: {
                int i = indexOf(project.getCode());
                if (i >= 0) {
                    rows.set(i, project);
                    fireTableRowsUpdated(i, i);
                }
                break;
            }

            case DELETED: {
                int i = indexOf(project.getCode());
                if (i >= 0) {
                    rows.remove(i);
                    fireTableRowsDeleted(i, i);
                }
                break;
            }
        }
    }

    private int indexOf(String codigo) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getCode().equals(codigo)) return i;
        }
        return -1;
    }
}
