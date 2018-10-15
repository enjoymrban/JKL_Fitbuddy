
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /*
* This template is called from the `index` template. This template
* handles the rendering of the page header and body tags. It takes
* two arguments, a `String` for the title of the page and an `Html`
* object to insert into the body of the page.
*/
  def apply/*7.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*7.32*/("""

"""),format.raw/*9.1*/("""<!DOCTYPE html>
<html lang="en">
    <head>
        """),format.raw/*12.62*/("""
        """),format.raw/*13.9*/("""<title>"""),_display_(/*13.17*/title),format.raw/*13.22*/("""</title>

        <meta charset="uft-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" media="screen" href=""""),_display_(/*17.54*/routes/*17.60*/.Assets.versioned("lib/bootstrap/css/bootstrap.min.css")),format.raw/*17.116*/("""">
        <link rel="stylesheet" media="screen" href=""""),_display_(/*18.54*/routes/*18.60*/.Assets.versioned("stylesheets/main.css")),format.raw/*18.101*/("""">
        <link rel="stylesheet" href=""""),_display_(/*19.39*/routes/*19.45*/.Assets.versioned("lib/leaflet/leaflet.css")),format.raw/*19.89*/(""""/>
        <link rel="stylesheet" href=""""),_display_(/*20.39*/routes/*20.45*/.Assets.versioned("lib/material-design-icons/material-icons.css")),format.raw/*20.110*/(""""/>
        <link rel="stylesheet" href=""""),_display_(/*21.39*/routes/*21.45*/.Assets.versioned("lib/font-awesome/css/all.css")),format.raw/*21.94*/(""""/>
        <link rel="shortcut icon" type="image/svg" href=""""),_display_(/*22.59*/routes/*22.65*/.Assets.versioned("images/Soccer_ball.svg")),format.raw/*22.108*/("""">
    </head>
    <body>
        <nav class="navbar navbar-expand-sm navbar-light bg-light">
            <a class="navbar-brand" href="#">
                <img src=""""),_display_(/*27.28*/routes/*27.34*/.Assets.versioned("images/Soccer_ball.svg")),format.raw/*27.77*/("""" width="30" height="30" class="d-inline-block align-top" alt="">
                Fitbuddy
            </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i class="material-icons">menu</i>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/fitbuddies">Fit buddies <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/" onclick="">Create event</a>
                    </li>
                </ul>
                <a href=""><i class="fas fa-sign-in-alt fa-2x"></i></a>
            </div>
        </nav>
        """),format.raw/*47.31*/("""
        """),_display_(/*48.10*/content),format.raw/*48.17*/("""

        """),format.raw/*50.9*/("""<script src=""""),_display_(/*50.23*/routes/*50.29*/.Assets.versioned("lib/jquery/jquery.min.js")),format.raw/*50.74*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*51.23*/routes/*51.29*/.Assets.versioned("lib/popper.js/dist/umd/popper.min.js")),format.raw/*51.86*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*52.23*/routes/*52.29*/.Assets.versioned("lib/bootstrap/js/bootstrap.min.js")),format.raw/*52.83*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*53.23*/routes/*53.29*/.Assets.versioned("lib/sammy/sammy.min.js")),format.raw/*53.72*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*54.23*/routes/*54.29*/.Assets.versioned("lib/leaflet/leaflet.js")),format.raw/*54.72*/(""""></script>
        <script src=""""),_display_(/*55.23*/routes/*55.29*/.Assets.versioned("javascripts/main.js")),format.raw/*55.69*/("""" type="text/javascript"></script>
    </body>
</html>
"""))
      }
    }
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Mon Oct 15 16:09:44 CEST 2018
                  SOURCE: C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/app/views/main.scala.html
                  HASH: a97eb18e7144878d70201a8efe151da4bc7cb74e
                  MATRIX: 1206->261|1331->291|1361->295|1444->403|1481->413|1516->421|1542->426|1762->619|1777->625|1855->681|1939->738|1954->744|2017->785|2086->827|2101->833|2166->877|2236->920|2251->926|2338->991|2408->1034|2423->1040|2493->1089|2583->1152|2598->1158|2663->1201|2862->1373|2877->1379|2941->1422|3986->2529|4024->2540|4052->2547|4091->2559|4132->2573|4147->2579|4213->2624|4298->2682|4313->2688|4391->2745|4476->2803|4491->2809|4566->2863|4651->2921|4666->2927|4730->2970|4815->3028|4830->3034|4894->3077|4956->3112|4971->3118|5032->3158
                  LINES: 33->7|38->7|40->9|43->12|44->13|44->13|44->13|48->17|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|53->22|58->27|58->27|58->27|77->47|78->48|78->48|80->50|80->50|80->50|80->50|81->51|81->51|81->51|82->52|82->52|82->52|83->53|83->53|83->53|84->54|84->54|84->54|85->55|85->55|85->55
                  -- GENERATED --
              */
          