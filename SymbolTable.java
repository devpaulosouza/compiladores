import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

class SymbolTable {
    private Logger logger = Logger.getInstance();
    private static String tag = "SymbolTable";
    private Map<Token, List<Symbol>> table;  

    public SymbolTable() {
        logger.info(tag, "criando tabela de símbolos");

        table = new HashMap<>();
        
        table.put(Token.AND, new ArrayList<>(Arrays.asList(createSymbol(Token.AND, "and"))));
        table.put(Token.ATTR, new ArrayList<>(Arrays.asList(createSymbol(Token.ATTR, "="))));
        table.put(Token.BEGIN, new ArrayList<>(Arrays.asList(createSymbol(Token.BEGIN, "begin"))));
        table.put(Token.CLOSE_PAR, new ArrayList<>(Arrays.asList(createSymbol(Token.CLOSE_PAR, ")"))));
        table.put(Token.COMMA, new ArrayList<>(Arrays.asList(createSymbol(Token.COMMA, ","))));
        table.put(Token.CONST, new ArrayList<>(Arrays.asList(createSymbol(Token.CONST, "cont"))));
        table.put(Token.END, new ArrayList<>(Arrays.asList(createSymbol(Token.END, "end"))));
        table.put(Token.ELSE, new ArrayList<>(Arrays.asList(createSymbol(Token.ELSE, "else"))));
        table.put(Token.IDENTIFIER, new ArrayList<>(Arrays.asList()));
        table.put(Token.IF, new ArrayList<>(Arrays.asList(createSymbol(Token.IF, "if"))));
        table.put(Token.MAIN, new ArrayList<>(Arrays.asList(createSymbol(Token.MAIN, "main"))));
        table.put(Token.NOT, new ArrayList<>(Arrays.asList(createSymbol(Token.NOT, "not"))));
        table.put(Token.OPEN_PAR, new ArrayList<>(Arrays.asList(createSymbol(Token.OPEN_PAR, "("))));
        table.put(Token.OR, new ArrayList<>(Arrays.asList(createSymbol(Token.OR, "or"))));
        table.put(Token.READLN, new ArrayList<>(Arrays.asList(createSymbol(Token.READLN, "readln"))));
        table.put(Token.SEMICOLON, new ArrayList<>(Arrays.asList(createSymbol(Token.SEMICOLON, ";"))));
        table.put(Token.THEN, new ArrayList<>(Arrays.asList(createSymbol(Token.THEN, "then"))));
        table.put(Token.WHILE, new ArrayList<>(Arrays.asList(createSymbol(Token.WHILE, "while"))));



        table.put(Token.BOOLEAN_VALUE, new ArrayList<>(
            Arrays.asList(
                    createSymbol(Token.BOOLEAN_VALUE, "true"), 
                    createSymbol(Token.BOOLEAN_VALUE, "false")
            )
        ));
        table.put(Token.COMPARATOR, new ArrayList<>(
            Arrays.asList(
                    createSymbol(Token.COMPARATOR, "=="), 
                    createSymbol(Token.COMPARATOR, "<="), 
                    createSymbol(Token.COMPARATOR, "<"), 
                    createSymbol(Token.COMPARATOR, ">"), 
                    createSymbol(Token.COMPARATOR, ">="), 
                    createSymbol(Token.COMPARATOR, "!=")
            )
        ));
        table.put(Token.MULT, new ArrayList<>(
            Arrays.asList(
                    createSymbol(Token.MULT, "*"),
                    createSymbol(Token.MULT, "/")
            )
        ));
        table.put(Token.SUM, new ArrayList<>(
            Arrays.asList(
                    createSymbol(Token.SUM, "+"),
                    createSymbol(Token.SUM, "-")
            )
        ));
        table.put(Token.TYPE, new ArrayList<>(
            Arrays.asList(
                    createSymbol(Token.TYPE, "integer"), 
                    createSymbol(Token.TYPE, "byte"), 
                    createSymbol(Token.TYPE, "string"), 
                    createSymbol(Token.TYPE, "boolean")
            )
        ));
        table.put(Token.WRITE, new ArrayList<>(
            Arrays.asList(
                    createSymbol(Token.WRITE, "write"),
                    createSymbol(Token.WRITE, "writeln")
            )
        ));
    }

    private Symbol createSymbol(Token token, String lexeme) {
        return new Symbol(token, lexeme);
    }

    public Symbol addIdentifier(String lexeme) {
        Symbol symbol = createSymbol(Token.IDENTIFIER, lexeme);
        this.table.get(Token.IDENTIFIER).add(symbol);

        return symbol;
    }

    public Symbol getByLexeme(String lexeme) {
        Symbol symbol = null;
        for (Entry<Token, List<Symbol>> entry : table.entrySet()) {
            Optional<Symbol> optional = entry.getValue()
                .stream()
                .filter(s -> s.getLemexe().equals(lexeme))
                .findFirst();
            
            if (optional.isPresent()) {
                symbol = optional.get();
            }
        }
        return symbol;
    }

    public void show() {
        for (Entry<Token, List<Symbol>> entry : table.entrySet()) {
            entry.getValue().forEach(symbol -> logger.debug(tag, "table : " + symbol.toString()));
        }
    }
}