// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/conf/routes
// @DATE:Mon Oct 15 13:08:08 CEST 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:15
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseSinglePageController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def fitbuddies: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SinglePageController.fitbuddies",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "fitbuddies"})
        }
      """
    )
  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SinglePageController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:9
    def myevents: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SinglePageController.myevents",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "myevents"})
        }
      """
    )
  
    // @LINE:8
    def myprofile: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SinglePageController.myprofile",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "myprofile"})
        }
      """
    )
  
  }


}
