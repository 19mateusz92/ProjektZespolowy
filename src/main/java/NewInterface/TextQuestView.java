package NewInterface;

import Editor.XmlBuilder;
import Quest.Campaign;
import Quest.QuestFactory;
import Quest.QuestPoint;
import Quest.QuestType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.xml.transform.TransformerException;

public class TextQuestView extends QuestView {

private QuestPoint quest;
protected JTextField textGoTo;
private DefaultComboBoxModel answers = new DefaultComboBoxModel();
private JComboBox answersComboBox;
protected ArrayList<String> textAnswer = new ArrayList<String>();

private JLabel lblGoTo;
private JLabel lblAnswer;

private JButton btnAddAnswer;
private JButton btnEditAnswer;
private JButton btnDeleteAnswer;

private Border border = BorderFactory.createLineBorder(Color.BLACK);
private int answerTrigger=0;

public TextQuestView() {
            super();	
            this.add(new JLabel("Field"));
            setSize(panelWidth, panelHeight);
            
            lblGoTo = new JLabel("Następna zagadka");
            lblGoTo.setBounds(26,283,120,29);
            add(lblGoTo);
            
            textGoTo = new JTextField();
            textGoTo.setBounds(26,308,60,29);
            add(textGoTo);

            lblAnswer = new JLabel("Odpowied\u017A");
            lblAnswer.setBounds(26, 353, 87, 14);
            add(lblAnswer);
            
            answersComboBox = new JComboBox(answers);
            answersComboBox.setSelectedIndex(-1);
            answersComboBox.setBounds(26,378,120,29);
            add(answersComboBox);
            
            btnAddAnswer = new JButton("Dodaj odpowiedź");
            btnAddAnswer.setBounds(156,378,130,30);
            add(btnAddAnswer);
            
            btnEditAnswer = new JButton("Edytuj odpowiedź");
            btnEditAnswer.setBounds(286,378,130,30);
            add(btnEditAnswer);
            
            btnDeleteAnswer = new JButton("Usuń odpowiedź");
            btnDeleteAnswer.setBounds(426,378,130,30);
            add(btnDeleteAnswer);
            
            addButtonsListeners();

            quest = QuestFactory.createQuest(QuestType.FIELDQUEST);
                
}
    private void addButtonsListeners()
    {
        btnAddAnswer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textAnswer.add(JOptionPane.showInputDialog("Podaj odpowiedź"));
                answers.addElement(textAnswer.get(answerTrigger));
                answersComboBox.setSelectedIndex(answerTrigger);
                answerTrigger++;
            }
        });
        
        btnEditAnswer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = answersComboBox.getSelectedIndex();
                if(selectedIndex!=-1){
                String tempAnswer=JOptionPane.showInputDialog("Podaj odpowiedź",textAnswer.get(selectedIndex));
                textAnswer.set(selectedIndex, tempAnswer);
                answers.removeAllElements();
                for(int i=0;i<textAnswer.size();i++)
                {
                    answers.addElement(textAnswer.get(i));
                }
                answersComboBox.setSelectedIndex(selectedIndex);
            }}
        });
        
        btnDeleteAnswer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = answersComboBox.getSelectedIndex();
                if(selectedIndex!=-1){
                textAnswer.remove(selectedIndex);
                answers.removeElementAt(selectedIndex);
                answerTrigger--;
                answersComboBox=new JComboBox(answers);}
            }
        });
    }
}