import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class SymbolTable {
    private Logger logger = Logger.getInstance();
    private static String tag = "SymbolTable";
    private Map<Token, List<Symbol>> table;  

    public SymbolTable() {
        logger.debug(tag, "criando tabela de símbolos");

        table = new HashMap<>();
        
        table.put(Token.AND, Arrays.asList(createSymbol(Token.AND, "and")));
        table.put(Token.ATTR, Arrays.asList(createSymbol(Token.ATTR, "=")));
        table.put(Token.BEGIN, Arrays.asList(createSymbol(Token.BEGIN, "begin")));
        table.put(Token.CLOSE_PAR, Arrays.asList(createSymbol(Token.CLOSE_PAR, ")")));
        table.put(Token.COMMA, Arrays.asList(createSymbol(Token.COMMA, ",")));
        table.put(Token.CONST, Arrays.asList(createSymbol(Token.CONST, "cont")));
        table.put(Token.END, Arrays.asList(createSymbol(Token.END, "end")));
        table.put(Token.ELSE, Arrays.asList(createSymbol(Token.ELSE, "else")));
        table.put(Token.IDENTIFIER, Arrays.asList());
        table.put(Token.IF, Arrays.asList(createSymbol(Token.IF, "if")));
        table.put(Token.MAIN, Arrays.asList(createSymbol(Token.MAIN, "main")));
        table.put(Token.NOT, Arrays.asList(createSymbol(Token.NOT, "not")));
        table.put(Token.OPEN_PAR, Arrays.asList(createSymbol(Token.OPEN_PAR, "(")));
        table.put(Token.OR, Arrays.asList(createSymbol(Token.OR, "or")));
        table.put(Token.READLN, Arrays.asList(createSymbol(Token.READLN, "readln")));
        table.put(Token.SEMICOLON, Arrays.asList(createSymbol(Token.SEMICOLON, ";")));
        table.put(Token.THEN, Arrays.asList(createSymbol(Token.THEN, "then")));
        table.put(Token.WHILE, Arrays.asList(createSymbol(Token.WHILE, "while")));



        table.put(Token.BOOLEAN_VALUE, Arrays.asList(
                    createSymbol(Token.BOOLEAN_VALUE, "true"), 
                    createSymbol(Token.BOOLEAN_VALUE, "false")
        ));
        table.put(Token.COMPARATOR, Arrays.asList(
                    createSymbol(Token.COMPARATOR, "=="), 
                    createSymbol(Token.COMPARATOR, "<="), 
                    createSymbol(Token.COMPARATOR, "<"), 
                    createSymbol(Token.COMPARATOR, ">"), 
                    createSymbol(Token.COMPARATOR, ">="), 
                    createSymbol(Token.COMPARATOR, "!=")
        ));
        table.put(Token.MULT, Arrays.asList(
                    createSymbol(Token.MULT, "*"),
                    createSymbol(Token.MULT, "/")
        ));
        table.put(Token.SUM, Arrays.asList(
                    createSymbol(Token.SUM, "+"),
                    createSymbol(Token.SUM, "-")
        ));
        table.put(Token.TYPE, Arrays.asList(
                    createSymbol(Token.TYPE, "integer"), 
                    createSymbol(Token.TYPE, "byte"), 
                    createSymbol(Token.TYPE, "string"), 
                    createSymbol(Token.TYPE, "boolean")
        ));
        table.put(Token.WRITE, Arrays.asList(
                    createSymbol(Token.WRITE, "write"),
                    createSymbol(Token.WRITE, "writeln")
        ));
    }

    private Symbol createSymbol(Token token, String lexeme) {
        return new Symbol(token, lexeme);
    }

    public Token getByLexeme(String lexeme) {
        for (Entry<Token, List<Symbol>> entry : table.entrySet()) {
            if (entry.getValue().stream().filter(symbol -> symbol.getLemexe().equals(lexeme)).findFirst().isPresent()) {
                return entry.getKey();
            }
        }

        table.get(Token.IDENTIFIER).add(createSymbol(Token.IDENTIFIER, lexeme));
        
        return Token.IDENTIFIER;
    }

    public void show() {
        for (Entry<Token, List<Symbol>> entry : table.entrySet()) {
            entry.getValue().forEach(symbol -> logger.debug(tag, "table : " + symbol.toString()));
        }
    }
}