
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.4*/("""

    """),_display_(/*3.6*/main("Fitbuddy")/*3.22*/ {_display_(Seq[Any](format.raw/*3.24*/("""
        """),format.raw/*4.9*/("""<div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div id="custom-search-input">
                        <div class="input-group col-md-12">
                            <input id="Search Field" type="text" class="form-control input-lg" placeholder="Search for event" />
                            <span class="input-group-btn">
                                <button class="btn btn-info btn-lg" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        <div id="mapid">

        </div>
    """)))}),format.raw/*25.6*/("""
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
                  DATE: Mon Oct 15 16:12:16 CEST 2018
                  SOURCE: C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/app/views/index.scala.html
                  HASH: 64ae785da2f19fa11a56f867628ae262aa728f8d
                  MATRIX: 941->1|1037->3|1071->12|1095->28|1134->30|1170->40|2026->866
                  LINES: 28->1|33->1|35->3|35->3|35->3|36->4|57->25
                  -- GENERATED --
              */
          