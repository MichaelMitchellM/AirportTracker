package display;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = -1350778671147446762L;
	
	private JLabel start_label_;
	private JLabel to_label_;
	private JLabel end_label_;
	
	JButton search_button_;
	
	public SearchPanel(){
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		start_label_ = new JLabel(" ");
		to_label_    = new JLabel("- to -");
		end_label_   = new JLabel(" ");

		search_button_ = new JButton("Calculate Trip");
		search_button_.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO CHECK NULL;
				Main.airport_graph.CalculateRoute(Main.start_airport, Main.end_airport);
			}
		});
		
		c.gridx = 0;
		c.gridy = 0;
		
		add(start_label_, c);
		
		c.gridx = 0;
		c.gridy = 1;
		
		add(to_label_, c);
		
		c.gridx = 0;
		c.gridy = 2;
		
		add(end_label_ , c);
		
		c.gridx = 0;
		c.gridy = 3;
		
		add(search_button_, c);
		
	}
	
	public void SetStart(String text){
		start_label_.setText(text);
	}
	
	public void SetEnd(String text){
		end_label_.setText(text);
	}
	
}
