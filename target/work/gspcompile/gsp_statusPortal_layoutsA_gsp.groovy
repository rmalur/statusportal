import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_statusPortal_layoutsA_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/A.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',6,['gsp_sm_xmlClosingForEmptyTag':(""),'charset':("utf-8")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',7,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("X-UA-Compatible"),'content':("IE=edge")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',8,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("viewport"),'content':("width=device-width, initial-scale=1")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',9,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("description"),'content':("")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',10,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("author"),'content':("")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',12,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',12,[:],2)
printHtmlPart(4)
expressionOut.print(request.contextPath)
printHtmlPart(5)
expressionOut.print(request.contextPath)
printHtmlPart(6)
expressionOut.print(request.contextPath)
printHtmlPart(7)
})
invokeTag('captureHead','sitemesh',36,[:],1)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(8)
createClosureForHtmlPart(9, 2)
invokeTag('link','g',62,['class':("navbar-brand"),'controller':("StatusPortal"),'action':("index")],2)
printHtmlPart(10)
createClosureForHtmlPart(11, 2)
invokeTag('link','g',68,['controller':("StatusPortal"),'action':("todaysTickets")],2)
printHtmlPart(12)
createClosureForHtmlPart(13, 2)
invokeTag('link','g',70,['controller':("StatusPortal"),'action':("getAllTicketsOfUser")],2)
printHtmlPart(12)
createClosureForHtmlPart(14, 2)
invokeTag('link','g',71,['controller':("StatusPortal"),'action':("ticketHistory")],2)
printHtmlPart(15)
invokeTag('layoutBody','g',86,[:],-1)
printHtmlPart(16)
})
invokeTag('captureBody','sitemesh',100,['ng-app':("myApp")],1)
printHtmlPart(17)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1493030665649L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
