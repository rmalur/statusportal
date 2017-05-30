import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_statusPortal_statusPortaltodaysTickets_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/statusPortal/todaysTickets.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("A")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',5,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],2)
printHtmlPart(1)
})
invokeTag('captureHead','sitemesh',6,[:],1)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(3)
expressionOut.print(createLink(controller:'logout'))
printHtmlPart(4)
if(true && (flash.message)) {
printHtmlPart(5)
expressionOut.print(flash.message)
printHtmlPart(6)
}
printHtmlPart(7)
createTagBody(2, {->
printHtmlPart(8)
loop:{
int i = 0
for( res in (results) ) {
printHtmlPart(9)
expressionOut.print(i+1)
printHtmlPart(10)
expressionOut.print(res.ticket_id)
printHtmlPart(11)
expressionOut.print(res.summary)
printHtmlPart(11)
expressionOut.print(res.assignee)
printHtmlPart(11)
expressionOut.print(res.status)
printHtmlPart(12)
createClosureForHtmlPart(13, 4)
invokeTag('link','g',54,['id':(res.ticket_id),'controller':("StatusPortal"),'action':("updateTicketStatus")],4)
printHtmlPart(14)
i++
}
}
printHtmlPart(15)
})
invokeTag('form','g',59,['controller':("UpdateStatus"),'action':("getResults")],2)
printHtmlPart(1)
})
invokeTag('captureBody','sitemesh',60,[:],1)
printHtmlPart(16)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1492074098306L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
