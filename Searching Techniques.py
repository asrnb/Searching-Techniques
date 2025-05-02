import numpy as np
import time

# Linear Search
def linear_search(arr, target):
    for i, value in enumerate(arr):
        if value == target:
            return i  # Found
    return -1  # Not found

# Binary Search (requires sorted array)
def binary_search(arr, target):
    low, high = 0, len(arr) - 1
    while low <= high:
        mid = (low + high) // 2
        if arr[mid] == target:
            return mid  # Found
        elif arr[mid] < target:
            low = mid + 1
        else:
            high = mid - 1
    return -1  # Not found

# Interpolation Search
def interpolation_search(arr, target):
    low, high = 0, len(arr) - 1
    while low <= high and arr[low] <= target <= arr[high]:
        if arr[high] == arr[low]:  # Avoid division by zero
            pos = low
        else:
            pos = low + int(((target - arr[low]) / (arr[high] - arr[low])) * (high - low))

        if pos < low or pos > high:
            return -1  # Not found

        if arr[pos] == target:
            return pos  # Found
        elif arr[pos] < target:
            low = pos + 1
        else:
            high = pos - 1
    return -1  # Not found


# Measure execution time of a function in milliseconds
def measure_execution_time(func, *args):
    start_time = time.time()
    result = func(*args)
    end_time = time.time()
    return result, (end_time - start_time) * 1000  # Convert to milliseconds

# Perform all search techniques and display results
def perform_all_searches(arr, target):
    try:
        # Linear Search
        result, linear_time = measure_execution_time(linear_search, arr, target)
        print(f"\nLinear Search: Found at index {result} in {linear_time:.6f} ms")

        # Binary Search
        result, binary_time = measure_execution_time(binary_search, arr, target)
        print(f"Binary Search: Found at index {result} in {binary_time:.6f} ms")

        # Interpolation Search
        result, interpolation_time = measure_execution_time(interpolation_search, arr, target)
        print(f"Interpolation Search: Found at index {result} in {interpolation_time:.6f} ms")


        # Summary
        print("\n--- Execution Time Summary (in ms) ---")
        print(f"Linear Search: {linear_time:.6f} ms")
        print(f"Binary Search: {binary_time:.6f} ms")
        print(f"Interpolation Search: {interpolation_time:.6f} ms")
    except Exception as e:
        print(f"An error occurred during searches: {str(e)}")

# Main function with improved data generation
def main():
    while True:
        try:
            # Get the size of the array from the user
            size = int(input("Enter the size of the array (e.g., 1-10000000): "))
            if size <= 0:
                raise ValueError("Size must be a positive integer.")

            # Generate an array with unique values from 1 to size, sorted in ascending order
            arr = list(range(1, size + 1))
            break  # Exit loop if input is valid

        except ValueError as e:
            print(f"Invalid input! {e}")

    # Ask if the user wants to input the target value manually or automatically
    while True:
        target_choice = input("Do you want to choose the target value manually? (yes/no): ").lower()
        if target_choice in ['yes', 'no']:
            break
        print("Invalid choice! Please enter 'yes' or 'no'.")

    if target_choice == 'yes':
        while True:
            try:
                target = int(input(f"Enter a target value between 1 and {size}: "))
                if 1 <= target <= size:
                    break  # Valid input
                else:
                    print(f"Please enter a value between 1 and {size}.")
            except ValueError:
                print("Invalid input! Please enter an integer.")
    else:
        # Select a guaranteed target from the generated array
        target = arr[np.random.randint(0, size)]
        print(f"Target value selected automatically: {target}")

    # Display a preview of the array
    print("\nArray Preview:")
    if size > 100:
        start = max(0, target - 10)  # Display 10 values before the target
        print(f"...{arr[start:start + 10]}")
    else:
        print(arr)  # Display full array for small sizes

    while True:
        try:
            # Ask the user to choose the search method
            print("\nChoose the search method:")
            print("[1] Linear Search")
            print("[2] Binary Search")
            print("[3] Interpolation Search")
            print("[4] All Search Methods")
            print("[5] Exit")

            choice = int(input("Enter the corresponding number: "))
            if choice not in range(1, 7):
                print("Invalid choice! Please select a valid option.")
                continue

            # Perform the selected search
            if choice == 1:
                result, linear_time = measure_execution_time(linear_search, arr, target)
                print(f"\nLinear Search: Found at index {result} in {linear_time:.6f} ms")

            elif choice == 2:
                result, binary_time = measure_execution_time(binary_search, arr, target)
                print(f"\nBinary Search: Found at index {result} in {binary_time:.6f} ms")

            elif choice == 3:
                result, interpolation_time = measure_execution_time(interpolation_search, arr, target)
                print(f"\nInterpolation Search: Found at index {result} in {interpolation_time:.6f} ms")

            elif choice == 4:
                perform_all_searches(arr, target)

            elif choice == 5:
                print("Exiting the program.")
                break

            # Ask if the user wants to search again
            continue_choice = input("\nDo you want to search again? (yes/no): ").lower()
            if continue_choice != 'yes':
                print("Exiting the program.")
                break

        except ValueError:
            print("Invalid input! Please enter a valid number.")

if __name__ == "__main__":
    main()
