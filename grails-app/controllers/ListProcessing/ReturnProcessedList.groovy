package ListProcessing

  class ReturnProcessedList {

	
	 def static serviceForListProcessing(Object ticketListForProcessing){
		
		def ticketList=[]
		for(ticketObject in ticketListForProcessing){
			def ticket = [:]
				ticket.put("id",ticketObject.id)
				ticket.put("ticket_id", ticketObject.ticket.ticket_id)
				ticket.put("summary",ticketObject.ticket.summary)
				ticket.put("assignee",ticketObject.ticket.assignee)
				ticket.put("workDoneBy",ticketObject.workdoneBy )
				ticket.put("impediments",ticketObject.impediments)
				ticket.put("todaysWorkHrs",ticketObject.todaysWorkHrs )
				ticket.put("updateDate",ticketObject.updateDate )
				ticket.put("updatedStatus",ticketObject.updatedStatus)
				ticketList.add(ticket)
				
		}
		
		return ticketList
		
	}
	
	def static returnUniqueListOfticketIds(Object ticketListForProcessing){
		def listOfTicketIds = []
		for(ticketObject in ticketListForProcessing){
			if(listOfTicketIds.contains(ticketObject.ticket.ticket_id)){
					continue
		
				}else{
				listOfTicketIds.add(ticketObject.ticket.ticket_id)
				}
			
		}
		return listOfTicketIds
	}
}
