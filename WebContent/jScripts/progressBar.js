
    function loadSubmit() {
	
    var ProgressImage = document.getElementById("progress_image");
    document.getElementById("progress_image").style.visibility = 'visible';
  
    document.getElementById("progress").style.visibility = "visible";
    return true;

    } 
    function finishSubmit() {

    var ProgressImage = document.getElementById("progress_image");
    ProgressImage.style.visibility = "hidden";
    document.getElementById("progress").style.visibility = "hidden";
    return true;
    } 
