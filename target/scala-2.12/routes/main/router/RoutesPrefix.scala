// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/conf/routes
// @DATE:Mon Oct 15 13:08:08 CEST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
