// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/conf/routes
// @DATE:Mon Oct 15 13:08:08 CEST 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:15
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:6
  class ReverseSinglePageController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def fitbuddies(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "fitbuddies")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:9
    def myevents(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "myevents")
    }
  
    // @LINE:8
    def myprofile(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "myprofile")
    }
  
  }


}
