package controllers

import javax.inject._

import constants.ToDo._
import play.api.mvc._
import model._
import play.api.libs.json.{JsValue, Json}
import services.ToDoService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

@Singleton
class ToDoController @Inject() extends Controller {

  def index = Action{ implicit request =>
    Ok(views.html.index("ToDo Application"))
  }

  def login: Action[JsValue] = Action(parse.json){ implicit request =>
    val userId = (request.body \ USER_ID).asOpt[String].get
    val password = (request.body \ PASSWORD).asOpt[String].get
    ToDoService.login(userId, password) fold(
      serviceResponse => Status(serviceResponse.statusCode)(Json.toJson(serviceResponse)),
      result => Ok(Json.toJson(ServiceResponse(OK, result.toString)))
    )
  }

  def createUser: Action[JsValue] = Action(parse.json){ implicit request =>
    val userId = (request.body \ USER_ID).asOpt[String].get
    val password = (request.body \ PASSWORD).asOpt[String].get
    val userName = (request.body \ USER_NAME).asOpt[String].get
    val user = new User(userId, userName, password)
    ToDoService.insertUserInfo(user) fold(
      serviceResponse => Status(serviceResponse.statusCode)(Json.toJson(serviceResponse)),
      result => Ok(Json.toJson(ServiceResponse(OK, result.toString)))
    )
  }


  def getTasks = Action{implicit request =>
    val userId = request.getQueryString(USER_ID).get
    ToDoService.getAllTasks(userId) fold(
    serviceResponse => Status(serviceResponse.statusCode)(Json.toJson(serviceResponse)),
    result => Ok(Json.toJson(result))
    )
  }

  def createTask: Action[JsValue] = Action(parse.json){ implicit request =>
    val userId = (request.body \ USER_ID).asOpt[String].get
    val desc = (request.body \ DESCRIPTION).asOpt[String].get
    val endDate = (request.body \ _END_DATE ).asOpt[String].get
    val taskDto = new TaskDto(userId, desc, endDate)
    ToDoService.createTask(taskDto) fold(
      serviceResponse => Status(serviceResponse.statusCode)(Json.toJson(serviceResponse)),
      result => Ok(Json.toJson(ServiceResponse(OK, result.toString)))
    )
  }

  def deleteTask(taskId: Int) = Action{ implicit request =>
    val userId = request.getQueryString(USER_ID).get
    ToDoService.removeTask(userId, taskId) fold(
      serviceResponse => Status(serviceResponse.statusCode)(Json.toJson(serviceResponse)),
      result => Ok(Json.toJson(result))
    )
  }

  def markAsCompleted(taskId: Int) = Action{ implicit request =>
    val userId = request.getQueryString(USER_ID).get
    ToDoService.markAsCompleted(userId, taskId) fold(
      serviceResponse => Status(serviceResponse.statusCode)(Json.toJson(serviceResponse)),
      result => Ok(Json.toJson(result))
    )
  }

  def getUserName = Action{implicit request =>
    val userId = request.getQueryString(USER_ID).get
    ToDoService.getUserName(userId) fold(
      serviceResponse => Status(serviceResponse.statusCode)(Json.toJson(serviceResponse)),
      result => Ok(result)
    )
  }

}
