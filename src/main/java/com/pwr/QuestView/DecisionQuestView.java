package com.pwr.QuestView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;


import com.pwr.DetailsView.DescribeView;
import com.pwr.MainView.ProjectMainView;
import com.pwr.Quest.Campaign;
import com.pwr.Quest.QuestPoint;

public class DecisionQuestView extends QuestView implements DescribeView {

	private QuestPoint quest;
	private JTable table;
	private JPanel tablePanel;
	private JPanel splitPane;
	private JTableHeader header;
	private JComboBox comboBox;
	protected DefaultTableModel tableModel;
	private int colNum, rowNum;

	private JScrollPane tableScrollPane;

	private final JButton btnAdd = new JButton("Dodaj");
	private JButton btnDel = new JButton("Usuń");

	public DecisionQuestView() {
		super();

		this.add(new JLabel("Decision"));
		// quest = QuestFactory.createQuest(QuestType.DECISIONQUEST);
		addAnswersTable();
	}

	private void addAnswersTable() {
		tablePanel = new JPanel();
		tableModel = new DefaultTableModel(new String[] { "Treść odpowiedzi","ID"}, 0);
		
		tablePanel.setBounds(23, 286, 518, 203);
		tablePanel.setLayout(new BorderLayout());
		table = new JTable(tableModel);
		header = table.getTableHeader();
		table.getColumn("ID").setPreferredWidth(0);
		table.getColumn("ID").setMinWidth(0);
		table.getColumn("ID").setWidth(0);
		table.getColumn("ID").setMaxWidth(0);
		

		setUpComboBoxColumn();

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

		btnAdd.setBounds(551, 309, 89, 23);
		btnDel.setBounds(551, 350, 89, 23);

		add(btnAdd);
		add(btnDel);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempAnswer = "";
				createDialog(tempAnswer);
			}
		});

		btnDel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableModel.getRowCount() != 0 && rowNum >= 0) {
					tableModel.removeRow(rowNum);
				}
			}
		});
	}

	private void setUpComboBoxColumn() {
		createComboBox();
		table.getColumnModel().getColumn(1)
				.setCellEditor(new DefaultCellEditor(comboBox));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
	}

	private void createComboBox() {
		comboBox = new JComboBox();
		if (ProjectMainView.getCampaign().getQuizes().size()!=0) {
			for (QuestPoint q : ProjectMainView.getCampaign().getQuizes()) {
				comboBox.addItem(Integer.toString(q.getId()));
			}
			table.getColumnModel().getColumn(1);
		}
		comboBox.setVisible(false);
	}

	private void createDialog(String answ) {
		final JDialog dialog = new JDialog();
		dialog.setSize(500, 250);
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

		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(335, 137, 89, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempAnswer = tfAnswer.getText();
				tableModel.addRow(new Object[] { tempAnswer, "",
						new JComboBox() });
				dialog.dispose();
			}
		});
		dialog.add(btnOk);
		dialog.setVisible(true);
	}

	@Override
	public String introduceYourself() {
		return "DecisionQuest";
	}
	
	public ArrayList<String> getAnswers() {
		ArrayList collectedAnswers = new ArrayList();

		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			collectedAnswers.add(tableModel.getValueAt(i, 0));
		}
		return collectedAnswers;
	}

	public ArrayList<String> getGoToList() {
		ArrayList collectedAnswers = new ArrayList();

		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			collectedAnswers.add(tableModel.getValueAt(i, 1));
		}
		return collectedAnswers;
	}
}
