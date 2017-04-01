package model

import java.util.Date

import play.api.libs.json.{Json, OFormat}

/**
  * Created by E7440 on 2/2/2017.
  */

case class Task(id: Int, description: String, isCompleted: Boolean, createDate: Date,
                endDate: Date) {}

object Task{
  implicit val taskFormat: OFormat[Task] = Json.format[Task]
}

case class User(userId: String, userName:String, password:String) {}

object User{
  implicit val userFormat: OFormat[User] = Json.format[User]
}

case class TaskDto(userId:String, description: String, endDate: String){}

object TaskDto{
  implicit val taskFormat: OFormat[TaskDto] = Json.format[TaskDto]
}