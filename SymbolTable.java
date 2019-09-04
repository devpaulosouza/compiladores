import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class SymbolTable {
    private Map<Token, List<String>> table;  

    public SymbolTable() {
        table = new HashMap<>();

        table.put(Token.CONST, Arrays.asList("cont"));
        table.put(Token.TYPE, Arrays.asList("integer", "byte", "string", "boolean"));
        table.put(Token.WHILE, Arrays.asList("while"));
        table.put(Token.IF, Arrays.asList("if"));
        table.put(Token.ELSE, Arrays.asList("else"));
        table.put(Token.COMPARATOR, Arrays.asList("and", "or"));
        table.put(Token.NOT, Arrays.asList("not"));
        table.put(Token.ATTR, Arrays.asList("="));
        table.put(Token.BINARY_COMPARATOR, Arrays.asList("==", "<=", "<", ">", ">=", "!="));
        table.put(Token.SUM, Arrays.asList("+", "-"));
        table.put(Token.MULT, Arrays.asList("*", "/"));
        table.put(Token.COMMA, Arrays.asList(","));
        table.put(Token.SEMICOLON, Arrays.asList(";"));
        table.put(Token.BEGIN, Arrays.asList("begin"));
        table.put(Token.END, Arrays.asList("end"));
        table.put(Token.THEN, Arrays.asList("then"));
        table.put(Token.READLN, Arrays.asList("readln"));
        table.put(Token.WRITE, Arrays.asList("write", "writeln"));
        table.put(Token.OPEN_PAR, Arrays.asList("("));
        table.put(Token.CLOSE_PAR, Arrays.asList(")"));
        table.put(Token.BOOLEAN_VALUE, Arrays.asList("true", "false"));
        table.put(Token.MAIN, Arrays.asList("main"));
        table.put(Token.IDENTIFIER, Arrays.asList());
    }

    public Token getByLexeme(String lexeme) {
        for (Entry<Token, List<String>> entry : table.entrySet()) {
            if (entry.getValue().contains(lexeme)) {
                return entry.getKey();
            }
        }

        table.get(Token.IDENTIFIER).add(lexeme);
        
        return Token.IDENTIFIER;
    }
}