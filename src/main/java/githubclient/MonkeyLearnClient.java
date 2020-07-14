package githubclient;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

//API用法  见https://github.com/monkeylearn/monkeylearn-java
public class MonkeyLearnClient {
	private static final String API_KEY = "8f5693c7bde8409e01d07fb6646215777eb2864d";
	
	public static void main(String[] args) {
		
		String[] textList = {
				"Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design and the first to feature the spacesuit’s full-body look.", 
			};
		List<List<String>> words = extractKeywords(textList);
		for (List<String> ws : words) {
			for (String w : ws) {
				System.out.println(w);
			}
			System.out.println();
		}
	}


	
	
	
	public static List<List<String>> extractKeywords(String[] text) {
		if (text == null || text.length == 0) {
			return new ArrayList<>();
		}

		// Use the API key from your account
		MonkeyLearn ml = new MonkeyLearn(API_KEY);

		// Use the keyword extractor, this step is to set the maximum number of keywords to present
		ExtraParam[] extraParams = { new ExtraParam("max_keywords", "3") };
		MonkeyLearnResponse response;
		try {
			response = ml.extractors.extract("ex_YCya9nrn", text, extraParams);//change to your model id
			JSONArray resultArray = response.arrayResult;
			/*System.out.println(resultArray);
			 * [[{"count":1,"positions_in_text":[0],"keyword":"elon musk","relevance":"0.952"},
			 * {"count":1,"positions_in_text":[78],"keyword":"second image","relevance":"0.952"},
			 * {"count":2,"positions_in_text":[36,145],"keyword":"spacesuit","relevance":"0.952"}]]
			 * array嵌套array，因为String[] text的length为1，所以resultArray里只有1个array，每个array
			 * 包含着所有top3的keyword json object，每个json object代表通过tfidf算出来的一个keyword的information，
			 * 我们只需要提取出"keyword"这个string出来。
			 */
			return getKeywords(resultArray);
		} catch (MonkeyLearnException e) {// it’s likely to have an exception
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	
	
	/*
	 * "front end developer"
	 * "black live matters"
	 * "hello world"
	 * 
	 * <"front", "end", "develpoer">
	 * <"black", "live", "matters">
	 * <"hello", "world">
	 * 
	 */
	private static List<List<String>> getKeywords(JSONArray mlResultArray) {
		List<List<String>> topKeywords = new ArrayList<>();
		// Iterate the result array and convert it to our format.
		for (int i = 0; i < mlResultArray.size(); ++i) {
			//System.out.println(mlResultArray.size()); // 1
			List<String> keywords = new ArrayList<>();
			JSONArray keywordsArray = (JSONArray) mlResultArray.get(i);
			//System.out.println(keywordsArray.size()); // 3
			for (int j = 0; j < keywordsArray.size(); ++j) {
				JSONObject keywordObject = (JSONObject) keywordsArray.get(j);
				// We just need the keyword, excluding other fields.
				String keyword = (String) keywordObject.get("keyword");
				keywords.add(keyword);

			}
			topKeywords.add(keywords);
		}
		return topKeywords;
	}

	
}
