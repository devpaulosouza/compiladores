class Compiler {
    private static final String tag = "Compiler";
    private static Logger logger = Logger.getInstance();

    private LexicalAnalyzer lexicalAnalyzer;
    private SymbolTable table;

    public Compiler() {
        this.lexicalAnalyzer = new LexicalAnalyzer();
        table = new SymbolTable();
        table.show();
    }
    
    public void S() throws CompilerException {
        logger.info(tag, "S()");

        B();
    }
    

    public void B() {
        logger.info(tag, "B()");
    }
 }