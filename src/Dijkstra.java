import java.util.LinkedList;
import java.util.Scanner;

public class Dijkstra {
    int[][] matrix =   {{0, 50, 7, 10, 0, 0, 0, 0, 0, 0},
                        {50, 0, 30, 0, 3, 0, 99, 0, 0, 0},
                        {7, 30, 0, 6, 27, 15, 0, 0, 0, 0},
                        {10, 0, 6, 0, 0, 11, 0, 0, 4, 0},
                        {0, 3, 27, 0, 0, 12, 120, 105, 0, 0},
                        {0, 0, 15, 11, 12, 0, 0, 119, 5, 0},
                        {0, 99, 0, 0, 120, 0, 0, 2, 0, 67},
                        {0, 0, 0, 0, 105, 119, 2, 0, 122, 66},
                        {0, 0, 0, 4, 0, 5, 0, 122, 0, 190},
                        {0, 0, 0, 0, 0, 0, 67, 66, 190, 0}};

    Node A = new Node('A', 0);
    Node J = new Node('J', 1);
    Node M = new Node('M', 2);
    Node R = new Node('R', 3);
    Node K = new Node('K', 4);
    Node S = new Node('S', 5);
    Node I = new Node('I', 6);
    Node N = new Node('N', 7);
    Node T = new Node('T', 8);
    Node D = new Node('D', 9);

    Node[] nodes = {A,J,M,R,K,S,I,N,T,D};

    int setup() {
        Scanner in = new Scanner(System.in);

        System.out.println("Where would you like to start?");
        String input = in.next();
        char s = input.toUpperCase().toCharArray()[0];
        Node start=null;

        System.out.println("Where would you like to end?");
        input = in.next();
        char e = input.toUpperCase().toCharArray()[0];
        Node end=null;

        for(Node n:nodes){
            if(s==n.getName()){
                start = n;
            }if(e==n.getName()){
                end = n;
            }
        }if(start==null||end==null) {
            System.out.println("Invalid Node");
            return -1;
        }else if(start.equals(end)){
            return 0;
        }

        return length(start, end);

    }

    private int length(Node start, Node end) {
        Queue q = new Queue();
        start.setDistFromS(0);
        Node wNode = start;
        while(!wNode.equals(end)) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[wNode.getIndex()][i] > 0){
                    int distThru = wNode.distFromS + matrix[wNode.getIndex()][i];
                    if (nodes[i].getDistFromS() > distThru) {
                        nodes[i].setPen(wNode);
                        nodes[i].setDistFromS(distThru);
                        q.add(nodes[i]);
                    }
                }
            }
            wNode = q.next();
        }
        path(end);
        return end.getDistFromS();
    }

    void path(Node end){
        Node n = end;
        LinkedList<Character> path = new LinkedList<>();
        while(n!=null){
            path.addFirst(n.name);
            n=n.pen;
        }
        System.out.println("By following path:\n" + path.toString() + "\nThe shortest path is:");
    }


}
