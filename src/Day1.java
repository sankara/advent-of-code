import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws Exception {
        // Create a Scanner object to read the file
        File inputFile = new File("resources/day-1.input.txt");
        Scanner scanner = new Scanner(inputFile);

        // Count the number of lines in the file
        int lineCount = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            lineCount++;
        }

        // Create an array to store the data
        int[] array1 = new int[lineCount];
        int[] array2 = new int[lineCount];

        // Read each line of the file and split it on whitespace
        scanner.close();
        scanner = new Scanner(inputFile);
        for (int i = 0; i < lineCount; i++) {
            String line = scanner.nextLine();
            String[] columns = line.split(" {3}");

            // Store the first column in array1 and the second column in array2
            array1[i] = Integer.parseInt(columns[0]);
            array2[i] = Integer.parseInt(columns[1]);
        }

        // Sort both arrays
        Arrays.sort(array1);
        Arrays.sort(array2);

        int sum = 0, similarity = 0, current = 0;
        for (int i = 0; i < lineCount; i++) {
            sum += Math.abs(array1[i] - array2[i]);

            while (current < lineCount - 1 && array2[i] > array1[current]) {
                current++;
            }
            for (int j = current; j < lineCount && array2[i] == array1[j]; j++) {
                similarity += array2[i];
            }
        }

        System.out.println("Sum: " + sum);
        //Sum: 2430334
        System.out.println("Similarity: " + similarity);
        //Similarity: 28786472
    }
}
