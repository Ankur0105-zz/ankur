package model

import play.api.libs.json.{Json, OFormat}

/**
  * Created by E7440 on 2/15/2017.
  */
case class ServiceResponse(statusCode: Int, message: String)

object ServiceResponse{
  implicit val taskFormat: OFormat[ServiceResponse] = Json.format[ServiceResponse]
}
