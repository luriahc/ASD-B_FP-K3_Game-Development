/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #3
 * 1 - 5026221134 - Mohammad Affan Shofi
 * 2 - 5026231121 - Rian Chairul Ichsan
 * 3 - 5026231211 - Hafidz Putra Dermawan
 */

package sudoku;

import java.util.Random;

public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private int[][] solution = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle() {
        super();
    }

    private void generateCompletePuzzle() {
        solve(0, 0);
        // Salin solusi untuk referensi
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            System.arraycopy(numbers[i], 0, solution[i], 0, SudokuConstants.GRID_SIZE);
        }
    }

    private boolean solve(int row, int col) {
        if (row == SudokuConstants.GRID_SIZE)
            return true; // Selesai

        int nextRow = (col == SudokuConstants.GRID_SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % SudokuConstants.GRID_SIZE;

        for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
            if (isSafeToPlace(row, col, num)) {
                numbers[row][col] = num;
                if (solve(nextRow, nextCol))
                    return true;
                numbers[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    private boolean isSafeToPlace(int row, int col, int num) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (numbers[row][i] == num || numbers[i][col] == num)
                return false;
        }
        int subgridRowStart = (row / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        int subgridColStart = (col / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        for (int i = 0; i < SudokuConstants.SUBGRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.SUBGRID_SIZE; j++) {
                if (numbers[subgridRowStart + i][subgridColStart + j] == num)
                    return false;
            }
        }
        return true;
    }

    public void newPuzzle(int cellsToGuess) {
        generateCompletePuzzle(); // Menghasilkan solusi lengkap

        // Mark semua sel sebagai diberikan
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                isGiven[row][col] = true;
            }
        }

        // Hapus angka secara acak untuk membuat puzzle
        Random random = new Random();
        for (int i = 0; i < cellsToGuess; i++) {
            int row, col;
            do {
                row = random.nextInt(SudokuConstants.GRID_SIZE);
                col = random.nextInt(SudokuConstants.GRID_SIZE);
            } while (numbers[row][col] == 0); // Jangan hapus sel yang sudah kosong

            numbers[row][col] = 0;
            isGiven[row][col] = false;
        }
    }

    public int[][] getSolution() {
        return solution;
    }
}
