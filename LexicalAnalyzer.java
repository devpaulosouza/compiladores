import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class LexicalAnalyzer {
    private static String tag = "LexicalAnalyzer";
    private static Logger logger = Logger.getInstance();

    private static final short FINAL_STATE = 99;
    private static final char EOF = (char)65535;


    private SymbolTable symbolTable;

    private BufferedReader br;

    private short state;
    private String lexeme;
    private char currentChar;
    private Character previousChar;
    private Symbol.Type type;

    private boolean errorLexemeNotFound;

    private int line;

    LexicalAnalyzer(String filename, SymbolTable simbolTable) {
        logger.info(tag, "constructor");

        this.state = 0;
        this.line = 1;
        this.symbolTable = simbolTable;

        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            logger.error(tag, "Arquivo informado não encontrado");
        }
    }

    private void readChar() {
        try {
            if (previousChar == null) {
                currentChar = (char) br.read();
                if (currentChar == '\n') {
                    line++;
                }
            } else {
                currentChar = previousChar;
                previousChar = null;
            }
        } catch (IOException e) {
            logger.error(tag, e.getMessage());
        }
    }

    public Symbol readToken() throws CompilerException {
        Symbol symbol = null;

        lexeme = "";
        type = null;

        while (state != FINAL_STATE && !errorLexemeNotFound && currentChar != EOF) {

            readChar();

            if (!StringUtil.isValidChar(currentChar)) {
                logger.invalidChar(line);
                throw new CompilerException();
            }

            switch(state) {
                case 0: 
                    e0();
                    break;
                case 1: 
                    e1();
                    break;
                case 2: 
                    e2();
                    break;
                case 3: 
                    e3();
                    break;
                case 4: 
                    e4();
                    break;
                case 5: 
                    e5();
                    break;
                case 6: 
                    e6();
                    break;
                case 7: 
                    e7();
                    break;
                case 8: 
                    e8();
                    break;
                case 9: 
                    e9();
                    break;
                case 10: 
                    e10();
                    break;
                case 11: 
                    e11();
                    break;
                case 12:
                    e12();
                    break;
                case 13:
                    e13();
                    break;
                case 14:
                    e14();
                    break;
            }


            // diferente de EOF
            if (currentChar != EOF && previousChar == null && currentChar != '\n' && currentChar != ' ') {
                lexeme = lexeme.concat(currentChar + "");
            }

        }

        if (errorLexemeNotFound) {
            logger.invalidLexeme(line, lexeme);
            throw new CompilerException();
        } else {
             symbol = this.symbolTable.getByLexeme(lexeme);

            if (symbol == null && !"".equals(lexeme)) {
                if (type != null) {
                    symbol = this.symbolTable.addConst(lexeme, type);
                } else if ("true".equals(lexeme) || "false".equals(lexeme)) {
                    symbol = this.symbolTable.addConst(lexeme, Symbol.Type.CONST_BOOL);
                } else {
                    symbol = this.symbolTable.addIdentifier(lexeme);
                }
            } 
        }
        if (symbol != null) {
            logger.debug(tag, symbol.toString());
        }

        state = 0;

        return symbol;
    }

    private void e0() {
        if (currentChar == '_') {
            state = 3;
        } else if (currentChar == '\n' || currentChar == ' ') {
            state = 0;
            lexeme = "";
        } else if (StringUtil.isAlpha(currentChar)) {
            state = 1;
        } else if (currentChar == '0') {
            state = 4;
        } else if (StringUtil.isNumeric(currentChar)) {
            state = 6;
        } else if (currentChar == '\'') {
            state = 7;
        } else if (currentChar == '>' || currentChar == '<' || currentChar == '=') {
            state = 9;
        } else if (currentChar == '!') {
            state = 10;
        } else if (currentChar == '/') {
            state = 11;
        } else if (currentChar == '(' || currentChar == ')' || currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == ';' || currentChar == ',' ) {
            state = FINAL_STATE;
        }
    }

    // TODO: achar um nome para essa caceta
    private void devolve () {
        previousChar = currentChar;
        state = FINAL_STATE;
    }

    private void e1() {
        if (currentChar == '_' || StringUtil.isAlpha(currentChar) || StringUtil.isNumeric(currentChar)) {
            state = 1;
        } else {
            devolve();
        }
    }
    private void e2() {
        
    }
    private void e3() {
        if (currentChar == '_') {
            state = 3;
        } else {
            errorLexemeNotFound = true;
        }
    }
    private void e4() {
        if (currentChar == 'h') {
            state = 5;
        } else if (StringUtil.isNumeric(currentChar)) {
            state = 6;
        } else {
            devolve();
            type = Symbol.Type.CONST_INT;
        }
    }
    private void e5() {
        if (StringUtil.isHexa(currentChar)) {
            state = 14;
        } else {
            errorLexemeNotFound = true;
        }
    }
    private void e6() {
        if (StringUtil.isNumeric(currentChar)) {
            state = 6;
        } else {
            devolve();
        }

        type = Symbol.Type.CONST_INT;
    }
    private void e7() {
        if (currentChar == '\'') {
            state = 8;
        } else if (currentChar != '\n') {
            state = 7;
        } else {
            errorLexemeNotFound = true;
        }
    }
    private void e8() {
        if (currentChar == '\'') {
            state = 7;
        } else {
            devolve();
            type = Symbol.Type.CONST_STRING;
        }
    }
    private void e9() {
        if (currentChar == '=') {
            state = FINAL_STATE;
        } else {
            devolve();
        }
    }
    private void e10() {
        if (currentChar == '=') {
            devolve();
        } else {
            errorLexemeNotFound = true;
        }
    }
    private void e11() {
        if (currentChar == '*') {
            state = 12;
        } else {
            devolve();
        }
    }
    private void e12() {
        if (currentChar == '*') {
            state = 13;
        } else {
            state = 12;
        }
    }
    private void e13() {
        if (currentChar == '/') {
            state = 0;
            lexeme = "";
            readChar();
        } else {
            state = 12;
        }
    }
    private void e14() {
        if (StringUtil.isHexa(currentChar)) {
            state = FINAL_STATE;
            type = Symbol.Type.CONST_BYTE;
        } else {
            errorLexemeNotFound = true;
        }
    }

    public int getLine() {
        return this.line;
    }

}