package errorHandler

/**
  * Created by E7440 on 2/15/2017.
  */
import javax.inject.Singleton

import controllers.Assets._
import model.ServiceResponse
import model.ServiceResponse._
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future

@Singleton
class ErrorHandler extends HttpErrorHandler{
  override def onClientError(request: RequestHeader,
                             statusCode: Int,
                             message: String): Future[Result] = {
    Logger.error("Client error occurred with status code : "+ statusCode
      + "error message : "+ message)
    statusCode match {
      case NOT_FOUND => Future.successful{
        Status(404)(Json.toJson(ServiceResponse(400,"Handler not found. Invalid request")))
      }
      case _ => Future.successful{
        Status(statusCode)(Json.toJson(ServiceResponse(statusCode,"Bad request "+message)))
      }
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Logger.error("Internal server error occurred with message : " + exception)
    Future.successful{
        InternalServerError(Json.toJson(ServiceResponse(INTERNAL_SERVER_ERROR, exception.getMessage)))
    }
  }
}
