import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SearchComparison {

    // Linear Search
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Found
            }
        }
        return -1; // Not found
    }

    // Binary Search (requires sorted array)
    public static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == target) {
                return mid; // Found
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1; // Not found
    }

    // Interpolation Search
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high && arr[low] <= target && arr[high] >= target) {
            if (arr[low] == arr[high]) {
                return (arr[low] == target) ? low : -1; // Avoid division by zero
            }
            int pos = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low]);

            if (pos < low || pos > high) {
                return -1; // Not found
            }

            if (arr[pos] == target) {
                return pos; // Found
            } else if (arr[pos] < target) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        return -1; // Not found
    }

    // Measure execution time in milliseconds
    public static long measureExecutionTime(Runnable searchMethod) {
        long startTime = System.currentTimeMillis();
        searchMethod.run();
        return System.currentTimeMillis() - startTime;
    }

    // Perform all searches and display results
    public static void performAllSearches(int[] arr, int target) {
        try {
            // Linear Search
            long linearTime = measureExecutionTime(() -> linearSearch(arr, target));
            System.out.println("\nLinear Search: Found at index " + linearSearch(arr, target) + " in " + linearTime + " ms");

            // Binary Search
            long binaryTime = measureExecutionTime(() -> binarySearch(arr, target));
            System.out.println("Binary Search: Found at index " + binarySearch(arr, target) + " in " + binaryTime + " ms");

            // Interpolation Search
            long interpolationTime = measureExecutionTime(() -> interpolationSearch(arr, target));
            System.out.println("Interpolation Search: Found at index " + interpolationSearch(arr, target) + " in " + interpolationTime + " ms");

            // Summary
            System.out.println("\n--- Execution Time Summary (in ms) ---");
            System.out.println("Linear Search: " + linearTime + " ms");
            System.out.println("Binary Search: " + binaryTime + " ms");
            System.out.println("Interpolation Search: " + interpolationTime + " ms");
        } catch (Exception e) {
            System.out.println("An error occurred during searches: " + e.getMessage());
        }
    }

    // Main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] arr = {};
        int target = 0;

        // Get the size of the array from the user
        while (true) {
            try {
                System.out.print("Enter the size of the array (e.g., 1-10000000): ");
                int size = scanner.nextInt();
                if (size <= 0) throw new IllegalArgumentException("Size must be a positive integer.");

                // Generate a sorted array from 1 to size
                arr = new int[size];
                for (int i = 0; i < size; i++) arr[i] = i + 1;
                break;
            } catch (Exception e) {
                System.out.println("Invalid input! " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }

        // Ask if the user wants to input the target value manually or automatically
        while (true) {
            System.out.print("Do you want to choose the target value manually? (yes/no): ");
            String choice = scanner.next().toLowerCase();
            if (choice.equals("yes") || choice.equals("no")) {
                if (choice.equals("yes")) {
                    while (true) {
                        try {
                            System.out.print("Enter a target value between 1 and " + arr.length + ": ");
                            target = scanner.nextInt();
                            if (target >= 1 && target <= arr.length) break;
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please enter an integer.");
                            scanner.nextLine();
                        }
                    }
                } else {
                    target = arr[new Random().nextInt(arr.length)];
                    System.out.println("Target value selected automatically: " + target);
                }
                break;
            }
            System.out.println("Invalid choice! Please enter 'yes' or 'no'.");
        }

        // Search Method Selection Loop
        while (true) {
            try {
                System.out.println("\nChoose the search method:");
                System.out.println("[1] Linear Search");
                System.out.println("[2] Binary Search");
                System.out.println("[3] Interpolation Search");
                System.out.println("[4] All Search Methods");
                System.out.println("[5] Exit");
                System.out.print("Enter the corresponding number: ");
                int searchChoice = scanner.nextInt();

                switch (searchChoice) {
                    case 1 -> {
                        long linearTime = measureExecutionTime(() -> linearSearch(arr, target));
                        System.out.println("Linear Search: Found at index " + linearSearch(arr, target) + " in " + linearTime + " ms");
                    }
                    case 2 -> {
                        long binaryTime = measureExecutionTime(() -> binarySearch(arr, target));
                        System.out.println("Binary Search: Found at index " + binarySearch(arr, target) + " in " + binaryTime + " ms");
                    }
                    case 3 -> {
                        long interpolationTime = measureExecutionTime(() -> interpolationSearch(arr, target));
                        System.out.println("Interpolation Search: Found at index " + interpolationSearch(arr, target) + " in " + interpolationTime + " ms");
                    }
                    case 4 -> performAllSearches(arr, target);
                    case 5 -> {
                        System.out.println("Exiting the program.");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please select a valid option.");
                }

                System.out.print("\nDo you want to search again? (yes/no): ");
                if (!scanner.next().equalsIgnoreCase("yes")) {
                    System.out.println("Exiting the program.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
}
