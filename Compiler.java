class Compiler {
    private static final String tag = "Compiler";
    private static Logger logger = Logger.getInstance();

    private String filename;
    private LexicalAnalyzer lexicalAnalyzer;
    private SymbolTable table;

    private Symbol symbol;

    public Compiler(String filename) throws CompilerException {
        this.filename = filename;
        this.table = new SymbolTable();
        this.lexicalAnalyzer = new LexicalAnalyzer(filename, this.table);

        symbol = lexicalAnalyzer.readToken();
    }

    /**
     * casaToken
     * 
     * verifica se o token atual é o token experado
     * @return true se for o token experado ou false caso contrário
     * 
     * @throws CompilerException caso não for o token experado
     */
    public boolean match(Token token) throws CompilerException {
        if(symbol != null && symbol.getToken().equals(token)){
            symbol = lexicalAnalyzer.readToken();

            return true;
        } else {
            logger.invalidLexeme(lexicalAnalyzer.getLine(), symbol.getLexeme());
            throw new CompilerException();
        }
    }

    /**
     * verifica se o token atual A está contido em uma lista de tokens B
     * @param tokens lista de tokens B
     * @return true caso A esteja contido em B
     */
    public boolean tokenIn(Token ...tokens) {
        for (Token token : tokens) {
            if (symbol.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }

    public boolean tokenEqualTo(Token token) {
        return symbol.getToken().equals(token);
    }
    
    /**
     * Bloco principal
     * S -> { D }* main { B }* end
     * @throws CompilerException
     */
    public void S() throws CompilerException {
        logger.info(tag, "S()");

        while (tokenIn(Token.CONST, Token.TYPE)) {
            D();
        }

        match(Token.MAIN);
        
        while (tokenIn(Token.WHILE, Token.IDENTIFIER, Token.IF, Token.SEMICOLON, Token.WRITE, Token.READLN)) {
            B();
        }
        match(Token.END);
    }
    

    private void D() throws CompilerException {
        if (tokenEqualTo(Token.CONST)) {
            match(Token.CONST);
        } else if (tokenEqualTo(Token.TYPE)) {
            match(Token.TYPE);
            match(Token.IDENTIFIER);
            // = EXP;
            if (tokenEqualTo(Token.ATTR)) {
                match(Token.ATTR);
                EXPR();
                match(Token.SEMICOLON);
            }
        }
        logger.info(tag, "D()");
    }

    private void B() {
        logger.info(tag, "D()");
    }

    private void EXPR() throws CompilerException {
        logger.info(tag, "EXPR()");
        match(Token.CONST_VALUE);
    }
 }