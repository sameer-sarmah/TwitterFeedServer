package com.sap.twitter.feed.sentiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalysor{
	public static SentimentType analyse(String text)  {
		SentimentType[] values=SentimentType.values();
		List<SentimentType> sentimentTypes=Arrays.asList(values);
		List<String> sentiments=new ArrayList<String>();
		for(SentimentType type:sentimentTypes){
			sentiments.add(type.getType());
		}
         Properties props = new Properties();
        String sentiment=null;
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = pipeline.process(text);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            //System.out.println(sentiment + "\t" + sentence);
        }
         if(sentiments.contains(sentiment)){
        	 return SentimentType.valueOf(sentiment.toUpperCase()); 
         }else{
        	return SentimentType.NEGATIVE;
         }
        
    }
}