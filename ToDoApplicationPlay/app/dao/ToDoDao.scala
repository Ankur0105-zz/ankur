package dao

import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase}
import helpers.Helpers._
import model._
import constants.User._
import constants.Task._
import constants.ToDo._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Updates
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result.UpdateResult

import scala.util.Try

/**
  * Created by E7440 on 2/2/2017.
  */
object ToDoDao {

  //To directly connect to the default server localhost on port 27017
  val mongoClient: MongoClient = MongoClient()

  //To get a database to operate upon
  val database: MongoDatabase = mongoClient.getDatabase(DB_NAME)

  //To get a collection to operate upon
  val collection: MongoCollection[Document] = database.getCollection(COLLECTION_NAME)

  //To insert a user data into a collection
  def insertUserInfo(user: User): Try[Completed] = {
    val tasksList: List[Document] = Nil
    val doc: Document = Document(_ID -> user.userId, USERNAME -> user.userName, PWD -> user.password, SEQUENCE_NO -> 0, TASKS -> tasksList)
    Try{
      collection.insertOne(doc).headResult()
    }
  }

  //Get a sequence number for task id
  def getSequenceNo(userId: String): Document = {
    collection.findOneAndUpdate(equal(_ID, userId), inc(SEQUENCE_NO, 1)).headResult()
  }

  //Insert a task details for user
  def insertTasksDetails(task: Task, userId: String): Try[Document] = {
    val doc: Document = Document(TASK_ID -> task.id, DESC -> task.description, IS_COMPLETED -> task.isCompleted,
      CREATE_DATE -> task.createDate, END_DATE -> task.endDate)
    Try{
      collection.findOneAndUpdate(equal(_ID, userId), Updates.addToSet(TASKS, doc)).headResult()
    }
  }

  //Give the number of tasks for the given user
  def getNumberOfTasks(userId: String): Try[Int] = Try{
      collection.find(equal(_ID, userId)).
      projection(fields(include(TASKS), excludeId())).
      results().head.getOrElse(TASKS, "").asArray.size
  }


  //Delete a task for the user
  def removeTask(userId: String, taskId: Int): Try[UpdateResult] = Try{
      collection.updateOne(equal(_ID, userId),
      Updates.pull(TASKS, Document(TASK_ID -> taskId))).headResult()
  }


  //Get all tasks for the user
  def getAllTasks(userId: String): Try[Seq[Document]] = Try {
    collection.find(equal(_ID, userId)).
    projection(fields(include(TASKS), excludeId())).results()
  }

  //Mark the task as completed for the user
  def markAsCompleted(userId: String, taskId: Int): Try[UpdateResult] = Try {
    collection.updateOne(and(equal(_ID , userId),equal("tasks.taskId",taskId)) , set("tasks.$.is_complted" , true )).headResult()
  }

  // Check that the particular user exists or not
  def login(userId: String, password: String): Try[Seq[Document]] = Try {
    collection.find(and(equal(_ID , userId),equal(PWD,password))).results()
  }

  //Get the username from user id
  def getUserName(userId: String): Try[Document] = Try {
    collection.find(equal(_ID , userId)).
      projection(fields(include(USERNAME), excludeId())).headResult()
  }
}

