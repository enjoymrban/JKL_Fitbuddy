// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/conf/routes
// @DATE:Mon Oct 15 13:08:08 CEST 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  SinglePageController_1: controllers.SinglePageController,
  // @LINE:15
  Assets_0: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    SinglePageController_1: controllers.SinglePageController,
    // @LINE:15
    Assets_0: controllers.Assets
  ) = this(errorHandler, SinglePageController_1, Assets_0, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, SinglePageController_1, Assets_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.SinglePageController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """fitbuddies""", """controllers.SinglePageController.fitbuddies"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """myprofile""", """controllers.SinglePageController.myprofile"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """myevents""", """controllers.SinglePageController.myevents"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_SinglePageController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_SinglePageController_index0_invoker = createInvoker(
    SinglePageController_1.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SinglePageController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ SinglePageController - Manages views for single page application""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_SinglePageController_fitbuddies1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("fitbuddies")))
  )
  private[this] lazy val controllers_SinglePageController_fitbuddies1_invoker = createInvoker(
    SinglePageController_1.fitbuddies,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SinglePageController",
      "fitbuddies",
      Nil,
      "GET",
      this.prefix + """fitbuddies""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_SinglePageController_myprofile2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("myprofile")))
  )
  private[this] lazy val controllers_SinglePageController_myprofile2_invoker = createInvoker(
    SinglePageController_1.myprofile,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SinglePageController",
      "myprofile",
      Nil,
      "GET",
      this.prefix + """myprofile""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_SinglePageController_myevents3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("myevents")))
  )
  private[this] lazy val controllers_SinglePageController_myevents3_invoker = createInvoker(
    SinglePageController_1.myevents,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SinglePageController",
      "myevents",
      Nil,
      "GET",
      this.prefix + """myevents""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_Assets_versioned4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned4_invoker = createInvoker(
    Assets_0.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_SinglePageController_index0_route(params@_) =>
      call { 
        controllers_SinglePageController_index0_invoker.call(SinglePageController_1.index)
      }
  
    // @LINE:7
    case controllers_SinglePageController_fitbuddies1_route(params@_) =>
      call { 
        controllers_SinglePageController_fitbuddies1_invoker.call(SinglePageController_1.fitbuddies)
      }
  
    // @LINE:8
    case controllers_SinglePageController_myprofile2_route(params@_) =>
      call { 
        controllers_SinglePageController_myprofile2_invoker.call(SinglePageController_1.myprofile)
      }
  
    // @LINE:9
    case controllers_SinglePageController_myevents3_route(params@_) =>
      call { 
        controllers_SinglePageController_myevents3_invoker.call(SinglePageController_1.myevents)
      }
  
    // @LINE:15
    case controllers_Assets_versioned4_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned4_invoker.call(Assets_0.versioned(path, file))
      }
  }
}
