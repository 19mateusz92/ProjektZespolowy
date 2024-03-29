package com.pwr.QuestView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.pwr.DetailsView.DescribeView;
import com.pwr.Quest.QuestPoint;

public class MultipleChoiceQuestView extends QuestView implements DescribeView {

	private JPanel splitPane;
	private JTableHeader header;
	protected DefaultTableModel tableModel;
	private int colNum, rowNum;

	private QuestPoint quest;
	private JTable table;
	private JPanel tablePanel;
	private JScrollPane tableScrollPane;
	
	private final JButton btnUp = new JButton("W górę");
	private final JButton btnDown = new JButton("W dół");
	private final JButton btnAdd = new JButton("Dodaj");
	private final JButton btnDelete = new JButton("Usuń");

	public MultipleChoiceQuestView() {
		super();
		this.add(new JLabel("Multi"));

		// quest = QuestFactory.createQuest(QuestType.CHOICEQUEST);
		addAnswersTable();
	}

	private void addAnswersTable() {
		tablePanel = new JPanel();
		tableModel = new DefaultTableModel(new String[] { "Treść odpowiedzi", "Poprawna" }, 0){
	        Class[] types = {
	                String.class, Boolean.class
	            };
	            // making sure that it returns boolean.class.   
	            @Override
	            public Class getColumnClass(int columnIndex) {
	                return types[columnIndex];
	            }
	        };;

		tablePanel.setLayout(new BorderLayout());
		table = new JTable(tableModel);
		header = table.getTableHeader();

		table.getColumn("Poprawna").setMinWidth(80);
		table.getColumn("Poprawna").setMaxWidth(80);

		// Moze bedzie kolumna JButtonow

		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					rowNum = target.getSelectedRow();
					colNum = target.getSelectedColumn();
				}
			}
		});
		
		tableScrollPane = new JScrollPane(tablePanel);
		tableScrollPane.setBounds(23, 286, 518, 203);
		add(tableScrollPane);
		
		tablePanel.add(header, BorderLayout.NORTH);
		tablePanel.add(table, BorderLayout.CENTER);

		splitPane = new JPanel();
		tablePanel.add(splitPane, BorderLayout.SOUTH);

		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveUp();
			}
		});
		add(btnUp);
		btnUp.setBounds(200, 500, 89, 23);

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveDown();
			}
		});
		
		btnDown.setBounds(100, 500, 89, 23);
		add(btnDown);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempAnswer = "";
				createDialog(tempAnswer);
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableModel.getRowCount() != 0 && rowNum >= 0) {
					tableModel.removeRow(rowNum);
				}
			}
		});

		btnAdd.setBounds(551, 309, 89, 23);
		btnDelete.setBounds(551, 350, 89, 23);

		add(btnAdd);
		add(btnDelete);
	}

	private void createDialog(String answ) {
		final JDialog dialog = new JDialog();
		dialog.setSize(460, 250);
		dialog.setResizable(false);
		dialog.getContentPane().setLayout(null);

		dialog.setLocationRelativeTo(this);
		dialog.setLayout(null);

		JLabel lblAnswerLabel = new JLabel("Wprowadź odpowiedź");
		lblAnswerLabel.setBounds(25, 25, 134, 14);
		dialog.add(lblAnswerLabel);

		final JTextField tfAnswer = new JTextField();
		tfAnswer.setBounds(25, 49, 399, 30);
		dialog.add(tfAnswer);
		tfAnswer.setColumns(10);

		final JCheckBox chckbxTrueOrFalse = new JCheckBox(
				"Odpowiedź prawidłowa?");
		chckbxTrueOrFalse.setBounds(25, 97, 161, 23);
		dialog.add(chckbxTrueOrFalse);

		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(335, 127, 89, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempAnswer = tfAnswer.getText();
				boolean trueOrFalse = chckbxTrueOrFalse.isSelected();
				tableModel.addRow(new Object[] { tempAnswer, trueOrFalse });
				dialog.dispose();
			}
		});
		dialog.add(btnOk);
		dialog.setVisible(true);
	}

	private void moveUp() {
		int[] rows = table.getSelectedRows();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (model.getRowCount() >= 2) {
			model.moveRow(rows[0], rows[rows.length - 1], rows[0] - 1);
			table.setRowSelectionInterval(rows[0] - 1,
					rows[rows.length - 1] - 1);
		}
	}

	private void moveDown() {
		int[] rows = table.getSelectedRows();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (model.getRowCount() >= 2) {
			model.moveRow(rows[0], rows[rows.length - 1], rows[0] + 1);
			table.setRowSelectionInterval(rows[0] + 1,
					rows[rows.length - 1] + 1);
		}
	}

	public ArrayList getAnswers() {
		ArrayList collectedAnswers = new ArrayList();

		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			collectedAnswers.add(tableModel.getValueAt(i, 0));
		}
		return collectedAnswers;
	}

	public ArrayList getAnswersBooleans() {
		ArrayList collectedAnswers = new ArrayList();

		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			collectedAnswers.add(tableModel.getValueAt(i, 1));
		}
		return collectedAnswers;
	}

	@Override
	public String introduceYourself() {
		return "MultipleChoiceQuest";
	}

}
