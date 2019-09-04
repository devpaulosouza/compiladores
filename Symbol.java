class Symbol {
    private Token token;
    private String lemexe;

    public Symbol(Token token, String lexeme) {
        this.token = token;
        this.lemexe = lexeme;
    }

    public Token getToken() {
        return this.token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getLemexe() {
        return this.lemexe;
    }

    public void setLexeme(String lexeme) {
        this.lemexe = lexeme;
    }

    public String toString() {
        return token.toString() + " " + lemexe;
    }

}