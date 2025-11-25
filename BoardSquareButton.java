package reversi;

import javax.swing.*;
import reversi.IModel;
import reversi.IController;
import java.awt.Color;
import java.awt.Graphics;

public class BoardSquareButton extends JButton {
    private int x, y, playerNumber;
    private IModel model;
    private IController controller;

    public BoardSquareButton(int x, int y, IModel model, IController controller, int playerNumber) {
        this.x = x;
        this.y = y;
        this.model = model;
        this.controller = controller;
        this.playerNumber = playerNumber;

        addActionListener(e -> controller.squareSelected(playerNumber, x, y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int state = model.getBoardContents(x, y);

        if (state == 1) {
            g.setColor(Color.WHITE);
            g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
        } else if (state == 2) {
            g.setColor(Color.BLACK);
            g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
        }
    }

}
