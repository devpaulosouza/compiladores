
public enum LogLevel {
    ERROR(0), INFO(1), DEBUG(2);

    int level;

    private LogLevel(int level) {
        this.level = level;
    }
}
