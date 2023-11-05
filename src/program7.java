import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.TreeMap;

public class program7 {

    /** 
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {

        String csvFile = "src/small_sample.csv"; // Path to your CSV file
        String line;

        // TreeMap to store Car Make as key and its corresponding AVL tree as value
        TreeMap<String, myAVL> carMakeTrees = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
               s String[] data = line.split(",");
                // Assuming the order of columns in the CSV matches the constructor parameters
                final SaleRecord saleRecord = new SaleRecord(data[0], data[1], data[2], data[3],
                        data[4], Integer.parseInt(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]),
                        Double.parseDouble(data[8]));

                String carMake = saleRecord.getMake();

                // Check if the Car Make AVL tree already exists, if not, create a new one
                if (!carMakeTrees.containsKey(carMake)) {
                    carMakeTrees.put(carMake, new myAVL(10));
                }

                // Insert the SaleRecord into the corresponding AVL tree based on Car Make
                myAVL tree = carMakeTrees.get(carMake);
                tree.root = tree.insert(tree.root, saleRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print Sale records for each Car Make
        for (String carMake : carMakeTrees.keySet()) {
            System.out.println("Sale records for Car Make: " + carMake);
            myAVL tree = carMakeTrees.get(carMake);
            tree.preOrder(tree.root);
            System.out.println("-------------");
        }
    }
}
