
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
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*20.59*/routes/*20.65*/.Assets.versioned("images/favicon.png")),format.raw/*20.104*/("""">
    </head>
    <body>
        """),format.raw/*24.31*/("""
        """),_display_(/*25.10*/content),format.raw/*25.17*/("""

        """),format.raw/*27.9*/("""<script src=""""),_display_(/*27.23*/routes/*27.29*/.Assets.versioned("lib/jquery/jquery.min.js")),format.raw/*27.74*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*28.23*/routes/*28.29*/.Assets.versioned("lib/popper.js/dist/umd/popper.min.js")),format.raw/*28.86*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*29.23*/routes/*29.29*/.Assets.versioned("lib/bootstrap/js/bootstrap.min.js")),format.raw/*29.83*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*30.23*/routes/*30.29*/.Assets.versioned("lib/sammy/sammy.min.js")),format.raw/*30.72*/("""" type="text/javascript"></script>
        <script src=""""),_display_(/*31.23*/routes/*31.29*/.Assets.versioned("lib/leaflet/leaflet.js")),format.raw/*31.72*/(""""></script>
        <script src=""""),_display_(/*32.23*/routes/*32.29*/.Assets.versioned("javascripts/main.js")),format.raw/*32.69*/("""" type="text/javascript"></script>
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
                  DATE: Mon Oct 15 12:25:41 CEST 2018
                  SOURCE: C:/Users/Silvan Knecht/Documents/GitHub/JKL_Fitbuddy/app/views/main.scala.html
                  HASH: e3cf7435adba1c73c6cff3bd04a2462d3d63c737
                  MATRIX: 1206->261|1331->291|1361->295|1444->403|1481->413|1516->421|1542->426|1762->619|1777->625|1855->681|1939->738|1954->744|2017->785|2086->827|2101->833|2166->877|2256->940|2271->946|2332->985|2397->1112|2435->1123|2463->1130|2502->1142|2543->1156|2558->1162|2624->1207|2709->1265|2724->1271|2802->1328|2887->1386|2902->1392|2977->1446|3062->1504|3077->1510|3141->1553|3226->1611|3241->1617|3305->1660|3367->1695|3382->1701|3443->1741
                  LINES: 33->7|38->7|40->9|43->12|44->13|44->13|44->13|48->17|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|54->24|55->25|55->25|57->27|57->27|57->27|57->27|58->28|58->28|58->28|59->29|59->29|59->29|60->30|60->30|60->30|61->31|61->31|61->31|62->32|62->32|62->32
                  -- GENERATED --
              */
          