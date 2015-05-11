package display;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Airport;
import main.Main;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = -1350778671147446762L;
	
	private static final Dimension dim = new Dimension(300, 500);
	
	private JLabel start_label_;
	private JLabel to_label_;
	private JLabel end_label_;
	
	private JButton search_button_;
	
	private JLabel spacer_;
	
	private JLabel distance_;
	private AirportsTable results_;
	
	public SearchPanel(){
		
		setPreferredSize(dim);
		
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
				if(Main.start_airport != null && Main.end_airport != null && !Main.start_airport.equals(Main.end_airport)){
					Airport[] route = Main.airport_graph.CalculateRoute(Main.start_airport, Main.end_airport);
					// distance
					float total_distance = 0.0f;
					int ap_index;
					Airport prev = Main.start_airport;
					
					Object[][] data = new Object[route.length + 1][];
					data[0] = new Object[3];
					data[0][0] = Main.start_airport.code();
					data[0][1] = Main.start_airport.loc().city();
					data[0][2] = Main.start_airport.loc().country();
					int index = 1;
					for(Airport ap : route){
						
						// find distance
						ap_index = 0;
						for(Airport apt : Main.airport_graph.routes().get(ap)){
							if(apt.equals(prev)){
								break;
							}
							++ap_index;
						}
						
						try{
							total_distance += Main.airport_graph.dists().get(ap)[ap_index];
						}
						catch(Exception er){
							
						}
						
						data[index] = new Object[3];
						data[index][0] = ap.code();
						data[index][1] = ap.loc().city();
						data[index][2] = ap.loc().country();
						++index;
						prev = ap;
					}
					
					distance_.setText(total_distance + " mi");
					results_.SetData(data, null);
				}
			}
		});
		
		spacer_ = new JLabel(" ");
		
		distance_ = new JLabel(" ");
		results_ = new AirportsTable();
		
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
		
		c.gridx = 0;
		c.gridy = 4;
		
		add(spacer_, c);
		
		c.gridx = 0;
		c.gridy = 5;
		
		add(distance_, c);
		
		c.gridx = 0;
		c.gridy = 6;
		
		add(results_, c);
		
	}
	
	public void SetStart(String text){
		start_label_.setText(text);
	}
	
	public void SetEnd(String text){
		end_label_.setText(text);
	}
	
}
