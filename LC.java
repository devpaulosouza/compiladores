public class LC {
    private static String tag = "LC";
    private static Logger logger;

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
        logger = Logger.getInstance();
    }

    private static String getFilename(String... args) {
        String filename = "teste.l";

        for (String arg : args) {
            if (!arg.contains("-d") && !arg.contains("-e") && !arg.contains("-i")) {
                filename = arg;
                break;    
            }
        }

        return filename;
    }

    public static void main(String... args) {
        
        initLogger(args);

        String filename = getFilename(args);

        logger.debug(tag, "Arquivo selecionado: " + filename);
        
        try {
            Compiler compiler = new Compiler(filename);
            compiler.S();
        } catch(CompilerException ignored) {
            ;
        }
    }
}