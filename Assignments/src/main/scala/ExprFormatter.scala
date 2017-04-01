/**
  * Created by E7440 on 1/30/2017.
  */
object ExprFormatter extends App{

  import scala.collection.mutable.Stack

  abstract class Expr
  case class Var(name: String) extends Expr
  case class Number(num: Double) extends Expr
  case class BinOp(operator: String,
                   left: Expr, right: Expr) extends Expr

  val precedenceMap: Map[String, Int] =Map("*"->2, "/"->0, "+"->1, "-"->1)

  def eval(opr:String, right:Expr, left:Expr):Expr=opr match{
    case "*"=>BinOp("*",left, right)
    case "+"=>BinOp("+",left, right)
    case "/"=>BinOp("/",left, right)
    case "-"=>BinOp("-",left, right)
  }

  val operatorList=List("*","/","+","-")

  def evaluateToCaseClassExpr(exprStr:String):Expr={
    val operatorStack:Stack[String]=Stack()
    val operandStack:Stack[Expr]=Stack()
    val exprList=exprStr.replaceAll(" ","").split("").toList
    exprList foreach  (str=>{
      if(str.charAt(0).isDigit) operandStack.push(Number(str.toDouble))
      else if(operatorList.contains(str)) operatorStack.push(str)
      else{
        if(str!=")") operandStack.push(Var(str))
        else{
          while(operandStack.tail.head!=Var("(")){
            operandStack.push(eval(operatorStack.pop, operandStack.pop, operandStack.pop))
          }
          val top=operandStack.pop
          operandStack.pop
          operandStack.push(top)
        }
      }
    })
    operatorStack foreach (operator => operandStack.push(eval(operator, operandStack.pop, operandStack.pop)))
    operandStack.pop
  }

  def generateSpace(str:String, maxLength:Int):String={
    val extraSpace=maxLength-str.length
    val right=extraSpace/2
    val left=extraSpace-right
    (" "*left)+str+(" "*right)
  }

  def formattedExpr(exprStr : Expr, enclPrec:Int):Array[String]=exprStr match{
    case Var(name)=> Array(name)

    case Number(num)=> {
      val s=num.toString
      if(s endsWith ".0") Array(s.substring(0, s.length-2))
      else Array(s)
    }

    case BinOp("/", left, right)=>{
      val opPrec=precedenceMap("/")
      val topArray = formattedExpr(left, opPrec)
      val bottomArray = formattedExpr(right, opPrec)
      val max=topArray(0).length max bottomArray(0).length
      val line = "-" * max
      val top=topArray map (str=>generateSpace(str, max))
      val bottom=bottomArray map (str=>generateSpace(str, max))
      Array(top mkString "\n", line, bottom mkString "\n")
    }

    case BinOp(op, left, right)=>{
      val opPrec=precedenceMap(op)
      val leftExpr=formattedExpr(left, opPrec)
      val rightExpr=formattedExpr(right, opPrec)
      val bracket=if(enclPrec!=opPrec && enclPrec!=0 && opPrec!=0) ("(",")")
      else("","")
      (leftExpr.length, rightExpr.length) match{
        case (1,1)=>Array(bracket._1+leftExpr(0)+" "+op+" "+rightExpr(0)+bracket._2)
        case (1,3)=>Array(rightExpr(0), bracket._1+leftExpr(0)+" "+op+" "+rightExpr(1)+bracket._2, rightExpr(2))
        case (3,1)=>Array(leftExpr(0), bracket._1+leftExpr(1)+" "+op+" "+rightExpr(0)+bracket._2, leftExpr(2))
        case (3,3)=>Array(leftExpr(0)+"   "+rightExpr(0), bracket._1+leftExpr(1)+" "+op+" "+rightExpr(1)+bracket._2, leftExpr(2)+"   "+rightExpr(2))
      }
    }
  }

  def showFormattedExpr(expr:String):String=
    "\n"+(formattedExpr(evaluateToCaseClassExpr(expr),0) mkString "\n")

  println(showFormattedExpr("((((a / (b * c)) + 1 / n) / 3) + 5)"))
  println(showFormattedExpr("x / (x + 1)"))
  println(showFormattedExpr("(1 / 2) * (x + 1)"))

}
