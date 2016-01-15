function A(){
	function B(){
		console.log("aaa");
	}
	
	return B;
}

var a=A();
a();
