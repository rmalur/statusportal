<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>History</title><!-- Include Bootstrap Datepicker -->


</style>
</head>
<g:javascript>

</g:javascript>
<style>
  .full button span {
    background-color: limegreen;
    border-radius: 32px;
    color: black;
  }
  .partially button span {
    background-color: orange;
    border-radius: 32px;
    color: black;
  }
</style>
<body>
<div ng-controller="DatepickerPopupDemoCtrl">
    

   
    <div class="row" ng-init="select()">
     <h4 style="margin-left: 30px;">Date:</h4>
      <div class="col-md-6">
        <p class="input-group"  style="display: inline-flex;">
          <input type="text"   ng-change="select()" class="form-control" uib-datepicker-popup="{{format}}" ng-model="dt" is-open="popup1.opened" datepicker-options="dateOptions" ng-required="true" close-text="Close" alt-input-formats="altInputFormats" />
          <span class="input-group-btn">
            <button type="button" class="btn btn-default" ng-click="open1()"><i class="glyphicon glyphicon-calendar"></i></button>
          </span>
        </p>
      </div>

<button class="btn btn-info"  ng-click="exportToExcel('#tblMain')">Export Record In Excel Format</button>



<g:if test="${flash.message}">
		<div class="alert alert-info alert-dismissable fade in">
			<a href="" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Info!</strong>
			${flash.message}

		</div>
	</g:if>
		<form>
 
      <table id="tblMain" border="0" class= "table  table-responsive" >
       <tr class="bg-info">
        <th>Id</th>
        <th>Ticket-id</th>
        <th>Summary</th>
        <th>Assignee</th>
        <th>Work_done</th>
        <th>Impediments</th>
        <th>Work Hours for day</th>
        <th>Updated_Date</th>
        <th>Status</th>
       </tr>
   
       <tr ng-repeat="ticket in ticketList">
        <td>{{$index+1}}</td>
        <td>{{ticket.ticket_id}}</td>
        <td>{{ticket.summary}}</td>
        <td>{{ticket.assignee}}</td>
        <td>{{ticket.workdoneBy}}</td> 
        <td>{{ticket.impediments}}</td>
        <td>{{ticket.todaysWorkHrs}}</td>
        <td>{{ticket.updateDate}}</td>
        <td>{{ticket.updatedStatus}}</td>
     
       </tr>
      </table>
     </div> 
    </form>
</body>
</html>