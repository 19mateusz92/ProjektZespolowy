package Editor;

import java.util.ArrayList;

public class QuestPoint  implements DescribeQuest{
	private ArrayList<String> PicturePaths;
	private ArrayList<String> SoundPaths;
	
	public QuestPoint()
	{
		PicturePaths = new ArrayList<String>();
		PicturePaths.add("");
		SoundPaths = new ArrayList<String>();
		SoundPaths.add("");
		QuestDescription = "";
		QuestAnswer = "";
	}
	
	public ArrayList<String> getPicturePaths() {
		return PicturePaths;
	}

	public void setPicturePaths(ArrayList<String> picturePaths) {
		PicturePaths = picturePaths;
	}

	public ArrayList<String> getSoundPaths() {
		return SoundPaths;
	}

	public void setSoundPaths(ArrayList<String> soundPaths) {
		SoundPaths = soundPaths;
	}

	public String getQuestDescription() {
		return QuestDescription;
	}

	public void setQuestDescription(String questDescription) {
		QuestDescription = questDescription;
	}

	public String getQuestAnswer() {
		return QuestAnswer;
	}

	public void setQuestAnswer(String questAnswer) {
		QuestAnswer = questAnswer;
	}

	private String QuestDescription;
	private String QuestAnswer;
	
}
