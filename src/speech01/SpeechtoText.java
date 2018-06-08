package speech01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class SpeechtoText {
	public static void main(String[] args) {
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("j16020", "j16020");

		File audio = new File("audio/sample1.wav");
		RecognizeOptions options = null;

		MySQL mysql = new MySQL();


		try {
			options = new RecognizeOptions.Builder()
					.model("ja-JP_BroadbandModel")
					.audio(audio)
					.contentType(RecognizeOptions.ContentType.AUDIO_WAV)
					.build();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		SpeechRecognitionResults transcript = service.recognize(options).execute();

		System.out.println(transcript);
		String s = String.valueOf(transcript);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(s);
			for(int i = 0;i < node.get("results").size(); i++){
			String tra = node.get("results").get(0).get("alternatives").get(0).get("transcript").asText();
			System.out.println("tra : " + tra);

			Double conf = node.get("results").get(0).get("alternatives").get(0).get("confidence").asDouble();
			System.out.println("conf : " + conf);
			mysql.updateImage(tra, conf);
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();

		}


	}

}
