package usaco;

public class CompanyTree {
/* 
 * 
 * 
 */
	public static void main(String[] args) {
		int[] manager =      {-1, 0, 0, 0, 1, 1, 1, 2, 2, 3, 9, 9};
		int[] salary =       { 5, 3, 4, 6, 3, 2, 2, 4, 5, 3, 2, 2};
		int[] productivity = { 6, 3, 5, 2, 4, 6, 1, 3, 8, 3, 8, 2};
		
		int max = maxProfit(0, manager, salary, productivity);
		System.out.println(max);
	}

	private static int maxProfit(int employee, int[] manager, int[] salary, int[] productivity) {
		int childrenProfit = 0;
		// go to each employee and see if they report to me
		for (int e = 0; e < manager.length; e ++) {
			if (manager[e] == employee) {
				//recursively call maxProfit on child
				int childProfit = maxProfit(e, manager, salary, productivity);
				if (childProfit >= 0) {
					//gain by firing
					childrenProfit += childProfit;
				} else {
					System.out.println("Fire employee " + e);
				}
			}
		}
		return productivity[employee] - salary[employee] + childrenProfit;
	}
}
