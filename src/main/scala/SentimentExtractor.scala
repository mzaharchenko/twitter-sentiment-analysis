import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._


class SentimentExtractor {
  val properties = new Properties()
  properties.put("annotators", "tokenize, ssplit, parse, sentiment")
  val pipeline = new StanfordCoreNLP(properties)

  def getSentiment(tweet: String): List[(String, String)] = {

    def classifySentiment(sentiment: Int): String = sentiment match {
      case x if x == 0 || x == 1 => "Negative"
      case 2 => "Neutral"
      case x if x == 3 || x == 4 => "Positive"
    }

    val annotation = pipeline.process(tweet)
    val sentences: List[CoreMap] = annotation.get(classOf[CoreAnnotations.SentencesAnnotation]).asScala.toList
    sentences
      .map(sentence => (sentence, sentence.get(classOf[SentimentAnnotatedTree])))
      .map { case (sentence, tree) => (sentence.toString, classifySentiment(RNNCoreAnnotations.getPredictedClass(tree)))
      }
  }
}
