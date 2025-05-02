#include <iostream>
#include <vector>
#include <chrono>   // For timing
#include <algorithm> // For std::max and std::min
#include <cstdlib>  // For rand()

using namespace std;

// Linear Search
int linearSearch(const vector<int>& arr, int target) {
    for (int i = 0; i < arr.size(); ++i) {
        if (arr[i] == target) {
            return i; // Found
        }
    }
    return -1; // Not found
}

// Binary Search (requires sorted array)
int binarySearch(const vector<int>& arr, int target) {
    int low = 0, high = arr.size() - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
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

// Interpolation Search (requires sorted array with uniform distribution)
int interpolationSearch(const vector<int>& arr, int target) {
    int low = 0, high = arr.size() - 1;
    while (low <= high && target >= arr[low] && target <= arr[high]) {
        if (low == high) {
            if (arr[low] == target) return low;
            return -1;
        }
        int pos = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low]);
        if (arr[pos] == target) return pos;
        if (arr[pos] < target) low = pos + 1;
        else high = pos - 1;
    }
    return -1; // Not found
}

// Measure execution time of a search function
pair<int, double> measureExecutionTime(int (*searchFunc)(const vector<int>&, int), const vector<int>& arr, int target) {
    auto start = chrono::high_resolution_clock::now();
    int result = searchFunc(arr, target);
    auto end = chrono::high_resolution_clock::now();
    double duration = chrono::duration<double, milli>(end - start).count(); // Convert to milliseconds
    return {result, duration};
}

// Perform all searches and display results
void performAllSearches(const vector<int>& arr, int target) {
    auto [result1, linear_time] = measureExecutionTime(linearSearch, arr, target);
    cout << "\nLinear Search: Found at index " << result1 << " in " << linear_time << " ms\n";

    auto [result2, binary_time] = measureExecutionTime(binarySearch, arr, target);
    cout << "Binary Search: Found at index " << result2 << " in " << binary_time << " ms\n";

    auto [result3, interpolation_time] = measureExecutionTime(interpolationSearch, arr, target);
    cout << "Interpolation Search: Found at index " << result3 << " in " << interpolation_time << " ms\n";

    cout << "\n--- Execution Time Summary (in ms) ---\n";
    cout << "Linear Search: " << linear_time << " ms\n";
    cout << "Binary Search: " << binary_time << " ms\n";
    cout << "Interpolation Search: " << interpolation_time << " ms\n";
}

// Main function
int main() {
    int size;
    while (true) {
        cout << "Enter the size of the array (e.g., 1-10000000): ";
        cin >> size;
        if (size > 0) break;
        cout << "Size must be a positive integer.\n";
    }

    // Generate an array with values from 1 to size
    vector<int> arr(size);
    for (int i = 0; i < size; ++i) {
        arr[i] = i + 1;
    }

    // Target value selection
    int target;
    string choice;
    cout << "Do you want to choose the target value manually? (yes/no): ";
    cin >> choice;

    if (choice == "yes") {
        while (true) {
            cout << "Enter a target value between 1 and " << size << ": ";
            cin >> target;
            if (target >= 1 && target <= size) break;
            cout << "Please enter a value between 1 and " << size << ".\n";
        }
    } else {
        target = arr[rand() % size];
        cout << "Target value selected automatically: " << target << "\n";
    }

    // Display array preview
    cout << "\nArray Preview:\n";
    int previewStart = max(0, target - 10);
    int previewEnd = min(size, target + 10);
    for (int i = previewStart; i < previewEnd; ++i) {
        cout << arr[i] << " ";
    }
    cout << "...\n";

    // Search menu
    while (true) {
        cout << "\nChoose the search method:\n";
        cout << "[1] Linear Search\n";
        cout << "[2] Binary Search\n";
        cout << "[3] Interpolation Search\n";
        cout << "[4] All Search Methods\n";
        cout << "[5] Exit\n";
        int option;
        cin >> option;

        if (option == 1) {
            auto [result, linear_time] = measureExecutionTime(linearSearch, arr, target);
            cout << "Linear Search: Found at index " << result << " in " << linear_time << " ms\n";
        } else if (option == 2) {
            auto [result, binary_time] = measureExecutionTime(binarySearch, arr, target);
            cout << "Binary Search: Found at index " << result << " in " << binary_time << " ms\n";
        } else if (option == 3) {
            auto [result, interpolation_time] = measureExecutionTime(interpolationSearch, arr, target);
            cout << "Interpolation Search: Found at index " << result << " in " << interpolation_time << " ms\n";
        } else if (option == 4) {
            performAllSearches(arr, target);
        } else if (option == 5) {
            cout << "Exiting the program.\n";
            break;
        } else {
            cout << "Invalid option! Please try again.\n";
        }

        cout << "\nDo you want to search again? (yes/no): ";
        cin >> choice;
        if (choice != "yes") break;
    }

    return 0;
}
