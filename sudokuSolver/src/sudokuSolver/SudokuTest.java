package sudokuSolver;

import static org.junit.Assert.*;

import org.junit.Test;

import apcs.Window;

public class SudokuTest {

	@Test
	public void test() {
		verify("643971002290850004081300609000069040024507906069080500300020401050010768406098205",
			   "643971852297856314581342679835269147124537986769184523378625491952413768416798235");
		verify("000900000007030006351607980003516240048000619106894073839051764010070000005069821",
			   "264985137987132456351647982793516248548723619126894573839251764612478395475369821");
		verify("340601905958300400761490002800034509000750630130002040293806054604070200580200003",
			   "342681975958327416761495382876134529429758631135962847293816754614573298587249163");
	}
	
	@Test
	public void testMediumSudoku() {
		verify("700961020402003091069000000007630204903050780026400003600307100004000002001000308",
			   "738961425452783691169245837817639254943152786526478913685327149394816572271594368");
		verify("007920005201000030000036040705840910300001050000090004078012493003005000020009571",
			   "637924185241758639859136247765843912394261758182597364578612493913475826426389571");
		verify("410003000070061200800002103001078405005040000024500306107004002900600701200097050",
			   "412783569573961248869452173691378425385246917724519386157834692948625731236197854");
	}
	
	@Test
	public void testHardSudoku() {
		verify("308050001052000600000906030000041009005000200000008000003000100000002090020670805",
			   "368254971952137684714986532287541369435769218691328457873495126546812793129673845");
		verify("030000000000000000095000672020604001060009000800002360907006004400870000000040500",
			   "238167459674295183195438672523684791761359248849712365917526834452873916386941527");
		verify("065007000040900080000000936000103000102000569000000003680002040000090000700340000",
			   "965837124243916785817425936496153278132784569578269413689572341324691857751348692");
	}
	
	public void verify(String board, String expected) {
		
		Sudoku solver = new Sudoku(board);
		Sudoku solution = new Sudoku(expected);
		
		solver.draw();
		
		if (solver.solve()) {
			assertArrayEquals(solver.board, solution.board);
			draw(solver.original, solver.board, solution.board);
		}
		else fail("Could not solve sudoku board.");
	}
	
	/**
	 * Draws the given Sudoku board.
	 * @param board - the Sudoku board to draw.
	 */
	public static void draw(int[][] original, int[][] board, int[][] expected) {
		Window.out.background("white");
		Window.out.font("Courier", Window.width() / 9 - 5);
		
		Window.out.color("black");
		for (int i = 0 ; i < 9 ; i++) {
			Window.out.line(i * Window.width() / 9, 0, i * Window.width() / 9, Window.width());
			Window.out.line(0, i * Window.width() / 9, Window.width(), i * Window.width() / 9);
		}
		
		Window.out.rectangle(Window.width() / 2, 2, Window.width(), 4);
		Window.out.rectangle(Window.width() / 2, Window.width() / 3 - 1, Window.width(), 4);
		Window.out.rectangle(Window.width() / 2, Window.width() * 2 / 3 - 1, Window.width(), 4);
		Window.out.rectangle(Window.width() / 2, Window.width() - 2, Window.width(), 4);
		Window.out.rectangle(2, Window.width() / 2, 4, Window.width());
		Window.out.rectangle(Window.width() / 3 - 1, Window.width() / 2, 4, Window.width());
		Window.out.rectangle(Window.width() * 2 / 3 - 1, Window.width() / 2, 4, Window.width());
		Window.out.rectangle(Window.width() - 2, Window.width() / 2, 4, Window.width());
		
		for (int x = 0 ; x < 9 ; x++) {
			for (int y = 0 ; y < 9 ; y++) {
				if (board[x][y] > 0) {
					if (board[x][y] == original[x][y])
						Window.out.color("black");
					else if (board[x][y] == expected[x][y])
						Window.out.color("green");
					else
						Window.out.color("red");
					Window.out.print(board[x][y], x * Window.width() / 9 + Window.width() / 40, (y + 1) * Window.width() / 9 - Window.width() / 50);
				}
			}
		}
		Window.frame(1000);
	}

	public int[][] toBoard(String boardString) {
		int[][] board = new int[9][9];
		
		for (int i = 0 ; i < boardString.length() ; i++)
			board[i % 9][i / 9] = boardString.charAt(i) - '0';
		
		return board;
	}
	
}