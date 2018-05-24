package speech01;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

public class Speech01_main {
	public static void main(String[] args){
	TextToSpeech synthesizer = new TextToSpeech();
	   synthesizer.setUsernameAndPassword("ee9d3ca0-4631-466c-bb5f-8cf52c91a788", "4MR32iYVhN37");
	   String translation = "THE CLOCK STOPPED TICKING FOREVER AGO ";
	   SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
		       .text(translation)
		       .voice(SynthesizeOptions.Voice.EN_US_LISAVOICE)
		       .accept(SynthesizeOptions.Accept.AUDIO_WAV)
		       .build();
		   InputStream in = synthesizer.synthesize(synthesizeOptions).execute();
		   try{
		   writeToFile(WaveUtils.reWriteWaveHeader(in), new File("wav/output.wav"));
		   } catch (IOException e){
			   e.printStackTrace();
		   }

	}

	private static void writeToFile(InputStream in, File file) {
		   try {
		     OutputStream out = new FileOutputStream(file);
		     byte[] buf = new byte[1024];
		     int len;
		     while ((len = in.read(buf)) > 0) {
		       out.write(buf, 0, len);
		     }
		     out.close();
		     in.close();
		   } catch (Exception e) {
		     e.printStackTrace();
		   }
		 }

}
