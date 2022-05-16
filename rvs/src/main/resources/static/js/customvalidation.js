function onlyAlphabets(e, t) {
            try {
                if (window.event) {
                    var charCode = window.event.keyCode;
                }
                else if (e) {
                    var charCode = e.which;
                }
                else { return true; }
                if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || (charCode == 32))
                    return true;
                else
                    return false;
            }
            catch (err) {
                alert(err.Description);
            }
        }

function onlyAlphanumeric(e, t) {
    try {
        if (window.event) {
            var charCode = window.event.keyCode;
        }
        else if (e) {
            var charCode = e.which;
        }
        else { return true; }
        
        // check number only.
        if(charCode == 48 || charCode == 49 || charCode == 50 || charCode == 51 || charCode == 52 || charCode == 53 ||  charCode ==  54 ||  charCode == 55 || charCode == 56 || charCode == 57 || charCode == 32){
            return true;
        }
        
        if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))
            return true;
        else
            return false;
    }
    catch (err) {
        alert(err.Description);
    }
}

$(document).ready(function(){
	
	
	$('[data-type="adhaar-number"]').keyup(function() {
	  var value = $(this).val();
	  value = value.replace(/\D/g, "").split(/(?:([\d]{4}))/g).filter(s => s.length > 0).join("-");
	  $(this).val(value);
	});

	$('[data-type="adhaar-number"]').on("change, blur", function() {
	  var value = $(this).val();
	  var maxLength = $(this).attr("maxLength");
	  if (value.length != maxLength) {
	    $(this).addClass("highlight-error");
	  } else {
	    $(this).removeClass("highlight-error");
	  }
	});
	
	$('.email').on("change, blur", function() {
		  var value = $(this).val();
		  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		  if (!emailReg.test(value)) {
		    $(this).addClass("highlight-error");
		  } else {
		    $(this).removeClass("highlight-error");
		  }
	});
		
	
	
});