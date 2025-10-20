package cr.ac.una.domain_layer;

public class Code {
    private static int projectCode = 0;
    private static int taskCode = 0;

    public static int nextProjectCode() { return projectCode++; }

    public static int nextTaskCode() { return taskCode++; }

    public static void setCountProjectCode(int value) { projectCode = value; }

    public static void setCountTaskCode(int value) { taskCode = value; }
}