public class SyntaxCheckerClient {

	public static void main(String[] args) {
   
		SyntaxChecker checker = new SyntaxChecker("expressions.txt", "symbols.txt");

		System.out.println(checker);
		System.out.println(checker.validate());
      
	}
}
