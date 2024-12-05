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

import javax.swing.*;
import java.awt.*;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;

    // Private variables
    GameBoardPanel board = new GameBoardPanel();
    JTextField statusBar = new JTextField("Welcome to Sudoku!");
    private JLabel timerLabel = new JLabel("Time: 00:00");
    private Timer timer;
    private int seconds = 0;

    // Constructor
    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Add game board
        cp.add(board, BorderLayout.CENTER);

        // Add timer and top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(timerLabel, BorderLayout.WEST);
        cp.add(topPanel, BorderLayout.NORTH);

        // Add side panel
        JPanel sidePanel = new JPanel(new GridLayout(5, 1));
        JButton hintButton = new JButton("Hint");
        JButton checkButton = new JButton("Check");
        JButton solveButton = new JButton("Solve");
        sidePanel.add(hintButton);
        sidePanel.add(checkButton);
        sidePanel.add(solveButton);
        cp.add(sidePanel, BorderLayout.EAST);

        // Add status bar
        statusBar.setEditable(false);
        statusBar.setHorizontalAlignment(JTextField.CENTER);
        cp.add(statusBar, BorderLayout.SOUTH);

        // Timer logic
        timer = new Timer(1000, e -> {
            seconds++;
            timerLabel.setText(String.format("Time: %02d:%02d", seconds / 60, seconds % 60));
        });
        timer.start();

        // Button functionality
        hintButton.addActionListener(e -> board.giveHint());
        checkButton.addActionListener(e -> board.checkProgress());
        solveButton.addActionListener(e -> board.solvePuzzle());

        // Initialize game board
        board.newGame();
        updateStatusBar();

        // Status listener
        board.addStatusListener(e -> updateStatusBar());

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setSize(700, 700);
        setVisible(true);
    }

    private void updateStatusBar() {
        int remainingCells = board.getRemainingCells();
        statusBar.setText(remainingCells + " cells remaining");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuMain::new);
    }
}
