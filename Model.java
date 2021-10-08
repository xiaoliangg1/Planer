import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Model {
	
	private static LocalDate date = LocalDate.now();
	private static LocalDate month = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
	private static JPanel panel;
	private static int space = 45;
	private static JTextField text;
	private static JTextArea textArea;
	private static JButton delete;
	private static int button_text;
	private static JComboBox<String> choose_color;
	private static HashMap<LocalDate, Integer> color = new HashMap<LocalDate, Integer>();
	private static HashMap<LocalDate, String> info = new HashMap<LocalDate, String>();
	
	static void Stoage_panel(JPanel Panel, JFrame Frame) {
		panel = Panel;
	}
	
	static void create_view() {
		
		String[] months = {"January", "February","March",  "April", "May", "June", 
				"July", "August", "September", "October", "November", "December"};
		String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		JButton last = new JButton("Last Month");
		last.addActionListener(new last());
		
		JButton next = new JButton("Next Month");
		next.addActionListener(new next());
		
		JLabel label = new JLabel("Today is " + date.toString());
		label.setPreferredSize(new Dimension(120, 30));
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label, c);
		
		c.gridx = 3;
		c.gridy = 0;
		JLabel month_year = new JLabel(months[month.getMonthValue() - 1] + " " + month.getYear());
		panel.add(month_year, c);
		
		c.gridy = 1;
		for (int i = 0; i < 7; i++) {
			c.gridx = i;
			JLabel weekday = new JLabel(week[i]);
			weekday.setPreferredSize(new Dimension(120, 30));
			panel.add(weekday, c);
		}
		
		c.gridx = Model.getDayNumberOld(java.sql.Date.valueOf(month)) - 1;
		c.gridy = 2;
		
		space -= Model.getDayNumberOld(java.sql.Date.valueOf(month)) - 1;
		for (int i = 1; i <= month.lengthOfMonth(); i++) {
			Model.AddButton(panel, c, i);
			c.gridx++;
			if (c.gridx >= 7) {
				c.gridx = 0;
				c.gridy++;
			}
		}
		
		c.gridx = 0;
		while (c.gridy <= 7) {
			c.gridy++;
			JLabel emptySpace = new JLabel(" ");
			emptySpace.setPreferredSize(new Dimension(120, 30));
			panel.add(emptySpace, c);
			
		}
		
		c.gridy = 8;
		panel.add(last, c);
		c.gridx = 6;
		panel.add(next, c);
		
		
//		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		panel.setSize(700, 200);
//		panel.setLayout(new GridLayout(10, 7, 1, 1));
//		panel.add(label);
//		Model.AddSpace(panel, 2);
//		panel.add(new JLabel(months[month.getMonthValue() - 1] + " " + month.getYear()));
//		Model.AddSpace(panel, 3);
//		Model.AddLabel(panel, week);
//		Model.AddSpace(panel, Model.getDayNumberOld(java.sql.Date.valueOf(month)) - 1);
//		space -= Model.getDayNumberOld(java.sql.Date.valueOf(month)) - 1;
//		Model.AddButton(panel, month.lengthOfMonth());
//		LocalDate end_month = LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());
//		Model.AddSpace(panel, space - Model.getDayNumberOld(java.sql.Date.valueOf(end_month)) - 2);
//		panel.add(last);
//		Model.AddSpace(panel, 5);
//		panel.add(next);
	}
	
	static void edit() {
		
		int color_index = 0;
		
		LocalDate date = LocalDate.of(month.getYear(), month.getMonthValue(), button_text);
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		
		textArea = new JTextArea(10, 40);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
		
		if (info.get(date) != null) {
			textArea.append(info.get(date));
			color_index = color.get(date);
		}
		
		JButton back = new JButton("Back");
		back.addActionListener(new back());
		c.ipadx = 20;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(back, c);
		
		JButton add = new JButton("Add");
		add.addActionListener(new add());
		c.gridx = 1;
		c.gridy = 0;
		panel.add(add, c);
		
		delete = new JButton("Delete");
		delete.addActionListener(new delete());
		c.gridx = 2;
		c.gridy = 0;
		panel.add(delete, c);
		
		JLabel color_text = new JLabel("Pick your color here:");
	    color_text.setVisible(true);
	    c.gridx = 0;
		c.gridy = 1;
		panel.add(color_text, c);
		
		String[] choices = { "RED","CYAN", "ORANGE","PINK","MAGENTA","Light Gray"};
	    choose_color = new JComboBox<String>(choices);
	    choose_color.setVisible(true);
	    choose_color.setSelectedIndex(color_index);
	    c.gridwidth = 2;
	    c.gridx = 1;
		c.gridy = 1;
		panel.add(choose_color, c);
		
		JLabel label = new JLabel("Input here:");
		c.gridx = 0;
		c.gridy = 2;
		panel.add(label, c);
		
		text = new JTextField(20);
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 2;
		panel.add(text, c);
		c.gridwidth = 1;
		
		c.gridwidth = 3;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(scrollPane, c);
	}
	
	static void ReadFile() {
		
		BufferedReader br = null;
		
		try{
	            //create file object
	            File f = new File("Your_Schedule.txt");
	            
	            if(!f.exists()) { 
	                return;
	            }
	            
	            //create BufferedReader object from the File
	            br = new BufferedReader( new FileReader(f) );
	            
	            String all_line = "";
	            String line = null;
	            
	            //read file line by line
	            while ((line = br.readLine()) != null ){
	            	if (!line.isEmpty()) {
	            		all_line += line + "\n";
	            	}
	            }
	            
	            all_line = all_line.trim();
                //split the line by :
				String[] parts = all_line.split("~");
				
				for (String temp : parts) {
					String[] inf = temp.split("`");
					
					if (!inf[0].isEmpty()) {
						//first part is Date, second is message
						int Color = 0;
		                LocalDate Date = LocalDate.parse(inf[0].trim());
		                String message = inf[1].trim();
		                if (inf.length > 2) {
			                Color = Integer.parseInt(inf[2].trim());
		                }
		                //put Date, Message in HashMap if they are not empty
		                if( !Date.equals("") && !message.equals("") )
		                    info.put(Date, message + "\n");
		                	color.put(Date, Color);
					}
		        }

	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{

	            //Always close the BufferedReader
	            if(br != null){
	                try { 
	                    br.close(); 
	                }catch(Exception e){};
	            }
	        } 
		}
	
	static void AddButton(JPanel panel, GridBagConstraints c, int d) {
		
		LocalDate date = LocalDate.of(month.getYear(), month.getMonthValue(), d);
		
		JButton button = new JButton(String.valueOf(d));
		button.setPreferredSize(new Dimension(120, 30));
		
		if (info.get(date) != null) {
			if (color.get(date) == 0) {
				button.setBackground(Color.red);
			}else if(color.get(date) == 1) {
				button.setBackground(Color.cyan);
			}else if(color.get(date) == 2) {
				button.setBackground(Color.orange);
			}else if(color.get(date) == 3) {
				button.setBackground(Color.pink);
			}else if(color.get(date) == 4){
				button.setBackground(Color.magenta);
			}else if(color.get(date) == 5){
				button.setBackground(Color.lightGray);
			}else{
				button.setBackground(Color.white);
			}
		}
		else if (info.get(date.plusDays(1)) != null || info.get(date.plusDays(2)) != null){
			button.setBackground(Color.yellow);
		}
		else {
			button.setBackground(Color.green);
		}
		
		button.addActionListener(new edit_it());
		panel.add(button, c);
		space -= 1;
	}
	
	static void AddSpace(JPanel panel, int num) {
		for (int i = 1; i <= num; i++) {
			JLabel label = new JLabel("");
			panel.add(label);
		}
	}
	
	static void AddLabel(JPanel panel, String[] array) {
		for (int i = 0; i < array.length; i++) {
			JLabel label = new JLabel(array[i]);
			panel.add(label);
		}
	}
	
	static void add_info(int year, int month, int day, String message, int Color) {
		LocalDate date = LocalDate.of(year, month, day);
		info.put(date, message);
		info.values().removeAll(Collections.singleton(""));
		color.put(date, Color);
	}
	
	public static int getDayNumberOld(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	static class last implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (month.getMonthValue() != 1) {
				month = LocalDate.of(month.getYear(), month.getMonthValue() - 1, 1);
			}
			else {
				month = LocalDate.of(month.getYear() - 1, 12, 1);
			}
			panel.removeAll();
			create_view();
			panel.revalidate();
			panel.repaint();
		}
	}
	
	static class next implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (month.getMonthValue() != 12) {
				month = LocalDate.of(month.getYear(), month.getMonthValue() + 1, 1);
			}
			else {
				month = LocalDate.of(month.getYear() + 1, 1, 1);
			}
			panel.removeAll();
			create_view();
			panel.revalidate();
			panel.repaint();
		}
	}
	
	static class edit_it implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			button_text = Integer.parseInt(((AbstractButton) e.getSource()).getText());
			panel.removeAll();
			edit();
			panel.revalidate();
			panel.repaint();
		}
	}
	
	static class back implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String[] choices = { "RED","CYAN", "ORANGE","PINK","MAGENTA","Light Gray"};
			String message = textArea.getText();
			int Color = 0;
			for (int i = 0; i < choices.length; i++) {
				if (choices[i] == choose_color.getSelectedItem()) {
					Color = i;
				}
			}
			add_info(month.getYear(), month.getMonthValue(), button_text, message, Color);
			
			BufferedWriter bf = null;
			
			try{
				File f = new File("Your_Schedule.txt");
				
	            //create new BufferedWriter for the output file
	            bf = new BufferedWriter( new FileWriter(f) );
	 
	            //iterate map entries
	            for(Entry<LocalDate, String> entry : info.entrySet()){
	                
	                //put key and value separated by a colon
	                bf.write( entry.getKey() + "`" + entry.getValue() + "`" + color.get(entry.getKey()) + "~");
	            }
	            bf.flush();
	        }
			catch(IOException e1){
	            e1.printStackTrace();
			}finally{
	            
	            try{
	                //always close the writer
	                bf.close();
	            }catch(Exception e1){}
	        }
			
			panel.removeAll();
			create_view();
			panel.revalidate();
			panel.repaint();
		}
	}

	static class add implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String assgiment = text.getText();
			if (!text.getText().isEmpty()) {
				textArea.append(assgiment + "\n");
			}
		}
	}
	
	static class delete implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
		    if (arg0.getSource() == delete){
		        String selected = textArea.getSelectedText() + "\n";
		        if(textArea.getText().contains(selected)){
		        	textArea.setText(textArea.getText().replaceFirst(selected, ""));
		        }
		    }
		}
	}
	
	

}



