import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class LexicalAnalyzer {
    private static String tag = "LexicalAnalyzer";
    private static Logger logger = Logger.getInstance();

    private static final short FINAL_STATE = 99;

    private Symbol preview;
    private BufferedReader br;

    private short state;
    private String lexeme;
    private char currentChar;
    private Character previousChar;

    private boolean errorLexemeNotFound;

    private int line;

    LexicalAnalyzer(String filename) {
        logger.info(tag, "construtor");

        this.state = 0;
        this.line = 1;

        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            logger.error(tag, "Arquivo informado não encontrado");
        }
    }

    public void readToken() {
        logger.info(tag, "readToken");

        lexeme = "";

        while (state != FINAL_STATE && !errorLexemeNotFound) {
            try {
                if (previousChar == null) {
                    currentChar = (char) br.read();
                } else {
                    currentChar = previousChar;
                    previousChar = null;
                }
            } catch (IOException e) {
                logger.error(tag, e.getMessage());
            }

            // diferente de EOF
            if ((int)currentChar != 65535) {
                lexeme = lexeme.concat(currentChar + "");
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
            }
        }

        if (errorLexemeNotFound) {
            logger.invalidLexeme(line, lexeme);
        }
        logger.debug(tag, lexeme);
    }

    private void e0() {
        if (currentChar == '_') {
            state = 3;
        } else if (currentChar == '\n' || currentChar == ' ') {
            state = 0;
        } else if (StringUtil.isAlpha(currentChar)) {
            state = 1;
        } else if (currentChar == '0') {
            state = 4;
        } else if (StringUtil.isNumeric(currentChar)) {
            state = 6;
        } else if (currentChar == '\'') {
            state = 7;
        } else if (currentChar == '>' || currentChar == '<' || currentChar == '>') {
            state = 9;
        } else if (currentChar == '!') {
            state = 10;
        } else if (currentChar == '/') {
            state = 11;
        }
    }
    private void e1() {
        logger.debug(tag, "e1 " + (int)currentChar);

        if (currentChar == '_' || StringUtil.isAlpha(currentChar) || StringUtil.isNumeric(currentChar)) {
            state = 1;
        } else {
            // devolve
            previousChar = currentChar;
            state = FINAL_STATE;
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
        
    }
    private void e5() {
        
    }
    private void e6() {
        
    }
    private void e7() {
        
    }
    private void e8() {
        
    }
    private void e9() {
        
    }
    private void e10() {
        
    }
    private void e11() {
        
    }
    private void e12() {
        
    }
    private void e13() {
        
    }

}