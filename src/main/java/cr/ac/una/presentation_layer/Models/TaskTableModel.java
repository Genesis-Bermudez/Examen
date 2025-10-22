package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.Task;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {

    private final String[] cols = {"Código", "Descripción", "Fecha", "Prioridad", "Estado", "Responsable"};
    private List<Task> rows;

    // ---- Constructores ----

    public TaskTableModel() {
        this.rows = new ArrayList<>();
    }

    public TaskTableModel(List<Task> rows) {
        this.rows = rows != null ? rows : new ArrayList<>();
    }

    // ---- Gets ----

    @Override
    public int getRowCount() { return rows.size(); }
    @Override
    public int getColumnCount() { return cols.length; }
    @Override
    public String getColumnName(int c) { return cols[c]; }
    public Task getAt(int row) { return rows.get(row); }

    // ---- GetValueAt ----

    @Override
    public Object getValueAt(int r, int c) {
        Task t = rows.get(r);
        return switch (c) {
            case 0 -> t.getCode();
            case 1 -> t.getDescription();
            case 2 -> t.getFinishDate();
            case 3 -> t.getPriority();
            case 4 -> t.getStatus();
            case 5 -> t.getWorker() != null ? t.getWorker().getName() : "";
            default -> "";
        };
    }
}
