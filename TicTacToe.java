import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame {

    private JButton[][] board = new JButton[3][3];
    private JLabel status = new JLabel("Turn: X (Player)");
    private JLabel scoreLabel = new JLabel("X: 0   O: 0   Draws: 0");
    private JPanel homePanel, gamePanel;

    private int scoreX = 0, scoreO = 0, draws = 0;
    private char currentPlayer = 'X';
    private boolean vsAI = true;

    public TicTacToe() {

        // WINDOW STYLE
        setTitle("Smart Tic-Tac-Toe | Modern UI");
        setSize(470, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        getContentPane().setBackground(new Color(230, 240, 250));

        createHomeScreen();
        createGameScreen();

        add(homePanel, "home");
        add(gamePanel, "game");

        showHome();
        setVisible(true);
    }

    // ----------------------------------------------------
    // NEW MODERN HOME SCREEN
    // ----------------------------------------------------
    private void createHomeScreen() {

        homePanel = new JPanel();
        homePanel.setBackground(new Color(230, 245, 255));
        homePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Smart Tic-Tac-Toe", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setOpaque(true);
        title.setBackground(new Color(90, 140, 255));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton pvpBtn = new JButton("Player vs Player");
        JButton aiBtn = new JButton("Player vs AI");
        JButton exitBtn = new JButton("Exit");

        styleMainButton(pvpBtn);
        styleMainButton(aiBtn);
        styleMainButton(exitBtn);

        pvpBtn.addActionListener(e -> { vsAI = false; startGame(); });
        aiBtn.addActionListener(e -> { vsAI = true; startGame(); });
        exitBtn.addActionListener(e -> System.exit(0));

        gbc.gridy = 0; gbc.ipady = 10;
        homePanel.add(title, gbc);
        gbc.gridy = 1;
        homePanel.add(pvpBtn, gbc);
        gbc.gridy = 2;
        homePanel.add(aiBtn, gbc);
        gbc.gridy = 3;
        homePanel.add(exitBtn, gbc);
    }

    // ----------------------------------------------------
    // NEW MODERN GAME SCREEN
    // ----------------------------------------------------
    private void createGameScreen() {

        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(230, 240, 250));

        // Beautiful Top Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(90, 140, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        status.setFont(new Font("Segoe UI", Font.BOLD, 20));
        status.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        scoreLabel.setForeground(Color.WHITE);

        topPanel.add(status);
        topPanel.add(scoreLabel);

        // Board Panel (modern)
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        boardPanel.setBackground(new Color(230, 240, 250));

        Font tileFont = new Font("Segoe UI", Font.BOLD, 48);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                JButton btn = new JButton("");
                btn.setFont(tileFont);
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 230), 3));
                btn.setOpaque(true);

                // Hover effect
                btn.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        if (btn.getText().isEmpty())
                            btn.setBackground(new Color(215, 230, 255));
                    }

                    public void mouseExited(MouseEvent evt) {
                        if (btn.getText().isEmpty())
                            btn.setBackground(Color.WHITE);
                    }
                });

                btn.addActionListener(new CellClick(r, c));
                board[r][c] = btn;
                boardPanel.add(btn);
            }
        }

        // Bottom Buttons
        JButton reset = new JButton("Reset Game");
        JButton home = new JButton("Back to Menu");

        styleSmallButton(reset);
        styleSmallButton(home);

        reset.addActionListener(e -> resetBoard());
        home.addActionListener(e -> showHome());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(230, 240, 250));
        bottomPanel.add(reset);
        bottomPanel.add(home);

        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // ----------------------------------------------------
    // BUTTON STYLES
    // ----------------------------------------------------
    private void styleMainButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 22));
        b.setBackground(new Color(100, 150, 255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSmallButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setBackground(new Color(100, 150, 255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ----------------------------------------------------
    // GAME LOGIC (UNCHANGED)
    // ----------------------------------------------------

    private class CellClick implements ActionListener {
        int r, c;
        CellClick(int r, int c){ this.r = r; this.c = c; }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!board[r][c].getText().isEmpty())
                return;

            board[r][c].setText(String.valueOf(currentPlayer));
            board[r][c].setForeground(currentPlayer == 'X' ? new Color(0, 90, 200) : new Color(210, 0, 120));

            // Win check
            if (checkWin()) {
                if (currentPlayer == 'X') scoreX++; else scoreO++;
                updateScore();
                gameOver(currentPlayer + " Wins!");
                return;
            }

            if (isFull()) {
                draws++;
                updateScore();
                gameOver("Draw!");
                return;
            }

            // Switch player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            status.setText("Turn: " + currentPlayer + (vsAI && currentPlayer == 'O' ? " (AI)" : ""));

            if (vsAI && currentPlayer == 'O') {
                aiMove();
            }
        }
    }

    // ----------------------------------------------------
    // AI LOGIC (UNCHANGED)
    // ----------------------------------------------------
    private void aiMove() {
        int[] move = bestMove();
        board[move[0]][move[1]].doClick();
    }

    private int[] bestMove() {
        int bestScore = -1000;
        int[] best = {0, 0};

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().isEmpty()) {
                    board[r][c].setText("O");
                    int score = minimax(0, false);
                    board[r][c].setText("");

                    if (score > bestScore) {
                        bestScore = score;
                        best[0] = r;
                        best[1] = c;
                    }
                }
            }
        }
        return best;
    }

    private int minimax(int depth, boolean isMax) {
        if (checkWinSimple("O")) return 10 - depth;
        if (checkWinSimple("X")) return depth - 10;
        if (isFull()) return 0;

        int best = isMax ? -1000 : 1000;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                if (board[r][c].getText().isEmpty()) {
                    board[r][c].setText(isMax ? "O" : "X");
                    int score = minimax(depth + 1, !isMax);
                    board[r][c].setText("");

                    best = isMax ? Math.max(best, score)
                                 : Math.min(best, score);
                }
            }
        }
        return best;
    }

    // ----------------------------------------------------
    // UTILITIES (UNCHANGED)
    // ----------------------------------------------------
    private boolean checkWin() {
        String[][] b = getBoard();

        for (int i = 0; i < 3; i++) {
            if (b[i][0].equals(b[i][1]) && b[i][1].equals(b[i][2]) && !b[i][0].isEmpty()) return true;
            if (b[0][i].equals(b[1][i]) && b[1][i].equals(b[2][i]) && !b[0][i].isEmpty()) return true;
        }

        if (b[0][0].equals(b[1][1]) && b[1][1].equals(b[2][2]) && !b[0][0].isEmpty()) return true;
        return b[0][2].equals(b[1][1]) && b[1][1].equals(b[2][0]) && !b[0][2].isEmpty();
    }

    private boolean checkWinSimple(String p) { return checkWin(); }

    private String[][] getBoard() {
        String[][] b = new String[3][3];
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                b[r][c] = board[r][c].getText();
        return b;
    }

    private boolean isFull() {
        for (JButton[] row : board)
            for (JButton b : row)
                if (b.getText().isEmpty()) return false;
        return true;
    }

    private void updateScore() {
        scoreLabel.setText(String.format("X: %d   O: %d   Draws: %d", scoreX, scoreO, draws));
    }

    private void resetBoard() {
        for (JButton[] row : board)
            for (JButton b : row) {
                b.setText("");
                b.setBackground(Color.WHITE);
                b.setEnabled(true);
            }
        currentPlayer = 'X';
        status.setText("Turn: X");
    }

    private void gameOver(String msg) {
        status.setText(msg);
        for (JButton[] row : board)
            for (JButton b : row)
                b.setEnabled(false);

        JOptionPane.showMessageDialog(this, msg);
    }

    private void showHome() {
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "home");
    }

    private void startGame() {
        resetBoard();
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "game");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}