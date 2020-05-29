<%
if (session.getAttribute("Username") != null)
 {
 response.sendRedirect("items.jsp");
 }
%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pharmacist</title>
<script src="./js/axios.min.js"></script>
<link
	href="https://fonts.googleapis.com/css?family=Quicksand&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="./css/style.css">
</head>

<body>
	<div class="topic">Pharmacist</div>
	<div id="alertSuccess"></div>
	<div id="alertError"></div>

	<div>
		<button class="accordion" id="addMoreAccordion">Add more
			items</button>
		<div class="panel">
			<form class="cus_form" id="validate_form">
				<input class="cus_input" id="med_generic_name" type="text"
					placeholder="Generic name" required /> 
				<input
					class="cus_input" id="med_brand_name" type="text" required
					placeholder="Brand name" />
				<input class="cus_input"
					id="med_expiration_date" type="date" required
					placeholder="Expiration date" value="" />
				<input
					class="cus_input" id="med_unit_price" type="text" required
					placeholder="Unit price" />
				<input class="cus_input"
					id="med_quantity" type="number" required placeholder="Quantity" />
			</form>
			<div class="btn_cover">
				<div class="editStock" id="btnAdd">Add</div>
			</div>
			<div id="sendSuccessfull"></div>
		</div>


	</div>
	<div id="error_msg" class="ErrorMessage"></div>
	<table id="mytable">
		<thead id="myhead">
			<tr>
				<th>ID</th>
				<th>Generic_name</th>
				<th>Brand_name</th>
				<th>Expiration_date</th>
				<th>Unit_price</th>
				<th>Quantity</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody id="mybody">
		</tbody>
	</table>
	
	<div id="divItemsGrid"></div>



	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		// Form validation client side
		$(document).ready(function()
		{
		 $("#alertSuccess").hide();
		 $("#alertError").hide();
		}); 
	function validateItemForm()
		{
			if ($("#med_generic_name").val().trim() == "")
			 {
			 	return "Insert medical name.";
			 }
			
			if ($("#med_brand_name").val().trim() == "")
			 {
			 	return "Insert brand name.";
			 }
			
			if ($("#med_expiration_date").val() == "")
			 {
			 	return "Select year.";
			 }
			
			if (parseInt($("#med_unit_price").val(),10) < 0 || $("#med_unit_price").val().trim() == "")
			 {
			 	return "Unit price should be positive.";
			 }
			
			if (parseInt($("#med_quantity").val(),10) < 0 || $("#med_quantity").val().trim() == "")
			 {
			 	return "Quntitiy should be positive.";
			 }
			
			return true; 
		}

	
	$(document).on("click", "#btnAdd", function(event)
	{
		// Clear status msges-------------
		 $("#alertSuccess").text("");
		 $("#alertSuccess").hide();
		 $("#alertError").text("");
		 $("#alertError").hide();
		// Form validation----------------
		var status = validateItemForm();
		// If not valid-------------------
		if (status != true)
		 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
		 }
		// If valid-----------------------
		addMedicine();
	});
			
 	getMedicineStock();
 	
 	// Ajax request for getting medicine stock
    function getMedicineStock() {
      axios({
        method: 'get',
        url: 'http://localhost:8080/final/rest/medicine'
      }).then(function(response) {
        var finaldata = response.data.Data;
        var finalerror = response.data.Error;
        var tbody = document.getElementById("mybody");
        cleanData();
        if (finalerror.error_code == 0) {
          for (let i = 0; i < finaldata.length; i++) {
            var row = tbody.insertRow(i);
            row.insertCell(0).innerHTML = finaldata[i].id;
            row.insertCell(1).innerHTML = finaldata[i].generic_name;
            row.insertCell(2).innerHTML = finaldata[i].brand_name;
            row.insertCell(3).innerHTML = finaldata[i].expiration_date;
            row.insertCell(4).innerHTML = finaldata[i].unit_price;
            row.insertCell(5).innerHTML = finaldata[i].quantity;
            row.insertCell(6).innerHTML = 
            "<div class=\"actionBtnCover\">" 
            +"<span class=\"updateBtn\" onclick=\"updateMe(\'" + finaldata[i].id + "\')\">Update</span>"
            +"<span class=\"deleteBtn\" onclick=\"deleteMe(\'" + finaldata[i].id + "\')\">Delete</span>"
            +"</div>";
          }
          document.getElementById("error_msg").innerHTML = "";
        } else {
          document.getElementById("error_msg").innerHTML = "Something Went Wrong";
        }

      }).catch(function(error) {
    	  document.getElementById("error_msg").innerHTML = "";
      });
    }

    function cleanData() {
      var tbody = document.getElementById("mybody");
      tbody.innerHTML = "";
    }
    
    function goToPage(redirect){
    	window.location.href = redirect;
    }
    
    function updateMe(id){
    	sessionStorage.setItem("id", id);
    	window.location.href = "form.html";
    }
    
    function onItemDeleteComplete(response, status)
    {
    if (status == "success")
    {
	     var resultSet = JSON.parse(response);
	     
	     if (resultSet.status.trim() == "success")
	     {
		     $("#alertSuccess").text("Successfully deleted.");
		     $("#alertSuccess").show();
		     $("#divItemsGrid").html(resultSet.data);
	     } else if (resultSet.status.trim() == "error"){
		     $("#alertError").text(resultSet.data);
		     $("#alertError").show();
	     }
	     
	     } else if (status == "error"){
		     $("#alertError").text("Error while deleting.");
		     $("#alertError").show();
	     } else{
		     $("#alertError").text("Unknown error while deleting..");
		     $("#alertError").show();
	     }
    }
    
    function onItemAddComplete(response, status)
    {
	    if (status == "success")
	     {
		     var resultSet = JSON.parse(response);
		     if (resultSet.status.trim() == "success")
		     {
			     $("#alertSuccess").text("Successfully saved.");
			     $("#alertSuccess").show();
			     $("#divItemsGrid").html(resultSet.data);
		     } else if (resultSet.status.trim() == "error"){
			     $("#alertError").text(resultSet.data);
			     $("#alertError").show();
		     }
	     
	     } else if (status == "error"){
		     $("#alertError").text("Error while saving.");
		     $("#alertError").show();
	     } else {
		     $("#alertError").text("Unknown error while saving..");
		     $("#alertError").show();
	     }
	     $("#hidItemIDSave").val("");
	     $("#formItem")[0].reset();
    }
    
    function onItemUpdateComplete(response, status)
    {
	    if (status == "success")
	     {
		     var resultSet = JSON.parse(response);
		     if (resultSet.status.trim() == "success")
		     {
			     $("#alertSuccess").text("Successfully saved.");
			     $("#alertSuccess").show();
			     $("#divItemsGrid").html(resultSet.data);
		     } else if (resultSet.status.trim() == "error"){
			     $("#alertError").text(resultSet.data);
			     $("#alertError").show();
		     }
	     
	     } else if (status == "error"){
		     $("#alertError").text("Error while saving.");
		     $("#alertError").show();
	     } else {
		     $("#alertError").text("Unknown error while saving..");
		     $("#alertError").show();
	     }
	     $("#hidItemIDSave").val("");
	     $("#formItem")[0].reset();
    }
    
    function deleteMe(id){
    	 $.ajax(
    			 {
    			 url : "PharmacistApi",
    			 type : "DELETE",
    			 data : "itemID=" + id,
    			 dataType : "text",
    			 complete : function(response, status)
    			 {
						onItemDeleteComplete(response.responseText, status);
				     	//getMedicineStock();
				     	//alert("Data Deleted Successfully");
    			 }
    			 });
    }
    
    function addMe(id){
   	 $.ajax(
	 			 {
	 			 url : "PharmacistApi",
	 			 type : "POST",
	 			 data : "id=" + id + " med_generic_name=" + $("#med_generic_name").value + " med_brand_name" + $("#med_brand_name").value + " med_expiration_date"+ $("#med_expiration_date").value + " med_unit_price"+ $("#med_unit_price").value + " med_quantity"+ $("#med_quantity").value,
	   	 		 dataType : "text",
	 			 complete : function(response, status)
	 			 {
	 				onItemAddComplete(response.responseText, status);
			     	//getMedicineStock();
			     	//alert("Data Deleted Successfully");
	 			 }
	 			 });
   }
    
    function updateMe(id){
      	 $.ajax(
   	 			 {
   	 			 url : "PharmacistApi",
   	 			 type : "POST",
   	 			 data : "id=" + id + " med_generic_name=" + $("#med_generic_name").value + " med_brand_name" + $("#med_brand_name").value + " med_expiration_date"+ $("#med_expiration_date").value + " med_unit_price"+ $("#med_unit_price").value + " med_quantity"+ $("#med_quantity").value,
   	 			 dataType : "text",
   	 			 complete : function(response, status)
   	 			 {
   	 				onItemAddComplete(response.responseText, status);
   			     	//getMedicineStock();
   			     	//alert("Data Deleted Successfully");
   	 			 }
   	 			 });
      }
    
    
    document.getElementById("validate_form").onclick = function() {hideErrorMsg()};
    document.getElementById("addMoreAccordion").onclick = function() {hideErrorMsg()};
    
    function hideErrorMsg() {
    	document.getElementById("sendSuccessfull").innerHTML = "";
    }


    function addMedicine() {
		axios.post('http://localhost:8080/final/rest/medicine', {
			   "generic_name": document.getElementById("med_generic_name").value,
			   "brand_name": document.getElementById("med_brand_name").value,
			   "expiration_date": document.getElementById("med_expiration_date").value,
			   "unit_price": document.getElementById("med_unit_price").value,
			   "quantity": document.getElementById("med_quantity").value
			 }).then(function(response) {
			   getMedicineStock();
	           document.getElementById("sendSuccessfull").innerHTML = "Data successfully added";
			 }).catch(function(error) {
				 document.getElementById("error_msg").innerHTML = "Something Went Wrong.Try it later!";
			 });

    }
    
	var acc = document.getElementsByClassName("accordion");	
	var i;
	
	for (i = 0; i < acc.length; i++) {
	  acc[i].addEventListener("click", function() {
	    this.classList.toggle("active");
	    document.getElementsByClassName("sendSuccessfull").innerHTML = "";
	    var panel = this.nextElementSibling;
	    if (panel.style.maxHeight) {
	      panel.style.maxHeight = null;
	    } else {
	      panel.style.maxHeight = (panel.scrollHeight+600) + "px";
	    } 
	  });
	}
  </script>
</body>

</html>