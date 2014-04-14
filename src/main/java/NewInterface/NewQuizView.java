package NewInterface;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import Editor.ZipPacker;
import Quest.Campaign;
import Quest.QuestFactory;
import Quest.QuestPoint;
import Quest.QuestType;
import Quest.TextQuest;

public class NewQuizView extends JFrame {

	// GUI Interface vars
	private JPanel leftSidePanel;
	private JPanel rightSidePanel;
	private JSplitPane splitPane;
	private JScrollPane leftScroll;
	private JScrollPane rightScroll;

	private JButton btnSafeQuiz;

	private JLabel lblTimeout;
	private JLabel lblType;

	private static final int panelWidth = 800;
	private static final int panelHeight = 800;

	private static final int windowWidth = 1000;
	private static final int windowHeight = 500;

	private JTextField timeoutField;
	private JTextField tfQuizName;
	private QuestView selectedCard;

	// Quest vars
	private Campaign campaignRef;

	public NewQuizView(Campaign campaign) {
		super();
		campaignRef = campaign;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		setSize(windowWidth, windowHeight);
		leftSidePanel = new JPanel();
		leftSidePanel.setPreferredSize(new Dimension(320, panelHeight));
		rightSidePanel = new JPanel(new CardLayout());
		rightSidePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

		leftScroll = new JScrollPane(leftSidePanel);
		rightScroll = new JScrollPane(rightSidePanel);
		rightScroll
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll,
				rightScroll);

		leftSidePanel.setLayout(null);

		createLeftSidePanel();

		splitPane.setResizeWeight(0.3);
		splitPane.setBounds(10, 11, 764, 418);
		getContentPane().add(splitPane);
		setVisible(true);
	}

	private void createLeftSidePanel() {

		JLabel lblTitle = new JLabel("Tytu\u0142");
		lblTitle.setBounds(10, 42, 46, 14);
		leftSidePanel.add(lblTitle);

		String[] quizTypes = { "Zagadka terenowa", "Zagadka tekstowa",
				"Zagadka wielokrotnego wyboru", "Zagadka uporządkowania",
				"Zagadka zasięgu" };

		final JComboBox questTypeCombo = new JComboBox(quizTypes);
		questTypeCombo.setBounds(76, 112, 154, 27);
		leftSidePanel.add(questTypeCombo);

		lblType = new JLabel("Typ");
		lblType.setBounds(10, 118, 46, 14);
		leftSidePanel.add(lblType);

		JLabel lblOpcjeQuizu = new JLabel("Opcje quizu");
		lblOpcjeQuizu.setBounds(10, 11, 126, 14);
		leftSidePanel.add(lblOpcjeQuizu);
		
		tfQuizName = new JTextField();
		tfQuizName.setBounds(76, 36, 154, 27);
		leftSidePanel.add(tfQuizName);
		tfQuizName.setColumns(10);
		
		timeoutField = new JTextField("0");
		timeoutField.setBounds(76, 74, 154, 27);
		leftSidePanel.add(timeoutField);
		timeoutField.setColumns(10);

		lblTimeout = new JLabel("Timeout");
		lblTimeout.setBounds(10, 80, 46, 14);
		leftSidePanel.add(lblTimeout);
		
		btnSafeQuiz = new JButton("Zapisz quiz");
		btnSafeQuiz.setBounds(0, 239, 320, 23);
		leftSidePanel.add(btnSafeQuiz);
		
		btnSafeQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
                                                TextQuestView textQuestView=null;
						QuestPoint newQuest = QuestFactory.createQuest(QuestType.TEXTQUEST);
						for (Component component : rightSidePanel.getComponents())
						{
							if (component.isVisible() == true) {
                                                            textQuestView = (TextQuestView) component;
						        selectedCard = (QuestView) component;
							}
						}
						
                                                TextQuest newTextQuest = (TextQuest) newQuest;
						newTextQuest.getPicturePaths().addAll(rewriteJListToArrayList(selectedCard.pics));
						newTextQuest.getSoundPaths().addAll(rewriteJListToArrayList(selectedCard.sounds));;
                                                newTextQuest.setQuestDescription(selectedCard.paragraphList);
						newTextQuest.setQuestName(tfQuizName.getText());
						newTextQuest.setQuestTimeout(Integer.parseInt(timeoutField.getText()));
                                                newTextQuest.setPoints(Integer.parseInt(selectedCard.points.getText()));
                                                newTextQuest.setGoTo(textQuestView.textGoTo.getText());
                                                newTextQuest.setPostNote(selectedCard.postNote.getText());
                                                newTextQuest.setPreNote(selectedCard.preNote.getText());
                                                newTextQuest.setDate(selectedCard.date.getText());
                                                newTextQuest.setWrong(selectedCard.wrong.getText());
                                                newTextQuest.setQuestAnswer(textQuestView.textAnswer);
                                                
                                                /*XmlBuilder xml = new XmlBuilder("tytul");
                                                
                                                xml.addQuizText(newTextQuest.getQuestName(),  newTextQuest.getSoundPaths(),newTextQuest.getPicturePaths(), newTextQuest.getQuestDescription(), newTextQuest.getPreNote(),
                                                        newTextQuest.getPostNote(), newTextQuest.getGoTo(), newTextQuest.getPoints(), newTextQuest.getDate(), newTextQuest.getQuestAnswer(), newTextQuest.getQuestTimeout(), newTextQuest.getWrong());
                                            try {
                                                xml.saveXml();
                                                //dodac pola w zaleznosci od innych typow
                                            } catch (TransformerException ex) {
                                                Logger.getLogger(NewQuizView.class.getName()).log(Level.SEVERE, null, ex);
                                            }*/
						ZipPacker zip = new ZipPacker("paczka.zip");
                                                for(int i=0;i<newQuest.getPicturePaths().size();i++)
                                                {
                                                    try {
                                                        zip.addFile(newQuest.getPicturePaths().get(i));
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(NewQuizView.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }
                                                
                                                for(int i=0;i<newQuest.getSoundPaths().size();i++)
                                                {
                                                    try {
                                                        zip.addFile(newQuest.getSoundPaths().get(i));
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(NewQuizView.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }
                                                
                                            try {
                                                zip.addFile("Config.xml");
                                            } catch (IOException ex) {
                                                Logger.getLogger(NewQuizView.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                                zip.closeZip();
                                                
						campaignRef.addQuiz(newTextQuest);
						campaignRef.createXml("title");
                                                
						System.out.println(campaignRef.getQuizes().get(0).getQuestName());
						System.out.println(campaignRef.getQuizes().get(0).getSoundPaths().get(0));
						System.out.println(campaignRef.getQuizes().get(0).getPicturePaths().get(0));
						
						dispose();
					}
				});
			}
		});
		
		
		rightSidePanel.add(new FieldQuestView(), "Zagadka terenowa");
		rightSidePanel.add(new TextQuestView(), "Zagadka tekstowa");
		rightSidePanel.add(new MultipleChoiceQuestView(),"Zagadka wielokrotnego wyboru");
		rightSidePanel.add(new OrderQuestView(), "Zagadka uporządkowania");
		rightSidePanel.add(new RangeQuestView(), "Zagadka zasięgu");

		questTypeCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent aE) {
				CardLayout cl = (CardLayout) rightSidePanel.getLayout();
				cl.show(rightSidePanel, aE.getItem().toString());
			}
		});

	}

	private ArrayList rewriteJListToArrayList(JList list) {
		ArrayList newList = new ArrayList();
		for (int i = 0; i < list.getModel().getSize(); i++)
		{
			newList.add(list.getModel().getElementAt(i));
		}
		return newList;

	}
}
