class Symbol {

    private Token token;
    
    private String lexeme;

    private Type type;

    public Symbol(Token token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    public Symbol(Token token, String lexeme, Type type) {
        this.token = token;
        this.lexeme = lexeme;
        this.type = type;
    }

    public Token getToken() {
        return this.token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if (type != null) {
            return token.toString() + " "  +  type.toString() + " " + lexeme ;
        } else {
            return token.toString() + " "  +  lexeme ;
        }
    }
    
    public enum Type {
        CONST_INT("INTEGER"),
        CONST_BYTE("BYTE"),
        CONST_BOOL("BOOLEAN"),
        CONST_STRING("STRING");

        private String type;

        Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type;
        }
    } 
}