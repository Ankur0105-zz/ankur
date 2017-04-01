package services

import java.text.SimpleDateFormat

import dao.ToDoDao
import model.{ServiceResponse, Task, TaskDto, User}
import java.util.Date

import constants.Task._
import constants.ToDo._
import org.mongodb.scala.{Completed, Document, MongoWriteException}
import play.api.http.Status._
import play.twirl.api.TemplateMagic.javaCollectionToScala

import scala.util.{Failure, Left, Right, Success, Try}
/**
  * Created by E7440 on 2/6/2017.
  */
object ToDoService {

  def login(userId: String, password: String): Either[ServiceResponse, String] = {
    val results = ToDoDao.login(userId, password)
    results match{
      case Success(_) => if(results.get.nonEmpty)Right(SUCCESS) else Left(ServiceResponse(BAD_REQUEST, "User Id or Password doesn't match"))
      case Failure(_) => Left(ServiceResponse(INTERNAL_SERVER_ERROR,"Not able to add the task. Please try later."))
    }
  }

  def createTask(dto: TaskDto): Either[ServiceResponse, String] = {
      val taskId = ToDoDao.getSequenceNo(dto.userId).get(SEQUENCE_NO).get.asNumber().intValue + 1
      // Date in String format : 07/28/2011
      val end_date = new SimpleDateFormat(DATE_FORMAT).parse(dto.endDate)
      val task = new Task(taskId, dto.description, false, new Date(), end_date)
      val results = ToDoDao.insertTasksDetails(task, dto.userId)
      results match{
        case Success(_) => Right("Task Successfully Created")
        case Failure(_) => Left(ServiceResponse(INTERNAL_SERVER_ERROR,"Not able to add the task. Please try later."))
      }
  }

  def getAllTasks(userId: String): Either[ServiceResponse, Seq[Task]] = {
    val tasks = ToDoDao.getAllTasks(userId)
    tasks match{
      case Success(_) => Right{
        tasks.get.flatMap(x => x.values.flatMap(y => y.asArray().map(z=>{
          val doc = z.asDocument
          Task(doc.get(TASK_ID).asNumber.intValue,
            doc.get(DESC).asString.getValue,
            doc.get(IS_COMPLETED).asBoolean.getValue,
            new Date(doc.get(CREATE_DATE).asDateTime.getValue),
            new Date(doc.get(END_DATE).asDateTime.getValue))
        })))
      }
      case Failure(_) => Left(ServiceResponse(INTERNAL_SERVER_ERROR,"Not able to fetch the tasks. Please try later."))
    }
  }

  def insertUserInfo(user: User): Either[ServiceResponse, String] = {
    val results: Try[Completed] = ToDoDao.insertUserInfo(user)
    results match{
      case Success(_) => Right("User Successfully Created")
      case Failure(_: MongoWriteException) => Left(ServiceResponse(BAD_REQUEST,"UserId already exists."))
      case Failure(_) => Left(ServiceResponse(INTERNAL_SERVER_ERROR,"Not able to add the user. Please try later."))
    }
  }

  def removeTask(userId: String, taskId: Int): Either[ServiceResponse, ServiceResponse] = {
    val results = ToDoDao.removeTask(userId, taskId)
    results match{
      case Success(_) => if(results.get.getMatchedCount > 0)
                            Right(ServiceResponse(OK, "Task deleted successfully"))
                         else
                            Right(ServiceResponse(BAD_REQUEST, "Task Id or User Id is not available"))
      case Failure(_) => Left(ServiceResponse(INTERNAL_SERVER_ERROR,"Not able to delete the task. Please try later."))
    }
  }

  def markAsCompleted(userId: String, taskId: Int): Either[ServiceResponse, ServiceResponse] = {
    val results = ToDoDao.markAsCompleted(userId, taskId)
    results match{
      case Success(_) => if(results.get.getMatchedCount > 0)
                            Right(ServiceResponse(OK, "Task updated successfully"))
                         else
                            Right(ServiceResponse(BAD_REQUEST, "Task Id or User Id is not available"))
      case Failure(_) => Left(ServiceResponse(INTERNAL_SERVER_ERROR,"Not able to update the task. Please try later."))
    }
  }

  def getUserName(userId: String): Either[ServiceResponse, String] = {
    val userName = ToDoDao.getUserName(userId)
    userName match{
      case Success(_) => Right(userName.get.toBsonDocument.get(USER_NAME).asString.getValue)
      case Failure(_) => Left(ServiceResponse(INTERNAL_SERVER_ERROR,"Not able to get user name. Please try later."))
    }
  }
}
