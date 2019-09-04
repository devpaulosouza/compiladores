public class LC {
    private static void initLogger(String... args) {
        for (String arg : args) {
            if (arg.contains("-d")) {
                Logger.getInstance().setLevel(LogLevel.DEBUG);
            } else if (arg.contains("-i")) {
                Logger.getInstance().setLevel(LogLevel.INFO);
            } else if (arg.contains(("-e"))) {
                Logger.getInstance().setLevel(LogLevel.ERROR);
            }
        }
    }

    public static void main(String... args) {
        initLogger(args);
        Compiler compiler = new Compiler();

        try {
            compiler.S();
        } catch(CompilerException ignored) {
            ;
        }
    }
}