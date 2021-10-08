import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class View {
	
	public View() {
		
		
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();

		Model.Stoage_panel(panel, frame);
		Model.ReadFile();
		Model.create_view();
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Planner");
		frame.setLayout(new FlowLayout());
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new View();
	}
}

