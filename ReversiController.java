package reversi;



public class ReversiController implements IController {
	private IModel model;
	private IView view;
	
	@Override
	public void initialise(IModel model, IView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void startup() {
		model.clear(0);
		model.setFinished(false);
		model.setPlayer(1);
		model.setBoardContents(3, 3, 1);
        model.setBoardContents(4, 4, 1);
        model.setBoardContents(3, 4, 2);
        model.setBoardContents(4, 3, 2);
        update();
	}

	@Override
	public void squareSelected(int player, int x, int y) {
		if (model.hasFinished()) {
			view.feedbackToUser(player, "Game Over!");
			return;
		}
		
		if (player != model.getPlayer()) {
			view.feedbackToUser(player, "It is not your turn.");
			return;
		}
		
		if (!isValidMove(player, x, y)) {
			view.feedbackToUser(player, "Can't move piece there.");
			return;
		}
		
		makeMove(player, x, y);
		switchPlayer();
		update();
	}
	
	private boolean isValidMove(int player, int x, int y) {
	    if (model.getBoardContents(x, y) != 0) {
	        return false; 
	    }
	    return countFlips(player, x, y) > 0; 
	}
	
	@Override
	public void doAutomatedMove(int player) {
		if (model.hasFinished()) return;
		if (player != model.getPlayer()) return;
		
		int bestX = -1;
		int bestY = -1;
		int bestFlips = -1;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				int flips = countFlips(player, x, y);
				if (flips > bestFlips) {
					bestFlips = flips;
					bestX = x;
					bestY = y;
				}
			}
		}
		
		if (bestX != -1) {
			makeMove(player, bestX, bestY);
			switchPlayer();
		}
		
		update();
	}
	
	@Override
	public void update() {
		if (canPlayerMove(1) || canPlayerMove(2)) {
			model.setFinished(false);
			if (!canPlayerMove(model.getPlayer())) {
				model.setPlayer(3 - model.getPlayer());
			}
			
			String playerName;
			if (model.getPlayer() == 1) {
				playerName = "White";
			}
			else {
				playerName = "Black";
			}
			view.feedbackToUser(model.getPlayer(), playerName + " player choose where to put your piece");
			
			String oppName;
			if (model.getPlayer() == 1) {
				oppName = "Black";
			}
			else {
				oppName = "White";
			}
			view.feedbackToUser(3 - model.getPlayer(), oppName + " player it is not your turn");
		}
		else {
			model.setFinished(true);
			declareWinner();
		}
		view.refreshView();
	}
	
	private int countFlips(int player, int x, int y) {
		int flips = 0;
		int opp = 3 - player;
		
		int[] directionx = {-1, -1, -1, 0, 1, 1, 1, 0};
		int[] directiony = {-1, 0, 1, 1, 1, 0, -1, -1};
		
		for (int direction = 0; direction < 8; direction++) {
			int newx = x + directionx[direction];
			int newy = y + directiony[direction];
			int count = 0;
			
			while (newx >= 0 && newx < 8 && newy >= 0 && newy < 8 && model.getBoardContents(newx, newy) == opp) {
				count++;
				newx += directionx[direction];
				newy += directiony[direction];
			}
			
			if (count > 0 && newx >= 0 && newx < 8 && newy >= 0 && newy < 8 && model.getBoardContents(newx, newy) == player) {
				flips += count;
			}
		}
		
		return flips;
	}
	
	private void makeMove(int player, int x, int y) {
		model.setBoardContents(x, y, player);
		int opp = 3 - player;
		
		int[] directionx = {-1, -1, -1, 0, 1, 1, 1, 0};
		int[] directiony = {-1, 0, 1, 1, 1, 0, -1, -1};
		
		for (int direction = 0; direction < 8; direction++) {
			int newx = x + directionx[direction];
			int newy = y + directiony[direction];
			int count = 0;
			
			while (newx >= 0 && newx < 8 && newy >= 0 && newy < 8 && model.getBoardContents(newx, newy) == opp) {
				count++;
				newx += directionx[direction];
				newy += directiony[direction];
			}
			
			if (count > 0 && newx >= 0 && newx < 8 && newy >= 0 && newy < 8 && model.getBoardContents(newx, newy) == player) {
				newx = x + directionx[direction];
				newy = y + directiony[direction];
				while (model.getBoardContents(newx, newy) == opp) {
					model.setBoardContents(newx, newy, player);
					newx += directionx[direction];
					newy += directiony[direction];
				}
			}
		}
	}
	
	private void switchPlayer() {
		model.setPlayer(3 - model.getPlayer());
	}
	
	private boolean canPlayerMove(int player) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (isValidMove(player, x, y)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void declareWinner() {
        int white = 0;
        int black = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (model.getBoardContents(x, y) == 1) {
                    white++;
                } else if (model.getBoardContents(x, y) == 2) {
                    black++;
                }
            }
        }

        if (white > black) {
            view.feedbackToUser(1, "White won. White " + white + " to Black " + black + ". Reset the game to replay.");
            view.feedbackToUser(2, "White won. White " + white + " to Black " + black + ". Reset the game to replay.");
        } else if (black > white) {
            view.feedbackToUser(1, "Black won. Black " + black + " to White " + white + ". Reset the game to replay.");
            view.feedbackToUser(2, "Black won. Black " + black + " to White " + white + ". Reset the game to replay.");
        } else {
            view.feedbackToUser(1, "Draw. Both players ended with " + white + " pieces. Reset the game to replay.");
            view.feedbackToUser(2, "Draw. Both players ended with " + white + " pieces. Reset the game to replay.");
        }
    }

}
