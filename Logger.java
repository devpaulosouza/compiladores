class Logger {
    private static Logger instance;

    private LogLevel logLevel;

    private Logger() {
        this.logLevel = LogLevel.ERROR;
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void setLevel(LogLevel level) {
        this.logLevel = level;
    }

    public void debug(String tag, String message) {
        this.log(LogLevel.DEBUG, tag, message);
    }

    public void info(String tag, String message) {
        this.log(LogLevel.INFO, tag, message);
    }

    public void error(String tag, String message) {
        this.log(LogLevel.ERROR, tag, message);
    }


    // Erros de compilação
    public void invalidChar(int line) {
        System.out.printf("%d:caractere invalido.\n", line);
    }

    public void invalidLexeme(int line, String lexeme) {
        System.out.printf("%d:lexema nao identificado [%s].\n", line, lexeme);
    }

    public void unexpectedEOF(int line) {
        System.out.printf("%d:fim de arquivo nao experado.", line);
    }

    private void log(LogLevel level, String tag, String message) {
        if (this.shouldLog(level)) {
            System.out.printf("[%s] [%s]: %s\n", level, tag, message);
        }
    }

    /**
     * Compara dois LogLevel e retorna se deve ser exibido o log ou não
     * 
     * @param level level da função que solicitou o log
     * @return <code>true</code> se deve exibir o log ou <code>false</code> caso
     *         contrário
     */
    private boolean shouldLog(LogLevel logLevel) {
        return this.logLevel.level >= logLevel.level;
    }
}
