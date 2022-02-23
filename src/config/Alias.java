package src.config;

public class Alias {
    String symbolicPath;
    String absolutePath;
    Alias(String symPath, String absolutePath){
        this.symbolicPath = symPath;
        this.absolutePath = absolutePath;
    }

    public String getSymbolicPath() {
        return symbolicPath;
    }
    public String getAbsolutePath() {
        return absolutePath;
    }
}
