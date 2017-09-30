/**
  * Created by E7440 on 1/30/2017.
  */
object WordCount extends App{
  import scala.io.Source

  val source=Source.fromFile("src/main/resources/WordCount/test.txt","UTF-8")

  val wordListWithCount=((source mkString) map{
    case ch if ch.isLetterOrDigit =>  ch
    case _ => " "
  } mkString).split(" ").toList.filter(_.length>1)
    .groupBy(word=> word.toLowerCase).toList.sortBy(_._2.length).map(word=> word._1+","+word._2.length)

  source.close()

  import java.io.PrintWriter
  val out=new PrintWriter("src/main/resources/WordCount/output.txt")
  out.println(wordListWithCount mkString "\n")
  out.close()
}
