package cr.ac.una.domain_layer;

public class Code {
    private static int projectCode = 0;
    private static int taskCode = 0;

    public static int nextProjectCode() {
        int aux = projectCode;
        projectCode += 1;
        return aux;
    }

    public static int nextTaskCode() {
        int aux = taskCode;
        taskCode += 1;
        return aux;
    }

    public static void setCountProjectCode(int value) { projectCode = value; }

    public static void setCountTaskCode(int value) { taskCode = value; }
}