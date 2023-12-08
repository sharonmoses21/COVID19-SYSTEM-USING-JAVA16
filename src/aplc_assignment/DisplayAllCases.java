package aplc_assignment;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang3.function.TriFunction;

public class DisplayAllCases {

	private JFrame frame;
	private JTable covidTable;
	private JButton btnReset;
	private JLabel lblConfirmedCases;
	private JLabel lblDeathCases;
	private JLabel lblRecoveredCases;
	private JTextField txtFieldCountry, txtFieldState;

//	public static List<Covid19Cases> confirmedCases;
//	public static List<Covid19Cases> deathCases;
//	public static List<Covid19Cases> recoveredCases;
	
//	TO READ ALL THE DATA FROM CSV FILE
	Covid19Reader covid19Reader = new Covid19Reader();

	public List<Covid19Cases> confirmedCases = covid19Reader.readDataLineByLine.apply(
			"C:\\Users\\Bryan Kee Jia Xian\\eclipse-workspace\\APLC_Assignment\\dataset\\time_series_covid19_confirmed_global.csv");

	public List<Covid19Cases> deathCases = covid19Reader.readDataLineByLine.apply(
			"C:\\Users\\Bryan Kee Jia Xian\\eclipse-workspace\\APLC_Assignment\\dataset\\time_series_covid19_deaths_global.csv");

	public List<Covid19Cases> recoveredCases = covid19Reader.readDataLineByLine.apply(
			"C:\\Users\\Bryan Kee Jia Xian\\eclipse-workspace\\APLC_Assignment\\dataset\\time_series_covid19_recovered_global.csv");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayAllCases window = new DisplayAllCases();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DisplayAllCases() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 801, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 171, 738, 279);
		frame.getContentPane().add(scrollPane);

		Object[][] covidData = showAllCases.apply(confirmedCases, deathCases, recoveredCases);

		covidTable = new JTable();
		covidTable.setModel(new DefaultTableModel(covidData,
				new String[] { "State", "Country", "Confirmed Cases", "Death Cases", "Recovered Cases" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, Integer.class, Integer.class,
					Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(covidTable);

		JLabel lblNewLabel = new JLabel("Country:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(24, 38, 73, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("State:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(24, 63, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);

		txtFieldCountry = new JTextField();
		txtFieldCountry.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String query = txtFieldCountry.getText();
				filterCovidData(query);
			}
		});
		txtFieldCountry.setBounds(107, 36, 155, 20);
		frame.getContentPane().add(txtFieldCountry);
		txtFieldCountry.setColumns(10);

		txtFieldState = new JTextField();
		txtFieldState.setBounds(107, 61, 155, 20);
		frame.getContentPane().add(txtFieldState);
		txtFieldState.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Week:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(294, 88, 63, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Month:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(294, 63, 63, 14);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel("Year:");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(294, 39, 63, 14);
		frame.getContentPane().add(lblNewLabel_3_1);

		JComboBox yearComboBox = new JComboBox();
		yearComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			}
		});
		yearComboBox.setModel(new DefaultComboBoxModel(new String[] { "-Choose Year-", "2020", "2021" }));
		yearComboBox.setBounds(367, 35, 125, 22);
		frame.getContentPane().add(yearComboBox);

		JComboBox weekComboBox = new JComboBox();
		weekComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (weekComboBox.getSelectedIndex() == 1) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 1, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 1, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 2) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 2, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 2, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 3) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 3, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 3, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 4) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 4, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 4, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 5) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 5, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 5, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 6) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 6, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 6, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 7) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 7, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 7, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 8) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 8, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 8, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 9) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 9, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 9, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 10) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 10, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 10, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 11) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 11, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 11, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 12) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 12, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 12, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 13) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 13, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 13, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 14) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 14, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 14, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 15) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 15, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 15, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 16) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 16, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 16, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 17) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 17, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 17, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 18) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 18, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 18, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 19) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 19, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 19, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 20) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 20, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 20, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 21) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 21, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 21, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 22) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 22, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 22, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 23) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 23, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 23, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 24) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 24, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 24, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 25) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 25, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 25, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 26) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 26, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 26, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 27) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 27, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 27, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 28) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 28, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 28, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 29) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 29, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 29, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 30) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 30, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 30, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 31) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 31, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 31, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 32) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 32, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 32, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 33) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 33, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 33, 2021);
						}
					} else if (weekComboBox.getSelectedIndex() == 34) {
						if (yearComboBox.getSelectedItem() == "2020") {
							weeklyCovidCases.accept(recoveredCases, 34, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							weeklyCovidCases.accept(recoveredCases, 34, 2021);
						}
					}
				}

			}
		});
		weekComboBox.setModel(new DefaultComboBoxModel(new String[] { "-Choose Week-", "Week 1", "Week 2", "Week 3",
				"Week 4", "Week 5", "Week 6", "Week 7", "Week 8", "Week 9", "Week 10", "Week 11", "Week 12", "Week 13",
				"Week 14", "Week 15", "Week 16", "Week 17", "Week 18", "Week 19", "Week 20", "Week 21", "Week 22",
				"Week 23", "Week 24", "Week 25", "Week 26", "Week 27", "Week 28", "Week 29", "Week 30", "Week 32",
				"Week 33", "Week 34", "Week 35", "Week 36", "Week 37", "Week 38", "Week 39", "Week 40", "Week 41",
				"Week 42", "Week 43", "Week 44", "Week 45", "Week 46", "Week 47", "Week 48", "Week 49", "Week 50",
				"Week 51", "Week 52", "Week 53" }));
		weekComboBox.setBounds(367, 85, 125, 22);
		frame.getContentPane().add(weekComboBox);

		JComboBox monthComboBox = new JComboBox();

		monthComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (monthComboBox.getSelectedIndex() == 1) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 1, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 1, 2021);
						} else {
							JOptionPane.showMessageDialog(weekComboBox, "Please select a year, Thank you.");
						}
					} else if (monthComboBox.getSelectedIndex() == 2) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 2, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 2, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 3) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 3, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 3, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 4) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 4, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 4, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 5) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 5, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 5, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 6) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 6, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 6, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 7) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 7, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 7, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 8) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 8, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 8, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 9) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 9, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 9, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 10) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 10, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 10, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 11) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 11, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 11, 2021);
						}
					} else if (monthComboBox.getSelectedIndex() == 12) {
						if (yearComboBox.getSelectedItem() == "2020") {
							monthlyCovidCases.accept(recoveredCases, 12, 2020);
						} else if (yearComboBox.getSelectedItem() == "2021") {
							monthlyCovidCases.accept(recoveredCases, 12, 2021);
						}
					}
				}
			}
		});
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] { "-Choose Month-", "January", "February", "March",
				"April", "May", "June", "July", "August", "September", "October", "November", "December" }));
		monthComboBox.setBounds(367, 60, 125, 22);
		frame.getContentPane().add(monthComboBox);

		JPanel panel = new JPanel();
		panel.setBounds(514, 11, 248, 149);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Total Confirmed Cases:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(10, 11, 163, 14);
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_4_1 = new JLabel("Total Death Cases:");
		lblNewLabel_4_1.setBounds(10, 61, 163, 14);
		panel.add(lblNewLabel_4_1);

		JLabel lblNewLabel_4_2 = new JLabel("Total Recovered Cases:");
		lblNewLabel_4_2.setBounds(10, 105, 163, 14);
		panel.add(lblNewLabel_4_2);

		lblConfirmedCases = new JLabel("New label");
		lblConfirmedCases.setForeground(Color.BLUE);
		lblConfirmedCases.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblConfirmedCases.setBounds(10, 36, 163, 14);
		panel.add(lblConfirmedCases);

		lblDeathCases = new JLabel("New label");
		lblDeathCases.setForeground(Color.RED);
		lblDeathCases.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDeathCases.setBounds(10, 83, 163, 14);
		panel.add(lblDeathCases);

		lblRecoveredCases = new JLabel("New label");
		lblRecoveredCases.setForeground(new Color(50, 205, 50));
		lblRecoveredCases.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRecoveredCases.setBounds(10, 124, 163, 14);
		panel.add(lblRecoveredCases);

		JButton button = new JButton("New button");
		button.setBounds(245, 110, 37, -38);
		frame.getContentPane().add(button);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(24, 99, 102, 34);
		frame.getContentPane().add(btnNewButton);

		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				DisplayAllCases displayAllCases = new DisplayAllCases();
				displayAllCases.frame.setVisible(true);
			}
		});
		btnReset.setBounds(160, 99, 102, 34);
		frame.getContentPane().add(btnReset);
		
//		SHOW ALL THE COVID CASES
		totalCovidCases.accept(confirmedCases, deathCases, recoveredCases);

//		TO SORT ALL THE COVID DATA
		sortCovidData();
	}

//	1) Display the total confirmed Covid-19 cases according to country
	TriFunction<List<Covid19Cases>, List<Covid19Cases>, List<Covid19Cases>, Object[][]> showAllCases = (confirmedCases,
			deathCases, recoveredCases) -> {
//		GET THE TOTAL CONFIRMED CASES 
		List<Integer> totalConfirmedCases = confirmedCases.stream().map(row -> row.getDailyCases()).map(cases -> {
			return cases.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());

//		GET THE TOTAL DEATH CASES 
		List<Integer> totalDeathCases = deathCases.stream().map(row -> row.getDailyCases()).map(cases -> {
			return cases.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());

//		GET THE TOTAL RECOVERED CASES
		List<Integer> totalRecoveredCases = recoveredCases.stream().map(row -> row.getDailyCases()).map(cases -> {
			return cases.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList());

//		AS THE RECOVERED CASES OF CANADA IS ONLY HAVING ONE ROW COMPARED TO THE OTHER TYPES OF CASES,
//		SEVERAL ROWS WITH NULL ARE ADDED INTO THE CANADA ROW
		IntStream.range(40, 55).forEach(row -> {
			totalRecoveredCases.add(row, 0);
		});
		
		Object[][] casesData = new Object[confirmedCases.size()][5];

		IntStream.range(0, confirmedCases.size()).forEach(row -> {
			Object[] caseData = { confirmedCases.get(row).getState().orElse("N/A"),
					confirmedCases.get(row).getCountry(), totalConfirmedCases.get(row), totalDeathCases.get(row),
					totalRecoveredCases.get(row) };
			casesData[row] = caseData;
		});
		return casesData;
	};

//	SORT DATA
//	Q3 - Find the highest/lowest death and recovered Covid-19 cases as per country
	private void sortCovidData() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(
				(DefaultTableModel) covidTable.getModel());
		covidTable.setRowSorter(sorter);
	}
	
	private void filterCovidData(String query) {
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) covidTable.getModel());
		covidTable.setRowSorter(tr);
		
		tr.setRowFilter(RowFilter.regexFilter(query));
	}

//	SET THE TOTAL AMOUNT OF CASES
	TriConsumer<List<Covid19Cases>, List<Covid19Cases>, List<Covid19Cases>> totalCovidCases = (confirmedCases, deathCases, recoveredCases) -> {
		int totalConfirmedCases = confirmedCases.stream().map(row -> row.getDailyCases()).map(a -> {
			return a.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList()).stream().reduce(0, Integer::sum);

		int totalDeathCases = deathCases.stream().map(row -> row.getDailyCases()).map(a -> {
			return a.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList()).stream().reduce(0, Integer::sum);
		
		int totalRecoveredCases = recoveredCases.stream().map(row -> row.getDailyCases()).map(a -> {
			return a.stream().map(row -> row.getCasesCount()).collect(Collectors.toList()).stream().reduce(0,
					Integer::sum);
		}).collect(Collectors.toList()).stream().reduce(0, Integer::sum);
		
		lblConfirmedCases.setText(String.valueOf(totalConfirmedCases));
		lblDeathCases.setText(String.valueOf(totalDeathCases));
		lblRecoveredCases.setText(String.valueOf(totalRecoveredCases));
	};

//	Q3 - Compute the sum of confirmed cases by month for each country
	TriConsumer<List<Covid19Cases>, Integer, Integer> monthlyCovidCases = (recoveredCases, month, year) -> {

		DefaultTableModel dm = (DefaultTableModel) covidTable.getModel();
		dm.setRowCount(0);

		if (recoveredCases.size() < 279) {
			IntStream.range(40, 55).forEach(row -> {
				recoveredCases.add(row, null);
			});
		}

		IntStream.range(0, confirmedCases.size()).forEach(covidCase -> {

			String province = confirmedCases.get(covidCase).getState().orElse("N/A");
			String country = confirmedCases.get(covidCase).getCountry();

//			GET MONTHLY CONFIRMED CASES
			int monthlyCC = confirmedCases.get(covidCase).getDailyCases().stream()
					.filter(a -> a.getCasesDate().getYear() == year)
					.filter(a -> a.getCasesDate().getMonthValue() == month).map(row -> row.getCasesCount())
					.collect(Collectors.toList()).stream().reduce(0, Integer::sum);

//			GET MONTHLY DEATH CASES
			int monthlyDC = deathCases.get(covidCase).getDailyCases().stream()
					.filter(a -> a.getCasesDate().getYear() == year)
					.filter(a -> a.getCasesDate().getMonthValue() == month).map(row -> row.getCasesCount())
					.collect(Collectors.toList()).stream().reduce(0, Integer::sum);

//			GET MONTHLY RECOVERED CASES
			int monthlyRC;
			if (covidCase >= 40 && covidCase < 55) {
				monthlyRC = 0;
			} else {
				monthlyRC = recoveredCases.get(covidCase).getDailyCases().stream()
						.filter(a -> a.getCasesDate().getYear() == year)
						.filter(a -> a.getCasesDate().getMonthValue() == month).map(row -> row.getCasesCount())
						.collect(Collectors.toList()).stream().reduce(0, Integer::sum);
			}

			Object[] newInstance = { province, country, monthlyCC, monthlyDC, monthlyRC };
			dm.addRow(newInstance);
		});
		dm.fireTableDataChanged();
	};

//	Q3 - Compute the sum of confirmed cases by week for each country
	TriConsumer<List<Covid19Cases>, Integer, Integer> weeklyCovidCases = (recoveredCases, week, year) -> {
		
		DefaultTableModel dm=(DefaultTableModel)covidTable.getModel();
		dm.setRowCount(0);
		
		if (recoveredCases.size() < 279) {
			IntStream.range(40, 55).forEach(row -> {
				recoveredCases.add(row, null);
			});
		}
		
		for(int covidCase = 0; covidCase < confirmedCases.size(); covidCase++){
			
			String province = confirmedCases.get(covidCase).getState().orElse("N/A");
			String country = confirmedCases.get(covidCase).getCountry();
			
			List<CasesDate> weeklyConfirmedCasesDate = confirmedCases.get(covidCase).getDailyCases();
			
			Map<Integer,Integer>weeklyConfirmedCases=weeklyConfirmedCasesDate.stream().filter(a->a.getCasesDate().getYear()==year)
					.collect(Collectors.groupingBy( a -> {
						TemporalField woy=WeekFields.of(Locale.UK).weekOfWeekBasedYear();
						int weekNumber=a.getCasesDate().get(woy);
						return weekNumber;
					},Collectors.mapping(a -> a.getCasesCount(), Collectors.reducing(0, Integer::sum))));
			
			List<CasesDate> weeklyDeathCasesDate = deathCases.get(covidCase).getDailyCases();

			Map<Integer,Integer> weeklyDeathCases = weeklyDeathCasesDate.stream().filter( a -> a.getCasesDate().getYear()==year)
					.collect(Collectors.groupingBy(a -> {
						TemporalField woy = WeekFields.of(Locale.UK).weekOfWeekBasedYear();
						int weekNumber=a.getCasesDate().get(woy);
						return weekNumber;
					},Collectors.mapping(a->a.getCasesCount(),Collectors.reducing(0,Integer::sum))));
			
			int wcc = 0;
			int wdc= 0;
			
			try { 
				wcc = weeklyConfirmedCases.get(week);
				wdc=weeklyDeathCases.get(week);
			} catch(Exception e) {
//				e.printStackTrace();
				JOptionPane.showMessageDialog(btnReset, "Covid cases of week " + week + " is not available, please select again.");
				break;
			}
			
			int wrc;
			if(covidCase >= 40 && covidCase < 55) {
				wrc = 0;
			} else
			{
				List<CasesDate> weeklyRecoveredCasesDate = recoveredCases.get(covidCase).getDailyCases();

				Map<Integer, Integer> weeklyRecoveredCases = weeklyRecoveredCasesDate.stream()
						.filter(a -> a.getCasesDate().getYear() == year).collect(Collectors.groupingBy(a -> {
							TemporalField woy = WeekFields.of(Locale.UK).weekOfWeekBasedYear();
							int weekNumber = a.getCasesDate().get(woy);
							return weekNumber;
						}, Collectors.mapping(a -> a.getCasesCount(), Collectors.reducing(0, Integer::sum))));

				wrc = weeklyRecoveredCases.get(week);

				Object[] newInstance = { province, country, wcc, wdc, wrc };
				dm.addRow(newInstance);
			}
		}
		dm.fireTableDataChanged();
	};
}