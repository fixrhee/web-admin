<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Transfer Type</h3>
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
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Create Broker</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="#">Settings</a>
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
                    <form id="feeForm" data-parsley-validate action="/admin/createBrokerForm" method="POST" modelAttribute="createBrokerModel" class="form-horizontal form-label-left">
					  
					   <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Broker Name <span class="required">*</span> :
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" id="name" name="name" required="required" class="form-control col-md-7 col-xs-12">
                        </div>
                       </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">Description <span class="required">*</span> :
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" id="description" name="description" required="required" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Credit to :
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" id="toMember" name="toMember" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                     
           			  <div  class="form-group">
		                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">To Account :</label>
		                <div class="col-md-6 col-sm-6 col-xs-12">
		                  <select name="toAccountName" class="select2_single form-control" tabindex="-1">
		                  	            <c:forEach var="listAccount" items="${listAccount}">
									<option name="toAccountName" id="toAccountName" value="${listAccount}">${listAccount}</option>
								</c:forEach>
		                   </select>
		                </div>
		              </div>
		              
		             <hr />
		             
		              <div class="form-group">
                          <label class="control-label col-md-3 col-sm-3 col-xs-12">Deduct All Fee </label>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                           <div class="">
                       		 <label>
                              <input type="checkbox" id="deductAllFee" name="deductAllFee" class="js-switch" value="1" onChange='handleChange(this);'/> True
                            </label>
                            </div>
                    		 </div>
                    	  </div>
                   
                     <div id="amountDiv" class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="fixedAmount">Fixed Amount <span class="required">*</span> :
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" id="fixedAmount" name="fixedAmount" required="required" value="0" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      
                       <div id="percentageDiv" class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="percentage">Percentage <span class="required">*</span> :
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" id="percentage" name="percentage" required="required" value="0" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      
                      <hr />
                      
                    	   <div class="form-group">
                          <label class="control-label col-md-3 col-sm-3 col-xs-12"><font color="red">Enabled </font></label>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                           <div class="">
                       		 <label>
                              <input type="checkbox" id="enabled" name="enabled" class="js-switch" value="1"  /> True
                            </label>
                            </div>
                    		 </div>
                    	  </div>
            
                      
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="hidden" id="feeID" name="feeID" required="required" value="${feeID}" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      
                      
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                  		  <button class="btn btn-primary" type="reset">Reset</button>
                          <button type="submit" class="btn btn-success">Submit</button>
                        </div>
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
   document.getElementById("amountDiv").style.display = "block";
   document.getElementById("percentageDiv").style.display = "block";
 </script>

<script>
function handleChange(checkbox) {
    if(checkbox.checked == true){
    document.getElementById("amountDiv").style.display = "none"
    document.getElementById("percentageDiv").style.display = "none"
    }else{
    document.getElementById("amountDiv").style.display = "block"
    document.getElementById("percentageDiv").style.display = "block"
    }
 }
</script>

	</body>
</html>