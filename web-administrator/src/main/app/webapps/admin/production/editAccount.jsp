<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Account</h3>
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
                    <h2>Edit Account</h2>
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
                    <form id="accountform" data-parsley-validate action="/admin/editAccountForm" method="POST" modelAttribute="createAccountModel" class="form-horizontal form-label-left">
				     	  <div class="form-group">
		                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Currency :</label>
		                <div class="col-md-6 col-sm-6 col-xs-12">
		                  <select name="currencyName" class="select2_single form-control" tabindex="-1">
		                     		<option name="currencyName" id="currencyName" value="${currencyID}">${currencyName}</option>
							</select>
		                </div>
		              </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name <span class="required">*</span> :
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" id="name" name="name" required="required" value="${name}" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">Description <span class="required">*</span> :
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" id="description" name="description" required="required" value="${description}" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      
                       <div class="form-group">
                          <label class="control-label col-md-3 col-sm-3 col-xs-12">System Account </label>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                           <div class="">
                       		 <label>
                       		 <c:if test="${systemAccount}">
                              <input type="checkbox" id="secure" name="secure" class="js-switch" value="1" checked /> True
                              </c:if>
                       		 <c:if test="${systemAccount == false}">
                              <input type="checkbox" id="systemAccount" name="systemAccount" class="js-switch" value="1" /> True
                              </c:if>
                            </label>
                            </div>
                    	 	</div>
                      </div>
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="hidden" id="accountID" name="accountID" required="required" value="${accountID}" class="form-control col-md-7 col-xs-12">
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
<!-- Initialize datetimepicker -->
<script>
    $('#myDatepicker').datetimepicker({
   		minDate: moment().add('days', 1),
   		maxDate: moment().add('days', 90),
        ignoreReadonly: true,
        allowInputToggle: true
    });
</script>

	</body>
</html>