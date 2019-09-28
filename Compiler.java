class Compiler {
    private static final String tag = "Compiler";
    private static Logger logger = Logger.getInstance();

    private String filename;
    private LexicalAnalyzer lexicalAnalyzer;
    private SymbolTable table;

    public Compiler(String filename) {
        this.filename = filename;
        this.lexicalAnalyzer = new LexicalAnalyzer(filename);

        lexicalAnalyzer.readToken();
        table = new SymbolTable();
    }
    
    public void S() throws CompilerException {
        logger.info(tag, "S()");

        B();
    }
    

    public void B() {
        logger.info(tag, "B()");
    }
 }