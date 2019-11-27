class Compiler {
    private static final String tag = "Compiler";
    private static Logger logger = Logger.getInstance();

    private LexicalAnalyzer lexicalAnalyzer;
    private SymbolTable table;

    private Symbol symbol;

    public Compiler(String filename) throws CompilerException {
        this.table = new SymbolTable();
        this.lexicalAnalyzer = new LexicalAnalyzer(filename, this.table);

        symbol = lexicalAnalyzer.readToken();
    }

    /**
     * casaToken
     * verifica se o token atual é o token experado
     * 
     * @return true se for o token experado ou false caso contrário
     * 
     * @throws CompilerException caso não for o token experado
     */
    private boolean match(Token token) throws CompilerException {
        if(symbol != null && symbol.getToken().equals(token)){

            // se o token for end, aceita o fim de arquivo
            boolean acceptEof = tokenEqualTo(Token.END);

            symbol = lexicalAnalyzer.readToken();

            if (!acceptEof && symbol == null) {
                logger.unexpectedEOF(lexicalAnalyzer.getLine());
                throw new CompilerException();
            }

            return true;
        } else {
            if (symbol == null) {
                logger.unexpectedEOF(lexicalAnalyzer.getLine());
                throw new CompilerException();
            } else {
                logger.unexpectedToken(lexicalAnalyzer.getLine(), symbol.getLexeme());
                throw new CompilerException();
            }
        }
    }

    /**
     * verifica se o token atual A está contido em uma lista de tokens B
     * 
     * @param tokens lista de tokens B
     * 
     * @return true caso A esteja contido em B
     */
    private boolean tokenIn(Token ...tokens) {
        for (Token token : tokens) {
            if (symbol != null && symbol.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }

    private boolean tokenEqualTo(Token token) {
        return symbol.getToken().equals(token);
    }
    
    /**
     * Bloco principal
     * S -> { D }* main { B }* end
     * 
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
    

    /**
     * D -> CONST ID = EXP;
     *      | TYPE ID [ = EXP ] { ,ID [ = EXP]  }*; 
     * 
     * @throws CompilerException
     */
    private void D() throws CompilerException {
        logger.info(tag, "D()");

        Symbol dAux = new Symbol();
        Symbol idAux;
        Symbol constAux;
        boolean signal = false;

        if (tokenEqualTo(Token.CONST)) {
            dAux.setClazz(Symbol.Clazz.CONST); // (6)
            match(Token.CONST);
            constAux = symbol;
            match(Token.IDENTIFIER);

            { // (7)
                if (constAux.getClazz() == null) {
                    constAux.setClazz(dAux.getClazz());
                } else {
                    logger.alreadyDeclared(lexicalAnalyzer.getLine(), constAux.getLexeme());
                    throw new CompilerException();
                }
            }

            match(Token.ATTR);

            if (tokenEqualTo(Token.MINUS)) {
                signal = true; // (8)
                match(Token.MINUS);
            }

            constAux = symbol;
            constAux.setClazz(dAux.getClazz());

            match(Token.CONST_VALUE);
            
            if (signal) {
                if (!constAux.getType().equals(Symbol.Type.CONST_INT)) {
                    logger.incompatibleTypes(lexicalAnalyzer.getLine(), constAux.getLexeme());
                    throw new CompilerException();
                } 
            }

            match(Token.SEMICOLON);
        } else if (tokenEqualTo(Token.TYPE)) {
            
            { // (1) (2) (3) (4)
                if (symbol.getLexeme().equals("integer")) {  // (2)
                    dAux.setType(Symbol.Type.CONST_INT);
                } else if (symbol.getLexeme().equals("boolean")) {  // (4)
                    dAux.setType(Symbol.Type.CONST_BOOL);
                } else if (symbol.getLexeme().equals("byte")) { // 3
                    dAux.setType(Symbol.Type.CONST_BYTE);
                } else if (symbol.getLexeme().equals("string")) { // (1)
                    dAux.setType(Symbol.Type.CONST_STRING);
                }
            }
            dAux.setClazz(Symbol.Clazz.VAR);

            match(Token.TYPE);

            idAux = symbol;
            
            match(Token.IDENTIFIER);
            

            { // (7)
                if (idAux.getClazz() == null) {
                    idAux.setClazz(dAux.getClazz());
                } else {
                    logger.alreadyDeclared(lexicalAnalyzer.getLine(), idAux.getLexeme());
                    throw new CompilerException();
                }
            }

            // = EXP;
            if (tokenEqualTo(Token.ATTR)) {
                match(Token.ATTR);

                if (tokenEqualTo(Token.MINUS)) {
                    signal = true; // (8)
                    match(Token.MINUS);
                }

                idAux = symbol;
                match(Token.CONST_VALUE);

                if (signal) {
                    if (!idAux.getType().equals(Symbol.Type.CONST_INT)) {
                        logger.incompatibleTypes(lexicalAnalyzer.getLine(), idAux.getLexeme());
                        throw new CompilerException();
                    }
                }

                { // (10)
                    if (!dAux.getType().equals(idAux.getType()) &&
                        !(dAux.getType().equals(Symbol.Type.CONST_BYTE) && idAux.getType().equals(Symbol.Type.CONST_INT) && Integer.parseInt(idAux.getLexeme()) >= 0 && Integer.parseInt(idAux.getLexeme()) <= 255)) {
                        logger.incompatibleTypes(lexicalAnalyzer.getLine(), idAux.getLexeme());
                        throw new CompilerException();
                    }
                }

                // EXP();
            }

            while (tokenEqualTo(Token.COMMA)) {
                match(Token.COMMA);

                idAux = symbol;

                match(Token.IDENTIFIER);

                { // (7)
                    if (idAux.getClazz() == null) {
                        idAux.setClazz(dAux.getClazz());
                    } else {
                        logger.alreadyDeclared(lexicalAnalyzer.getLine(), idAux.getLexeme());
                        throw new CompilerException();
                    }
                }

                // = EXP
                if (tokenEqualTo(Token.ATTR)) {
                    match(Token.ATTR);


                    if (tokenEqualTo(Token.MINUS)) {
                        signal = true; // (8)
                        match(Token.MINUS);
                    }

                    
                    idAux = symbol;
                    match(Token.CONST_VALUE);

                    if (signal) {
                        if (!idAux.getType().equals(Symbol.Type.CONST_INT)) {
                            logger.incompatibleTypes(lexicalAnalyzer.getLine(), idAux.getLexeme());
                            throw new CompilerException();
                        }
                    }

                    { // (10)
                        if (!dAux.getType().equals(idAux.getType()) &&
                        !(dAux.getType().equals(Symbol.Type.CONST_BYTE) && idAux.getType().equals(Symbol.Type.CONST_INT) && Integer.parseInt(idAux.getLexeme()) >= 0 && Integer.parseInt(idAux.getLexeme()) <= 255)) {
                            logger.incompatibleTypes(lexicalAnalyzer.getLine(), idAux.getLexeme());
                            throw new CompilerException();
                        }
                    }
                }
            }
            match(Token.SEMICOLON);
        }
    }

    private void B() throws CompilerException {
        logger.info(tag, "B()");

        if (tokenEqualTo(Token.READLN)) {
            READ();
        } else if (tokenEqualTo(Token.WRITE)) {
            WRITE();
        } else if (tokenEqualTo(Token.WHILE)) {
            WHILE();
        } else if (tokenEqualTo(Token.IF)) {
            IF();
        } else if (tokenEqualTo(Token.IDENTIFIER)) {
            ATT();
        } else {
            match(Token.SEMICOLON);
        }
    }

    /**
     * EXP -> EXPS [( < | > | <= | >= | == | != ) EXPS]
     * 
     * @throws CompilerException
     */
    private void EXP(Symbol dAux) throws CompilerException {
        logger.info(tag, "EXP()");
        
        EXPS(dAux);

        while (tokenEqualTo(Token.COMPARATOR)) {
            match(Token.COMPARATOR);
            dAux = symbol;
            EXPS(dAux);
        }
        
    }

    /**
     * EXPS -> [( + | - )] EXPM { ( + | - | or ) EXPM }*
     * 
     * @throws CompilerException
     */
    private void EXPS(Symbol dAux) throws CompilerException {
        logger.info(tag, "EXPRS()");

        if (tokenEqualTo(Token.MINUS)) {
            match(Token.MINUS);
            EXPM(dAux);
        } else {
            EXPM(dAux);
            while (tokenIn(Token.SUM, Token.MINUS, Token.OR)) {
                if (tokenEqualTo(Token.SUM)) {
                    match(Token.SUM);
                } else if (tokenEqualTo(Token.MINUS)) { 
                    match(Token.MINUS);
                }else {
                    match(Token.OR);
                }
                EXPM(dAux);
            }
        }
    }

    /**
     * EXPM -> VALUE { ( * | / | and ) VALUE }*
     * 
     * @throws CompilerException
     */
    private void EXPM(Symbol dAux) throws CompilerException {
        logger.info(tag, "EXPM()");

        if (tokenEqualTo(Token.SUM)) {
            match(Token.SUM);
            VALUE(dAux);
        } else {
            VALUE(dAux);
        }

        while (tokenIn(Token.MULT, Token.AND)) {
            if (tokenEqualTo(Token.MULT)) {
                match(Token.MULT);
            } else if (tokenEqualTo(Token.AND)) {
                match(Token.AND);
            }
            VALUE(dAux);
        }
    }

    /**
     * VALUE -> “(“ EXP ”)” | ID | const | not EXP
     * 
     * @throws CompilerException
     */
    private void VALUE(Symbol dAux) throws CompilerException {
        logger.info(tag, "VALUE()");

        if (tokenEqualTo(Token.OPEN_PAR)) {
            match(Token.OPEN_PAR);
            EXP(null);
            match(Token.CLOSE_PAR);   
        } else if (tokenEqualTo(Token.IDENTIFIER)) {
            match(Token.IDENTIFIER);
        } else if (tokenEqualTo(Token.CONST_VALUE)) {

            match(Token.CONST_VALUE);

            // (12)
            {
                if (dAux.getToken().equals(Token.IDENTIFIER) && dAux.getClazz() == null) {
                    logger.notDeclared(lexicalAnalyzer.getLine(), dAux.getLexeme());
                    throw new CompilerException();
                }
            }
        } else {
            match(Token.NOT);
            EXP(null);
        }
    }


    /**
     * READ -> readln “(“ ID “)”; 
     * @throws CompilerException
     */
    private void READ() throws CompilerException {
        logger.info(tag, "READ()");

        Symbol dAux;

        match(Token.READLN);
        match(Token.OPEN_PAR);
        
        dAux = symbol;
        match(Token.IDENTIFIER);

        // (11)
        if(dAux.getClazz() == null) {
            logger.notDeclared(lexicalAnalyzer.getLine(), dAux.getLexeme());
            throw new CompilerException();
        }

        match(Token.CLOSE_PAR);
        match(Token.SEMICOLON);
    }

    /**
     * WRITE -> write “(“ EXP { , EXP }* “)” ; | writeln “(“ EXP { , EXP }* “)” ;
     * @throws CompilerException
     */
    private void WRITE() throws CompilerException {
        logger.info(tag, "WRITE()");

        match(Token.WRITE);
        match(Token.OPEN_PAR);
        EXP(symbol);

        while (tokenEqualTo(Token.COMMA)) {
            match(Token.COMMA);
            EXP(symbol);
        }
        
        match(Token.CLOSE_PAR);
        match(Token.SEMICOLON);
    }

    /**
     * WHILE -> while “(” EXP “)” ( “begin” { B }* “end” | B )
     * 
     * @throws CompilerException
     */
    private void WHILE() throws CompilerException {
        logger.info(tag, "WHILE()");

        if (tokenEqualTo(Token.WHILE)) {
            match(Token.WHILE);
            
            match(Token.OPEN_PAR);
            EXP(symbol);
            match(Token.CLOSE_PAR);
            
            if (tokenEqualTo(Token.BEGIN)){
                match(Token.BEGIN);
                while (!tokenEqualTo(Token.END)) {
                    B();
                }
                match(Token.END);
            } else {
                B();
            }
        }
    }

    /**
     * IF -> if “(“ EXP “)” THEN ( B | “begin” { B }* “end” ) [ else B | “begin” { B }* “end” ]
     * 
     * @throws CompilerException
     */
    private void IF() throws CompilerException {
        logger.info(tag, "IF()");

        if (tokenEqualTo(Token.IF)) {
            match(Token.IF);

            match(Token.OPEN_PAR);
            EXP(symbol);
            match(Token.CLOSE_PAR);

            match(Token.THEN);

            if (tokenEqualTo(Token.BEGIN)) {
                match(Token.BEGIN);
                while (!tokenEqualTo(Token.END)) {
                    B();
                }
                match(Token.END);
            } else {
                B();
            }

            if (tokenEqualTo(Token.ELSE)){
                match(Token.ELSE);

                if (tokenEqualTo(Token.BEGIN)) {
                    match(Token.BEGIN);
                    while (!tokenEqualTo(Token.END)) {
                        B();
                    }
                    match(Token.END);
                } else {
                    B();
                }
            }
        }
    }

    /**
     * ATT -> ID = EXP;
     * 
     * @throws CompilerException
     */
    private void ATT() throws CompilerException {
        Symbol dAux = symbol;
        match(Token.IDENTIFIER);
        match(Token.ATTR);
        EXP(dAux);
        match(Token.SEMICOLON);
    }

 }