package display;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {

	private JFrame frame_;
	
	private JPanel top_bar_;
	
	private SearchPanel search_panel_;
	
	private AirportSearchPanel start_search_;
	private AirportSearchPanel end_search_;
	
	public Window(){
		Dimension screen_size;
		
		screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		// search panel
		search_panel_ = new SearchPanel();
		search_panel_.setPreferredSize(new Dimension(300, 300));
		
		// start search
		start_search_ = new AirportSearchPanel(search_panel_, true);
		
		// end search
		end_search_ = new AirportSearchPanel(search_panel_, false);
		
		// top bar
		top_bar_ = new JPanel();
		
		top_bar_.setLayout(new GridBagLayout());
		GridBagConstraints tb_c = new GridBagConstraints();
		
		
		tb_c.gridx = 0;
		tb_c.gridy = 0;
		
		top_bar_.add(start_search_, tb_c);
		
		tb_c.gridx = 1;
		tb_c.gridy = 0;
		
		tb_c.weightx = 1.0;
		tb_c.weighty = 1.0;
		
		top_bar_.add(end_search_,   tb_c);
		
		tb_c.gridx = 2;
		tb_c.gridy = 0;
		
		top_bar_.add(search_panel_, tb_c);
		
		// frame
		frame_ = new JFrame("Airport Map");
		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_.setMinimumSize(new Dimension(screen_size.width / 2, screen_size.height / 2));
		frame_.setLayout(new GridBagLayout());
		
		
		GridBagConstraints cnstr = new GridBagConstraints();
		
		cnstr.gridx = 0;
		cnstr.gridy = 0;
		
		cnstr.weightx = 1.0;
		cnstr.weighty = 0.03;
		
		frame_.add(top_bar_, cnstr);
		
		frame_.setVisible(true);
		
		
	}
	
}
