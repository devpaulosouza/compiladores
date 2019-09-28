class Compiler {
    private static final String tag = "Compiler";
    private static Logger logger = Logger.getInstance();

    private String filename;
    private LexicalAnalyzer lexicalAnalyzer;
    private SymbolTable table;

    public Compiler(String filename) throws CompilerException {
        this.filename = filename;
        this.table = new SymbolTable();
        this.lexicalAnalyzer = new LexicalAnalyzer(filename, this.table);

        lexicalAnalyzer.readToken();
    }
    
    public void S() throws CompilerException {
        logger.info(tag, "S()");

        B();
    }
    

    public void B() {
        logger.info(tag, "B()");
    }
 }