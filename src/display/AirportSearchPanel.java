package display;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AirportSearchPanel extends JPanel{
	private static final long serialVersionUID = -8217787729572303235L;
	
	private static Dimension dim = new Dimension(250, 300);
	
	private JScrollPane   scroll_pane_;
	private JPanel        table_panel_;
	private SearchBar     search_bar_;
	private AirportsTable table_;
	
	private SearchPanel search_panel_;
	
	private boolean start_table_;
	
	public AirportSearchPanel(SearchPanel search_panel, boolean start_table){
		setPreferredSize(dim);
		
		search_panel_ = search_panel;
		
		start_table_ = start_table;
		
		// table
		table_ = new AirportsTable(search_panel_, start_table_);
		
		// search bar
		String search_text = "";
		if(start_table_) search_text = "Starting Airport";
		else search_text = "Ending Airport";
		search_bar_ = new SearchBar(search_text, table_);
		search_bar_.setPreferredSize(new Dimension(dim.width, 25));
		
		// table panel
		table_panel_ = new JPanel();
		table_panel_.add(table_);
		
		// scroll pane
		scroll_pane_ = new JScrollPane(table_panel_);
		scroll_pane_.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER  );
		scroll_pane_.setVerticalScrollBarPolicy  (JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll_pane_.setPreferredSize(new Dimension(dim.width, dim.height - 35));
		
		add(search_bar_);
		add(scroll_pane_);
	}
	
}
