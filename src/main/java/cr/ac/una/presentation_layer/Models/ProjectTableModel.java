package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.Project;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProjectTableModel extends AbstractTableModel {

    private final String[] cols = {"Código", "Descripción", "Encargado"};
    private List<Project> rows;

    public ProjectTableModel(List<Project> data) {
        this.rows = data;
    }

    @Override public int getRowCount() { return rows == null ? 0 : rows.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int col) { return cols[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Project p = rows.get(row);
        return switch (col) {
            case 0 -> p.getCode();
            case 1 -> p.getDescription();
            case 2 -> p.getLeader() != null ? p.getLeader().getName() : "";
            default -> "";
        };
    }

    public Project getAt(int row) { return rows.get(row); }
}
