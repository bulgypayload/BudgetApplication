package budgetApp;
import java.io.IOException;
import java.awt.Toolkit;
import javax.swing.JFrame;
/**
 * Main method
 * @author bulgyPayload
 *
 */
public class MyBudget{
	
	public static void main(String[] args)throws ClassNotFoundException, IOException {
		JFrame frame = new BudgetGUI();		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
		frame.setTitle("IdealBudget");
		frame.setSize(900, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);				
	}//end main
}
