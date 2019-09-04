class Compiler {
    private static final String tag = "Compiler";
    private static Logger logger = Logger.getInstance();

    private LexicalAnalyzer lexicalAnalyzer;

    public Compiler() {
        this.lexicalAnalyzer = new LexicalAnalyzer();
    }
    
    public void S() throws CompilerException {
        logger.info(tag, "S()");
    }
    
}