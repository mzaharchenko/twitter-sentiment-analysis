import scala.concurrent.ExecutionContext.Implicits.global
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.entities.enums.ResultType


object TwitterApplication extends App{

  val client = TwitterRestClient()
  val sentimentExtractor = new SentimentExtractor()

  def getMostLiked(tweets: Seq[Tweet], n: Int = 10) : Seq[(String, Int)] = {

    val tweetTexts: Seq[(String,Int)] = tweets.map{tweet=>
      (tweet.text,tweet.favorite_count)
    }
    tweetTexts.sortBy { case (entity, frequency) => -frequency }.take(n)
  }

  val query = "#movie OR \"movie review\" or movie"
  client.searchTweet(query,count = 10).map {
      tweets =>
        val mostRetweeted: Seq[(String, Int)] = getMostLiked(tweets.data.statuses)
        mostRetweeted.foreach{
          tweet => sentimentExtractor.getSentiment(tweet._1)
              .map{case (tweet,sentiment) => s"$tweet | $sentiment"}
              .foreach(println)
        }
    }

}
