package reversi;

import javax.swing.*;
import java.awt.*;
import reversi.IView;
import reversi.IModel;
import reversi.IController;

public class GUIView implements IView {
    private IModel model;
    private IController controller;
    private int x, y, playerNumber;
    private JFrame whiteFrame, blackFrame;
    private JLabel whiteLabel, blackLabel;
    private BoardSquareButton[][] whiteSquares, blackSquares;
    private JButton whiteRestartButton, whiteAIButton;
    private JButton blackRestartButton, blackAIButton;
    
    @Override
    public void initialise(IModel model, IController controller) {
        this.model = model;
        this.controller = controller;
        
        whiteFrame = new JFrame("White Player's Board");
        blackFrame = new JFrame("Black Player's Board");
        
        whiteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        blackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        whiteFrame.setLayout(new BorderLayout());
        blackFrame.setLayout(new BorderLayout());
        
        whiteLabel = new JLabel("White's Turn", SwingConstants.CENTER);
        blackLabel = new JLabel("Black's Turn", SwingConstants.CENTER);
        whiteFrame.add(whiteLabel, BorderLayout.NORTH);
        blackFrame.add(blackLabel, BorderLayout.NORTH);
        
        JPanel whiteBoardPanel = new JPanel(new GridLayout(8,8));
        JPanel blackBoardPanel = new JPanel(new GridLayout(8,8));
        whiteSquares = new BoardSquareButton[8][8];
        blackSquares = new BoardSquareButton[8][8];
        
        for (int x = 0; x < 8; x++) {
        	for (int y = 0; y < 8; y++) {
        		whiteSquares[x][y] = new BoardSquareButton(x, y, model, controller, 1);
        		blackSquares[x][y] = new BoardSquareButton(x, y, model, controller, 2);
        		whiteBoardPanel.add(whiteSquares[x][y]);
        		blackBoardPanel.add(blackSquares[x][y]);
        	}
        }
        
        whiteFrame.add(whiteBoardPanel, BorderLayout.CENTER);
        blackFrame.add(blackBoardPanel, BorderLayout.CENTER);
        
        JPanel whiteButtonPanel = new JPanel();
        JPanel blackButtonPanel = new JPanel();
        
        whiteRestartButton = new JButton("Restart");
        whiteAIButton = new JButton("Greedy AI");
        blackRestartButton = new JButton("Restart");
        blackAIButton = new JButton("Greedy AI");
        
        whiteButtonPanel.add(whiteAIButton);
        whiteButtonPanel.add(blackRestartButton);
        blackButtonPanel.add(blackAIButton);
        blackButtonPanel.add(blackRestartButton);
        
        whiteFrame.add(whiteButtonPanel, BorderLayout.SOUTH);
        blackFrame.add(blackButtonPanel, BorderLayout.SOUTH);
        
        whiteRestartButton.addActionListener(e -> controller.startup());
        blackRestartButton.addActionListener(e -> controller.startup());

        whiteAIButton.addActionListener(e -> controller.doAutomatedMove(1));
        blackAIButton.addActionListener(e -> controller.doAutomatedMove(2));

        whiteFrame.setSize(600, 700);
        blackFrame.setSize(600, 700);

        whiteFrame.setVisible(true);
        blackFrame.setVisible(true);
    }

    @Override
    public void refreshView() {
    	for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                whiteSquares[x][y].repaint();
                blackSquares[x][y].repaint();
            }
        }
    }

    @Override
    public void feedbackToUser(int playerNumber, String message) {
        if (playerNumber == 1) {
        	whiteLabel.setText(message);
        }
        else {
        	blackLabel.setText(message);
        }
    }
}