jQuery.fn.serializeObject = function() {
  var obj = null;
  try {
    if ( this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) {
      var arr = this.serializeArray();
      if ( arr ) {
        obj = {};
        jQuery.each(arr, function() {
          obj[this.name] = this.value;
        });				
      }//if ( arr ) {
 		}
  }
  catch(e) {alert(e.message);}
  finally  {}
  
  return obj;
};

var ROOT_URL = "http://localhost:8080/";
		
var post = function(url, data, success) {
	$.ajax({
		  type: "POST",
		  url: ROOT_URL + url,
		  crossDomain: true,
		  data: data,
		  success: success,
		  dataType: 'jsonp'
	});
};

var get = function(url, data, success) {
	data['userIdx'] = "{{ session['userIdx'] }}";
	$.ajax({
  		url: ROOT_URL + url,
		jsonp: "callback",
  		dataType: 'json',
  		data: data,
		success: function(result) {
			console.log("success: " + JSON.stringify(result));
		}
	});
}
