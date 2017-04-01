package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if(r==0 || c==0 || r==c)
          1
      else
          pascal(c-1, r-1)+pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      if(chars.isEmpty)
          true
      else{
          def check(charList : List[Char], brackets:Int):Boolean={
              if(charList.isEmpty)
                  brackets==0
              else if(brackets<0)
                  false
              else{
                  if(charList.head=='(')
                    check(charList.tail,brackets+1)
                  else if(charList.head==')')
                    check(charList.tail,brackets-1)
                  else
                    check(charList.tail,brackets)
              }
          }

        check(chars,0)
      }
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if(money == 0)
        1
      else if(money < 0)
        0
      else if(coins.isEmpty && money>=1)
        0
      else
        countChange(money, coins.tail)+countChange(money-coins.head, coins)
    }
  }
