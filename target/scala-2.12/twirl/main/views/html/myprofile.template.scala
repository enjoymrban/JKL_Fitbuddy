
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._

object myprofile extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.4*/("""

"""),_display_(/*3.2*/main("My Profile")/*3.20*/ {_display_(Seq[Any](format.raw/*3.22*/("""
  """),format.raw/*4.3*/("""<h1>My Profile</h1>
""")))}),format.raw/*5.2*/("""
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Mon Oct 15 12:42:45 CEST 2018
                  SOURCE: C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/app/views/myprofile.scala.html
                  HASH: ce56fe255e10f2fdedc3f1f5c109d8735d012a4b
                  MATRIX: 945->1|1041->3|1071->8|1097->26|1136->28|1166->32|1217->54
                  LINES: 28->1|33->1|35->3|35->3|35->3|36->4|37->5
                  -- GENERATED --
              */
          