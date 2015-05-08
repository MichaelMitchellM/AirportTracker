package display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import javax.swing.JTextField;

import data.Airport;
import main.Main;

public class SearchBar extends JTextField{
	private static final long serialVersionUID = 1349361617500882651L;

	private String initial_text_ = "";
	
	private AirportsTable table_;
	
	private String cached_text_;
	private HashMap<Airport, Airport[]> cached_map_;
	
	public SearchBar(String initial_text, AirportsTable table){
		super(initial_text);
		
		initial_text_ = initial_text;
		
		table_ = table;
		
		cached_text_ = "";
		cached_map_  = null;
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(getText().equals(initial_text_))
					setText("");
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			
			/**
			 * Listens to changes in the search value
			 * Searches all the airports and finds the best matches
			 * Displays matches in table
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == 8){
					cached_map_ = null;
				}
				if(!cached_text_.equals(getText())){
					cached_text_ = getText();
					ArrayList<Airport> airports = new ArrayList<Airport>();
					ArrayList<Object[]> data = new ArrayList<Object[]>();
					BiConsumer<Airport, Airport[]> bc = (k, v) -> {
						if(
							k.code().toLowerCase().contains(getText().toLowerCase())
							|| k.loc().city().toLowerCase().contains(getText().toLowerCase())
							|| k.loc().country().toLowerCase().contains(getText().toLowerCase())
							)
						{
							if(k.code().equalsIgnoreCase(getText())){
								data.add(0, new Object[]{k.code(), k.loc().city(), k.loc().country()});
								airports.add(0, k);
							}
							else{
								data.add(new Object[]{k.code(), k.loc().city(), k.loc().country()});
								airports.add(k);	
							}
						}
					};
					if(cached_map_ == null)
						cached_map_ = Main.airport_graph.routes();
					cached_map_.forEach(bc);
					Object[][] data_arr = {{" ", "No Match", " "}};
					if(data.size() > 0){
						data_arr = new Object[data.size()][3];
						int index = 0;
						for(Object[] o: data) data_arr[index++] = o;
					}
					table_.SetData(data_arr, airports);
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(getText().equals(initial_text_)) setText("");
			}
		});
		
	}
	
}
