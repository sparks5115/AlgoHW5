import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        runHasher();
        //runDijkstra();

    }

    private static void runDijkstra() {
        Dijkstra d = new Dijkstra();
        System.out.println(d.setup());
    }

    static void runHasher() throws FileNotFoundException {
        File file = new File("src\\ElephantsChild.txt");
        Hasher h = new Hasher(file);
        h.putAll();
    }
}
