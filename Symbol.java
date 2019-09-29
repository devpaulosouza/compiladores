class Symbol {
    private Token token;
    private String lexeme;

    public Symbol(Token token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
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

    public String toString() {
        return token.toString() + " " + lexeme;
    }

}