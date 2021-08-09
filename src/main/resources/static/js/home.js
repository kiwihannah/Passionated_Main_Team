	//메인 슬라이더
	var counter = 1;
    setInterval(function() {
        document.getElementById('radio' + counter).checked = true;
        counter++;
        if(counter > 4) {
            counter =1;
        }
    }, 5000);

    /*load 함수*/
    $(window).load(function() { /*window가 로드되면 함수 실행*/
    		$('.loader').delay('1000').fadeOut(); /*loader class 처리해야 fade out*/
    	});

    /*Tawk*/
    	var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
        (function(){
        var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
        s1.async=true;
        s1.src='https://embed.tawk.to/60a4d80a185beb22b30ea5d1/1f61unr3a';
        s1.charset='UTF-8';
        s1.setAttribute('crossorigin','*');
        s0.parentNode.insertBefore(s1,s0);
        })();