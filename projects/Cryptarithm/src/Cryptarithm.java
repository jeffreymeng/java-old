
public class Cryptarithm {

    public static void main(String[] args) {
        String solution = solve("SEND+MORE=MONEY");
        System.out.println(solution);
    }

    private static String solve(String equation) {
        // Base case
        if (isSolved(equation)) {
            return equation;
        }
        
        // Find the first choice
        for (int i = 0 ; i < equation.length() ; i++) {
            char c = equation.charAt(i);
            char before = 0;
            if (i > 0)
                before = equation.charAt(i - 1);
            
            // If this is a letter
            if (Character.isLetter(c)) {
                
                // Go to each available choice
                for (char n = '0' ; n <= '9' ; n++) {
                    // If there is already a letter for this number
                    if (equation.indexOf(n) >= 0)
                        continue;
                    
                    // If its the first digit of a number, can't be a 0
                    if (n == '0' && (before == 0 || before == '+' || before == '='))
                        continue;
                    
                    String newEquation = equation.replace(c, n);
                    
                    String solveEquation = solve(newEquation);
                    if (solveEquation != null) {
                        return solveEquation;
                    }
                }
                
                return null;
            }
        }
        
        return null;
    }

    private static boolean isSolved(String equation) {
        for (int i = 0 ; i < equation.length() ; i++) {
            if (Character.isLetter(equation.charAt(i)))
                return false;
        }
        
        // a + b = c
        int plus = equation.indexOf('+');
        int equal = equation.indexOf('=');
        
        int a = Integer.parseInt(equation.substring(0, plus));
        int b = Integer.parseInt(equation.substring(plus + 1, equal));
        int c = Integer.parseInt(equation.substring(equal + 1));
        
        return a + b == c;
    }

}
