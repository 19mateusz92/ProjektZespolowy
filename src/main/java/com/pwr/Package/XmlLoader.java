package com.pwr.Package;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.pwr.Quest.Campaign;
import com.pwr.Quest.ChoiceQuest;
import com.pwr.Quest.DecisionQuest;
import com.pwr.Quest.FieldQuest;
import com.pwr.Quest.OrderQuest;
import com.pwr.Quest.QuestFactory;
import com.pwr.Quest.QuestPoint;
import com.pwr.Quest.TextQuest;
import com.pwr.Quest.QuestType;
import com.pwr.Quest.TreasureBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlLoader {

	private File file;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;

	public XmlLoader(String name) {
		if (!name.equals("")) {
			try {
				file = new File(name);
				dbFactory = DocumentBuilderFactory.newInstance();
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void LoadXml(Campaign campaign) {
		ArrayList<String> introList = new ArrayList();
		ArrayList<String> introFileList = new ArrayList();

		ArrayList<String> outroList = new ArrayList();
		ArrayList<String> outroFileList = new ArrayList();

		ArrayList<String> treasureBoxNoteList = new ArrayList();
		ArrayList<String> treasureBoxXList = new ArrayList();
		ArrayList<String> treasureBoxYList = new ArrayList();
		ArrayList<String> treasureBoxWidthList = new ArrayList();
		ArrayList<String> treasureBoxHeightList = new ArrayList();

		String settingsStartdate="";
		String settingsBackGround1="";
		String settingsBackGround2="";
		String settingsBackGround3="";
		String settingsButton1="";
		String settingsLogo1="";

		String gameTitle="";

		NodeList gameList = doc.getElementsByTagName("game");
		Node gameNode = gameList.item(0);
		if (gameNode.getNodeType() == Node.ELEMENT_NODE) {
			Element gameElement = (Element) gameNode;
			gameTitle = gameElement.getAttribute("title").trim();
			campaign.setGameTitle(gameTitle);
		}

		NodeList introSrcList = doc.getElementsByTagName("intro");

		for (int i = 0; i < introSrcList.getLength(); i++) {
			Node nodeIntro = introSrcList.item(i);
			if (nodeIntro.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nodeIntro;
				NodeList introModuleSrcList = element
						.getElementsByTagName("introModule");
				for (int j = 0; j < introModuleSrcList.getLength(); j++) {
					Element introElement = (Element) introModuleSrcList.item(j);
					if (introElement.getNodeType() == Node.ELEMENT_NODE) {
						introList.add(introElement.getTextContent().trim());
						introFileList.add("temp" + File.separator
								+ introElement.getAttribute("image").trim());
					}
				}
			}
		}

		if (introFileList.size() > 0) {
			campaign.setIntroPics(introFileList);
		}
		if (introList.size() > 0) {
			campaign.setIntroText(introList);
		}

		NodeList outroSrcList = doc.getElementsByTagName("outro");

		for (int i = 0; i < outroSrcList.getLength(); i++) {
			Node nodeOutro = outroSrcList.item(i);
			if (nodeOutro.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nodeOutro;
				NodeList outroModuleSrcList = element
						.getElementsByTagName("outroModule");
				for (int j = 0; j < outroModuleSrcList.getLength(); j++) {
					Element outroElement = (Element) outroModuleSrcList.item(j);
					if (outroElement.getNodeType() == Node.ELEMENT_NODE) {
						outroList.add(outroElement.getTextContent().trim());
						outroFileList.add("temp" + File.separator
								+ outroElement.getAttribute("image").trim());
					}
				}
			}
		}

		if (outroFileList.size() > 0) {
			campaign.setOutroPics(outroFileList);
		}
		if (outroList.size() > 0) {
			campaign.setOutroText(outroList);
		}

		NodeList settingsSrcList = doc.getElementsByTagName("settings");
		Node nodeSettings = settingsSrcList.item(0);
		if (nodeSettings != null) {
			if (nodeSettings.getNodeType() == Element.ELEMENT_NODE) {
				Element element = (Element) nodeSettings;
				settingsStartdate = element.getElementsByTagName("startdate").item(0).getTextContent().trim();
				//campaign.setGameDate(element.getAttribute("startdate").trim());
				if(element.getElementsByTagName("backGround1").item(0)!=null)
				settingsBackGround1 = "temp" + File.separator
						+element
						.getElementsByTagName("backGround1").item(0)
						.getTextContent().trim();
				if(element.getElementsByTagName("backGround2").item(0)!=null)
				settingsBackGround2 = "temp" + File.separator
						+element
						.getElementsByTagName("backGround2").item(0)
						.getTextContent().trim();
				if(element.getElementsByTagName("backGround3").item(0)!=null)
				settingsBackGround3 = "temp" + File.separator
						+element
						.getElementsByTagName("backGround3").item(0)
						.getTextContent().trim();
				if(element.getElementsByTagName("logo1").item(0)!=null)
				settingsLogo1 = "temp" + File.separator
						+element.getElementsByTagName("logo1").item(0)
						.getTextContent().trim();
				if(element.getElementsByTagName("button1").item(0)!=null)
				settingsButton1 = "temp" + File.separator
						+element.getElementsByTagName("button1")
						.item(0).getTextContent().trim();
			}
			campaign.setSettings(settingsBackGround1, settingsBackGround2, settingsBackGround3, settingsLogo1, settingsButton1,settingsStartdate,gameTitle);
			//campaign.setGameDate(settingsStartdate);
		}
		NodeList treasureBoxList = doc.getElementsByTagName("treasurebox");

		for (int i = 0; i < treasureBoxList.getLength(); i++) {
			Node treasureBoxNode = treasureBoxList.item(i);
			if (treasureBoxNode.getNodeType() == Element.ELEMENT_NODE) {
				Element treasureBoxElement = (Element) treasureBoxNode;
				treasureBoxNoteList.add(treasureBoxElement
						.getElementsByTagName("note").item(0).getTextContent()
						.trim());
				treasureBoxXList.add(treasureBoxElement
						.getElementsByTagName("x").item(0).getTextContent()
						.trim());
				treasureBoxYList.add(treasureBoxElement
						.getElementsByTagName("y").item(0).getTextContent()
						.trim());
				treasureBoxWidthList.add(treasureBoxElement
						.getElementsByTagName("width").item(0).getTextContent()
						.trim());
				treasureBoxHeightList.add(treasureBoxElement
						.getElementsByTagName("height").item(0)
						.getTextContent().trim());
			}
			TreasureBox treasureBox = new TreasureBox();
			treasureBox.setNote(treasureBoxNoteList.get(i));
			treasureBox.setxCoordinate(Double.parseDouble(treasureBoxXList
					.get(i)));
			treasureBox.setyCoordinate(Double.parseDouble(treasureBoxYList
					.get(i)));
			//treasureBox
			//		.setWidth(Double.parseDouble(treasureBoxWidthList.get(i)));
			//treasureBox.setHeight(Double.parseDouble(treasureBoxHeightList
			//		.get(i)));
			treasureBox.setX2Coordinate(Double.parseDouble(treasureBoxWidthList.get(i)));
			treasureBox.setY2Coordinate(Double.parseDouble(treasureBoxHeightList.get(i)));
			treasureBox.countDimmension();
			campaign.addTreasureBox(treasureBox);
		}

		NodeList nList = doc.getElementsByTagName("quiz");

		for (int i = 0; i < nList.getLength(); i++) {
			String title;
			String id;

			String date;
			String points;
			String goTo;
			String wrong;
			String timestop;

			String preNote;
			String postNote;

			ArrayList<String> imageList = new ArrayList();
			ArrayList<Boolean> imageInventoryList = new ArrayList();
			ArrayList<String> soundList = new ArrayList();
			ArrayList<Boolean> soundInventoryList = new ArrayList();
			int narration = -1;
			String paragraphList = "";
			Node node = nList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				title = element.getAttribute("title").trim();
				id = element.getAttribute("id").trim();

				preNote = element.getElementsByTagName("prenote").item(0)
						.getTextContent().trim();
				postNote = element.getElementsByTagName("postnote").item(0)
						.getTextContent().trim();

				NodeList imageSrcList = element.getElementsByTagName("image");
				for (int j = 0; j < imageSrcList.getLength(); j++) {
					Element imageSrcElement = (Element) imageSrcList.item(j);
					if (imageSrcElement.getNodeType() == Element.ELEMENT_NODE) {
						imageList.add("temp" + File.separator
								+ imageSrcElement.getAttribute("src").trim());
						
						if (imageSrcElement.getAttribute("inventory") != null) {
							String imageInventory = imageSrcElement.getAttribute("inventory").trim();
							
							if(imageInventory.equals("true"))
							{
								imageInventoryList.add(true);
							}
							else {
								imageInventoryList.add(false);
							} 
						}
						
					}
				}

				NodeList soundSrcList = element.getElementsByTagName("sound");
				for (int j = 0; j < soundSrcList.getLength(); j++) {
					Element soundSrcElement = (Element) soundSrcList.item(j);
					if (soundSrcElement.getNodeType() == Element.ELEMENT_NODE) {
						soundList.add("temp" + File.separator
								+ soundSrcElement.getAttribute("src").trim());
					}

					if (soundSrcElement.getAttribute("inventory") != null) {
						String soundInventory = soundSrcElement.getAttribute("inventory").trim();
						
						if(soundInventory.equals("true"))
						{
							System.out.println("jest");
							soundInventoryList.add(true);
						}
						else {
							System.out.println("nie jest");
							soundInventoryList.add(false);
					} 
					}
					

					if (soundSrcElement.getAttribute("narration") != null) {
						String narrationS=soundSrcElement.getAttribute("narration").trim();
						if(narrationS.equals("true"))
						narration = j;
					}
				}

				NodeList paragraphSrcList = element
						.getElementsByTagName("questionmodule");
				Element paragraphSrcElement = (Element) paragraphSrcList
						.item(0);
				for (int j = 0; j < paragraphSrcElement.getElementsByTagName(
						"paragraph").getLength(); j++) {
					if (paragraphSrcElement.getNodeType() == Element.ELEMENT_NODE) {
						paragraphList = (paragraphSrcElement
								.getElementsByTagName("paragraph").item(j)
								.getTextContent().trim());
					}
				}

				NodeList answerSrcList = element.getElementsByTagName("answer");
				Element answerModuleElement = (Element) answerSrcList.item(0);

				String type = answerModuleElement.getAttribute("type");

				goTo = answerModuleElement.getAttribute("goto").trim();
				points = answerModuleElement.getAttribute("points").trim();
				if (points == "") {
					points = "0";
				}
				wrong = answerModuleElement.getAttribute("wrong").trim();
				timestop = answerModuleElement.getAttribute("timestop").trim();
				if (timestop == "") {
					timestop = "0";
				}
				date = answerModuleElement.getAttribute("datetime").trim();

				ArrayList<String> answerList = new ArrayList();
				QuestPoint quest;

				switch (type) {
				case "Text":
					answerList = new ArrayList();
					NodeList answersList = answerModuleElement
							.getElementsByTagName("correct");
					for (int j = 0; j < answersList.getLength(); j++) {
						answerList.add(answersList.item(j).getTextContent()
								.trim());
					}
					quest = (TextQuest) QuestFactory
							.createQuest(QuestType.TEXTQUEST);
					setQuest(id,quest, title, imageList, imageInventoryList,
							soundList, soundInventoryList, narration,
							paragraphList, preNote, postNote);
					setQuestAnswer(quest, goTo, Integer.parseInt(points), date,
							wrong, Integer.parseInt(timestop));
					setTextQuest((TextQuest) quest, answerList);
					campaign.addQuiz(quest);
					campaign.createdTrue();
					break;
				case "GPS":
					String x = answerModuleElement.getElementsByTagName("x")
							.item(0).getTextContent().trim();
					String y = answerModuleElement.getElementsByTagName("y")
							.item(0).getTextContent().trim();
					String width = answerModuleElement
							.getElementsByTagName("width").item(0)
							.getTextContent().trim();
					String height = answerModuleElement
							.getElementsByTagName("height").item(0)
							.getTextContent().trim();
					quest = (FieldQuest) QuestFactory
							.createQuest(QuestType.FIELDQUEST);
					setQuest(id,quest, title, imageList, imageInventoryList,
							soundList, soundInventoryList, narration,
							paragraphList, preNote, postNote);
					setQuestAnswer(quest, goTo, Integer.parseInt(points), date,
							wrong, Integer.parseInt(timestop));
					setGpsQuest((FieldQuest) quest, x, y, width, height);
					campaign.addQuiz(quest);
					campaign.createdTrue();
					break;
				case "MofN":
					answerList = new ArrayList();
					ArrayList<Boolean> answerListBool = new ArrayList();
					NodeList answersMList = answerModuleElement
							.getElementsByTagName("option");
					for (int j = 0; j < answersMList.getLength(); j++) {
						answerList.add(answersMList.item(j).getTextContent()
								.trim());
						Element answerMElement = (Element) answersMList.item(j);
						String tempAnswer = answerMElement.getAttribute(
								"correct").trim();
						if (tempAnswer.equals("true")) {
							answerListBool.add(true);
						} else if (tempAnswer.equals("false")) {
							answerListBool.add(false);
						}
					}
					quest = (ChoiceQuest) QuestFactory
							.createQuest(QuestType.CHOICEQUEST);
					setQuest(id,quest, title, imageList, imageInventoryList,
							soundList, soundInventoryList, narration,
							paragraphList, preNote, postNote);
					setQuestAnswer(quest, goTo, Integer.parseInt(points), date,
							wrong, Integer.parseInt(timestop));
					setMofNQuest((ChoiceQuest) quest, answerList,
							answerListBool);
					campaign.addQuiz(quest);
					campaign.createdTrue();
					break;
				case "Decision":
					answerList = new ArrayList();
					ArrayList<String> goToList = new ArrayList();
					NodeList answersGoToList = answerModuleElement
							.getElementsByTagName("option");
					for (int j = 0; j < answersGoToList.getLength(); j++) {
						answerList.add(answersGoToList.item(j).getTextContent()
								.trim());
						Element answerGoToElement = (Element) answersGoToList
								.item(j);
						goToList.add(answerGoToElement.getAttribute("goto")
								.trim());
					}
					quest = (DecisionQuest) QuestFactory
							.createQuest(QuestType.DECISIONQUEST);
					setQuest(id,quest, title, imageList, imageInventoryList,
							soundList, soundInventoryList, narration,
							paragraphList, preNote, postNote);
					setQuestAnswer(quest, goTo, Integer.parseInt(points), date,
							wrong, Integer.parseInt(timestop));
					setDecisionQuest((DecisionQuest) quest, answerList,
							goToList);
					campaign.addQuiz(quest);
					campaign.createdTrue();
					break;
				case "Permutation":
					answerList = new ArrayList();
					ArrayList<String> indexList = new ArrayList();
					NodeList answersPermutationList = answerModuleElement
							.getElementsByTagName("option");
					for (int j = 0; j < answersPermutationList.getLength(); j++) {
						answerList.add(answersPermutationList.item(j)
								.getTextContent().trim());
						Element answerPermutationElement = (Element) answersPermutationList
								.item(j);
						indexList.add(answerPermutationElement.getAttribute(
								"index").trim());
					}
					quest = QuestFactory.createQuest(QuestType.ORDERQUEST);
					setQuest(id,quest, title, imageList, imageInventoryList,
							soundList, soundInventoryList, narration,
							paragraphList, preNote, postNote);
					setQuestAnswer(quest, goTo, Integer.parseInt(points), date,
							wrong, Integer.parseInt(timestop));
					setPermutationQuest((OrderQuest) quest, answerList,
							indexList);
					campaign.addQuiz(quest);
					campaign.createdTrue();
					break;
				default:
					break;
				}

			}
		}

	}

	private void setQuest(String id,QuestPoint quest, String title,
			ArrayList<String> imageList, ArrayList<Boolean> imageInventoryList,
			ArrayList<String> soundList, ArrayList<Boolean> soundInventoryList,
			int narration, String paragraphList, String preNote, String postNote) {
		quest.setId(id);
		quest.setQuestName(title);
		quest.setPicturePaths(imageList);
		quest.setPictureInventoryList(imageInventoryList);
		quest.setSoundPaths(soundList);
		quest.setSoundInventoryList(soundInventoryList);
		quest.setSoundNarration(narration);
		quest.setParagraph(paragraphList);
		quest.setPreNote(preNote);
		quest.setPostNote(postNote);
		QuestPoint.compareAndChangeId(Integer.parseInt(id));
	}

	private void setQuestAnswer(QuestPoint quest, String goTo, int points,
			String date, String wrong, int timestop) {
		String gt[] = new String[1];
		gt[0]=goTo;
		quest.setGoTo(gt);
		quest.setPoints(points);
		quest.setDate(date);
		quest.setWrong(wrong);
		quest.setQuestTimeout(timestop);
	}

	private void setTextQuest(TextQuest quest, ArrayList answerList) {
		quest.setQuestAnswer(answerList);
	}

	private void setGpsQuest(FieldQuest quest, String x, String y,
			String width, String height) {
		quest.setXCoordinate(Double.parseDouble(x));
		quest.setYCoordinate(Double.parseDouble(y));
		quest.setX2Coordinate(Double.parseDouble(width));
		quest.setY2Coordinate(Double.parseDouble(height));
		quest.countDimmension();
	}

	private void setMofNQuest(ChoiceQuest quest, ArrayList<String> answerList,
			ArrayList<Boolean> answerListBool) {
		quest.setQuestAnswer(answerList, answerListBool);
	}

	private void setDecisionQuest(DecisionQuest quest,
			ArrayList<String> answerList, ArrayList<String> goToList) {
		quest.setDecisionAnswer(answerList, goToList);
	}

	private void setPermutationQuest(OrderQuest quest,
			ArrayList<String> answerList, ArrayList<String> indexList) {
		
		int index=1;
		ArrayList<String> list = new ArrayList();
		
		for(int j=0;j<answerList.size();j++)
		{
			for(int i=0;i<answerList.size();i++)
			{
				if(Integer.parseInt(indexList.get(i))==index)
				{
					list.add(answerList.get(i));
					index++;
					break;
				}
			}
		}
		quest.setQuestAnswer(list);
	}

}
