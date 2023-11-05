import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class program7 extends myAVL {
    program7(int myLimit) {
        super(myLimit);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) throws ParseException {

        String csvFile = "src/small_sample.csv"; // Path to your CSV file
        String line;

        myAVL tree = new myAVL(10);
        // /* Constructing tree given in the above figure */

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming the order of columns in the CSV matches the constructor parameters
                final SaleRecord saleRecord = new SaleRecord(data[0], data[1], data[2], data[3],
                        data[4], Integer.parseInt(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]),
                        Double.parseDouble(data[8]));

                tree.root = tree.insert(tree.root, saleRecord);
                // System.out.println(tree.root.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // /* The constructed AVL Tree would be
        // 30
        // / \
        // 20 40
        // / \ \
        // 10 25 50
        // */
        System.out.println("Preorder traversal" +
                " of constructed tree is : ");
        tree.preOrder(tree.root);

    }
    //

}