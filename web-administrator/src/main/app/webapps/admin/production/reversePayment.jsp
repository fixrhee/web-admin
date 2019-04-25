<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
       <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Transfer</h3>
              </div>
              
               <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                 <div class="input-group">
                    <br /> <br /> </div>
                </div>
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-24">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Reversal</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="#">Settings 1</a>
                          </li>
                        </ul>
                      </li>
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  
                  <div class="x_content">
                  <br />
                  <table id="trfMemberInquiry" class="table table-striped">
                      <tbody>
                        <tr>
                          <td width="150">Transaction Date</td>
                          <td width="150"> : </td>
                          <td align="right">${transactionDate}</td>
                        </tr>
                        <tr>
                          <td width="150">To/From Member</td>
                          <td width="150"> : </td>
                          <td align="right">${toFromMember}</td>
                        </tr>
                        <tr>
                          <td width="150">Amount</td>
                          <td width="150"> : </td>
                          <td align="right">${amount}</td>
                        </tr>
                        <tr>
                          <td width="150">Trace Number</td>
                          <td width="150"> : </td>
                          <td align="right">${traceNumber}</td>
                        </tr>
                         <tr>
                          <td width="150">Transaction Number</td>
                          <td width="150"> : </td>
                          <td align="right">${transactionNumber}</td>
                        </tr>
                        <tr>
                          <td width="150">Remark</td>
                          <td width="150"> : </td>
                          <td align="right">${remark}</td>
                        </tr>
                        <tr>
                          <td width="150">Description</td>
                          <td width="150"> : </td>
                          <td align="right">${description}</td>
                        </tr>
                        <tr>
			               <td width="150">PIN</td>
			               <td width="150"> : </td>
			               <td align="right">
								<input type="password" id="credential1" name="credential1" required="required" style=”text-align:right”>
						   </td>
                        </tr>
                      </tbody>
                    </table>
                    <hr/>
                    <form id="reverseform" data-parsley-validate action="/admin/reversePayment" method="POST" modelAttribute="reversepayment" class="form-horizontal form-label-left">
                  		  <input type="hidden" id="credential" name="credential" readonly required="required" class="form-control col-md-7 col-xs-12">
                          <input type="hidden" id="transactionNumber" name="transactionNumber" required="required" readonly value="${transactionNumber}" class="form-control col-md-7 col-xs-12">
                          <input id="username" name="username" value="${member.username}" class="date-picker form-control col-md-7 col-xs-12" type="hidden">
                          <!--input id="sessionID" name="SessionID" value="${sessionID}" class="date-picker form-control col-md-7 col-xs-12" type="hidden"-->
                      
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
           				  <button class="btn btn-primary" type="button" onclick="history.back()">Back</button>
                          <button type="submit" id="sbm" class="btn btn-success">Submit</button>
                        </div>
                      
                      </form>
                  </div>
                </div>
              </div>

      
            </div>
          </div>
        </div>
        <!-- /page content -->
   
	<%@include file="footer.jsp" %>
	<script>
		$('button[type=submit]').click(function(){
	    	$('#credential').val($('#credential1').val());
		});
	</script>
	
	<c:if test="${not empty fn:trim(notification)}">
		<script type="text/javascript">
			$(function(){
		 	 new PNotify({
		        title: '${title}',
		        text: '${message}',
		        type: '${notification}',
		        styling: 'bootstrap3'
		        });
			});
		</script>
	</c:if>
	
	<script>
		function reloadTable(){
			var table = $('#trxtable').DataTable();
			table.ajax.reload( null, false );
		}
	</script>
	
	</body>
</html>
