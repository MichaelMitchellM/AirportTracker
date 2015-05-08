package display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.Main;
import data.Airport;

public class AirportsTable extends JTable{
	private static final long serialVersionUID = 3127350893696122345L;

	private static Object[][] data = {{"", "", ""}};
	private static Object[] col_names = {"Airport Code", "Country", "City"};
	
	private SearchPanel search_panel_;
	
	private boolean start_table_;
	
	private ArrayList<Airport> available_airports_;
	
	public AirportsTable(SearchPanel search_panel, boolean start_table){
		super(data, col_names);
		
		search_panel_ = search_panel;
		
		start_table_ = start_table;
		
		available_airports_ = null;
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				int row = rowAtPoint(e.getPoint());
				if(available_airports_ != null){
					if(start_table_){
						Main.start_airport = available_airports_.get(row);
						search_panel_.SetStart("[ " + Main.start_airport.name() + " ] " + Main.start_airport.name());
					}
					else{
						Main.end_airport = available_airports_.get(row);
						search_panel_.SetEnd("[ " + Main.end_airport.code() + " ] " + Main.end_airport.name());
					}
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
	}
	
	public void SetData(Object[][] data, ArrayList<Airport> airports){
		available_airports_ = airports;
		TableModel model = new DefaultTableModel(data.length, 3);
		setModel(model);
		getColumnModel().getColumn(0).setPreferredWidth(30);
		getColumnModel().getColumn(1).setPreferredWidth(100);
		getColumnModel().getColumn(2).setPreferredWidth(100);
		int row_index = 0;
		for(Object[] o_arr : data){
			model.setValueAt(o_arr[0], row_index, 0);
			model.setValueAt(o_arr[1], row_index, 1);
			model.setValueAt(o_arr[2], row_index, 2);
			++row_index;
		}
	}
	
}
